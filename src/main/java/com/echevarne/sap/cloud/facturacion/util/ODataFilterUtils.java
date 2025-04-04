package com.echevarne.sap.cloud.facturacion.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.echevarne.sap.cloud.facturacion.odata.extension.utils.EntityUtils;
import com.echevarne.sap.cloud.facturacion.odata.extension.utils.PropertyUtils;
import com.echevarne.sap.cloud.facturacion.odata.processor.parser.SQLFilterParser;
import com.sap.cloud.sdk.service.prov.api.filter.ExpressionOperatorTypes.OPERATOR;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionParserException;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.core.uri.expression.BinaryExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.ExpressionParserInternalError;
import org.apache.olingo.odata2.core.uri.expression.FilterExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.FilterParserImpl;
import org.apache.olingo.odata2.core.uri.expression.LiteralExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.MemberExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.PropertyExpressionImpl;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ODataFilterUtils {

	private static final String GET = "get";
	private static final String COUNT = "count";
	private static final String SEARCH = "search";
	public static final List<String> EXCLUDED_ENTITIES = Arrays.asList("ActividadColectiva");

	/**
	 *
	 *
	 *
	 * @param jpaEntities
	 * @param uriInfo
	 * @param search
	 * @param skip
	 * @param top
	 * @return
	 */
	public static List<Object> filteredList(List<Object> jpaEntities, UriInfoImpl uriInfo, String search, Integer skip,
			Integer top) {
		List<Object> jpaEntitiesFiltered = new ArrayList<Object>();
		if (jpaEntities.size() > 0) {
			Method[] methods = jpaEntities.get(0).getClass().getMethods();
			List<Method> methodsList = Arrays.asList(methods);
			jpaEntities.stream().forEach(o -> {
				methodsList.stream().forEach(m -> {
					if (m.getName().contains(GET)) {
						Object value = null;
						try {
							value = m.invoke(o);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							log.error("Ops!", e);
						}
						String valueStr = "";
						if (value instanceof String) {
							valueStr = (String) value;
						}
						if (value instanceof Integer) {
							valueStr = String.valueOf(value);
						}
						if (valueStr.contains(search) && !jpaEntitiesFiltered.contains(o)) {
							jpaEntitiesFiltered.add(o);
						}
					}
				});
			});
		}
		int size = jpaEntitiesFiltered.size();
		uriInfo.getCustomQueryOptions().put(COUNT, String.valueOf(size));
		if (skip == null || top == null) {
			return jpaEntitiesFiltered;
		}
		if (size > skip && size < skip + top) {
			return jpaEntitiesFiltered.subList(skip, size);
		}
		if (size > skip && size >= skip + top) {
			return jpaEntitiesFiltered.subList(skip, skip + top);
		}
		return jpaEntitiesFiltered;
	}

	/**
	 *
	 * @param uriParserResultView
	 * @return
	 */
	public static UriInfoImpl convertFilterValueToInternal(GetEntitySetUriInfo uriParserResultView) {
		UriInfoImpl uriInfo = (UriInfoImpl) uriParserResultView;
		try {
			FilterExpressionImpl filter = (FilterExpressionImpl) uriInfo.getFilter();
			if (filter != null && ConversionUtils.applyAlphaConvertion(filter)) {
				BinaryExpressionImpl expression = (BinaryExpressionImpl) filter.getExpression();
				LiteralExpressionImpl rightOp = (LiteralExpressionImpl) expression.getRightOperand();
				CommonExpression leftOp = expression.getLeftOperand();
				String campo = null;
				if (leftOp instanceof PropertyExpressionImpl) {
					PropertyExpressionImpl prExp = (PropertyExpressionImpl) leftOp;
					campo = prExp.getUriLiteral();
				} else if (leftOp instanceof MemberExpressionImpl) {
					MemberExpressionImpl mbrExp = (MemberExpressionImpl) leftOp;
					campo = mbrExp.getPath().getUriLiteral() + "/" + mbrExp.getProperty().getUriLiteral();
				}
				String valor = rightOp.getUriLiteral();
				String op = expression.getOperator().toUriLiteral();
				String valorNum = valor.substring(1, valor.length() - 1);
				if (StringUtils.isInteger(valorNum)) {
					String filterString = ConversionUtils.applyAlphaConversionForField(campo, op,
							Integer.valueOf(valorNum));
					FilterParserImpl filterParser = new FilterParserImpl((EdmEntityType) uriInfo.getTargetType());
					FilterExpressionImpl filterPadding = (FilterExpressionImpl) filterParser
							.parseFilterString(filterString);
					uriInfo.setFilter(filterPadding);
				}
			}
		} catch (Exception e) {
			log.error("Ops!", e);
		}
		return uriInfo;

	}

	public static UriInfoImpl convertFilterValuesToInternal(GetEntitySetUriInfo uriParserResultView) {
		try {
			UriInfoImpl uriInfo = (UriInfoImpl) uriParserResultView;
			FilterExpression filter = uriInfo.getFilter();
			Map<String, String> customQueryOptions = uriInfo.getCustomQueryOptions();
			String search = customQueryOptions == null ? null : customQueryOptions.get(SEARCH);
			if (filter == null && search == null) {
				return uriInfo;
			}
			if (filter == null) {
				filter = new FilterExpressionImpl("");
			}
			StringBuilder filterString = new StringBuilder();
			FilterExpressionImpl customizedFilter = getCustomizedFilter(filter, uriInfo.getTargetType());
			String customizedExp = customizedFilter.getExpressionString();
			if (!StringUtils.isNullOrEmpty(customizedExp) && !StringUtils.isNullOrEmpty(search)) {
				filterString.append("(");
			}
			filterString.append(customizedExp);
			if (!StringUtils.isNullOrEmpty(customizedExp) && !StringUtils.isNullOrEmpty(search)) {
				filterString.append(")");
			}
			if (!StringUtils.isNullOrEmpty(search)) {
				appendSearchField(uriInfo, filterString, search);
			}
			SQLFilterParser filterParser = new SQLFilterParser((EdmEntityType) uriInfo.getTargetType());
			FilterExpressionImpl newFilter = (FilterExpressionImpl) filterParser.parseFilterString(filterString.toString());
			uriInfo.setFilter(newFilter);
			return uriInfo;
		} catch (Exception e) {
			log.error("Ops!", e);
		}
		return (UriInfoImpl) uriParserResultView;
	}

	private static FilterExpressionImpl getCustomizedFilter(FilterExpression filter, EdmType type)
			throws ExpressionParserException, ExpressionParserInternalError, EdmException {
		String lastCustomizedField = "";
		var stringFilter = filter.getExpressionString();
		var arrayFilter = stringFilter.split(" ");
		var filterSize = arrayFilter.length;
		var customizedFilter = "";
		for (int i = 0; i < filterSize; i++) {
			var filterElement = arrayFilter[i];
			if (filterElement.endsWith("Fiori")) {
				String customizedElement = ConversionUtils.getFioriCustomizedField(filterElement);
				customizedFilter += customizedElement;
				lastCustomizedField = customizedElement;
			} else if (filterElement.endsWith("tipoPeticion") && !EXCLUDED_ENTITIES.contains(type.getName())) {
				customizedFilter += filterElement;
				lastCustomizedField = filterElement;
			} else if (lastCustomizedField.endsWith("tipoPeticion") && !ConversionUtils.isOperator(filterElement)) {
				String customizedValue = filterElement + "d";
				int idClose = customizedValue.indexOf(")");
				if (idClose >= 0) {
					customizedValue = customizedValue.replace(")", "");
					customizedValue += ")";
				}
				customizedFilter += customizedValue;
				lastCustomizedField = "";
			} else if (!Objects.equals(lastCustomizedField, "") && !ConversionUtils.isOperator(filterElement)) {
				Object objValue = ConversionUtils.getFioriCustomizedValue(filterElement);
				String customizedValue = String.valueOf(objValue);
				customizedFilter += customizedValue;
				lastCustomizedField = "";
			} else {
				customizedFilter += filterElement;
			}
			if (i < filterSize - 1) {
				customizedFilter += " ";
			}
		}
		SQLFilterParser filterParser = new SQLFilterParser((EdmEntityType) type);
		FilterExpressionImpl newFilter = (FilterExpressionImpl) filterParser.parseFilterString(customizedFilter.toString());
		return newFilter;
	}

	private static void appendSearchField(UriInfoImpl uriInfo, StringBuilder filterString, String search)
			throws EdmException {
		String fieldSearch = findSearchField(uriInfo);
		if (fieldSearch != null) {
			String searchString = "";
			if (!filterString.toString().equals("")) {
				searchString = StringUtils.SPACE + OPERATOR.AND.toString().toLowerCase() + StringUtils.SPACE;
			}
			searchString += fieldSearch + StringUtils.SPACE + OPERATOR.EQ.toString().toLowerCase() + StringUtils.SPACE
					+ StringUtils.QUOTE + search + StringUtils.QUOTE;
			filterString.append(searchString);
		}
	}

	private static String findSearchField(UriInfoImpl uriInfo) throws EdmException {
		Class<?> entity = EntityUtils.getJpaEntity(uriInfo);
		return PropertyUtils.getSearchField(entity);
	}

	public static String getFioriOrderBy(String originalOrderBy) {
		String response = originalOrderBy;
		try {
			String fioriOrderBy = "";
			var arrayOrderBy = originalOrderBy.split(",");
			var orderBySize = arrayOrderBy.length;
			for (int i = 0; i < orderBySize; i++) {
				var splittedField = arrayOrderBy[i].split(" ");
				var originalField = splittedField[0];
				var fioriField = ConversionUtils.getFioriCustomizedField(originalField);
				fioriOrderBy += fioriField == null ? originalField : fioriField;
				fioriOrderBy += splittedField.length > 1 ? " " + splittedField[1] : "";
				if (i < orderBySize - 1) {
					fioriOrderBy += ",";
				}
			}
			response = fioriOrderBy;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}

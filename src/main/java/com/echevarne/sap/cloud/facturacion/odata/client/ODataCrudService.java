package com.echevarne.sap.cloud.facturacion.odata.client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import com.google.common.collect.Lists;
import com.sap.cloud.sdk.odatav2.connectivity.FilterExpression;
import com.sap.cloud.sdk.odatav2.connectivity.ODataCreateRequest;
import com.sap.cloud.sdk.odatav2.connectivity.ODataCreateRequestBuilder;
import com.sap.cloud.sdk.odatav2.connectivity.ODataDeleteRequestBuilder;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQuery;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryBuilder;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQueryResult;
import com.sap.cloud.sdk.odatav2.connectivity.ODataType;
import com.sap.cloud.sdk.odatav2.connectivity.ODataUpdateRequestBuilder;
import com.sap.cloud.sdk.odatav2.connectivity.UpdateMethod;
import com.sap.cloud.sdk.odatav2.connectivity.internal.ODataExceptionInternal;
import com.sap.cloud.sdk.result.ElementName;
import com.sap.cloud.sdk.service.prov.api.annotations.Key;

import static com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil.OBJECT_REFLECTION_UTIL;

/**
 *
 * Generic class for OData management
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 *
 */
public abstract class ODataCrudService<T, K> {

	private ODataEntityParams param;

	private final Class<T> type;

	private final String keyElementName;

	private final List<String> elementNames;

	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public ODataCrudService() {

		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

		this.keyElementName = getKeyElementName();

		this.elementNames = getAllClassElements();

		this.param = ODataDataSource.getInstance().get(this.getClass());

	}

	/**
	 *
	 * @param t
	 * @throws ODataException
	 */
	public void createWithoutReturn(T t) throws ODataException {

		ODataCreateRequestBuilder.withEntity(getServicePath(), param.getTable()).withBodyAs(t).build()
				.execute(getProvidedClient());
	}

	/**
	 *
	 * @param t
	 * @return
	 * @throws ODataException
	 */
	public Map<String, Object> create(T t) throws ODataException {

		ODataCreateRequest request = ODataCreateRequestBuilder.withEntity(getServicePath(), param.getTable()).withBodyAs(t).build();
		return request.execute(getProvidedClient()).asMap();

	}

	public void update(T t) throws ODataException, IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException, InvocationTargetException {
		Map<String, Object> keys = getKeyMap(t);

		ODataUpdateRequestBuilder.withEntity(getServicePath(), param.getTable(), keys).withBodyAs(t).build()
				.execute(UpdateMethod.PATCH, getProvidedClient());

	}

	public List<T> fetchAll() throws IllegalArgumentException, ODataException {

		ODataQuery query = ODataQueryBuilder.withEntity(getServicePath(), param.getTable()).select(this.elementNames)
				.build();
		ODataQueryResult queryResult = query.execute(getProvidedClient());
		final List<T> list = queryResult.asList(type);

		return list;

	}

	public T fetchById(K id) throws IllegalArgumentException, ODataException {

		FilterExpression filter = new FilterExpression(this.keyElementName, "eq", ODataType.of(id));

		final List<T> list = ODataQueryBuilder.withEntity(getServicePath(), param.getTable()).select(this.elementNames)
				.filter(filter).build().execute(getProvidedClient()).asList(type);

		return list.size() > 0 ? list.get(0) : null;
	}

	public List<T> fetchByIdsIn(List<K> ids) throws IllegalArgumentException, ODataException {

		FilterExpression filter = null;
		for (K k : ids) {
			if (filter == null) {
				filter = new FilterExpression(this.keyElementName, "eq", ODataType.of(k));
			} else {
				filter = filter.or(new FilterExpression(this.keyElementName, "eq", ODataType.of(k)));
			}
		}

		final List<T> list = ODataQueryBuilder.withEntity(getServicePath(), param.getTable()).select(this.elementNames)
				.filter(filter).build().execute(getProvidedClient()).asList(type);

		return list;
	}

	public List<T> fetchPage(int skip, int top) throws IllegalArgumentException, ODataException {

		final List<T> page = ODataQueryBuilder.withEntity(getServicePath(), param.getTable()).select(this.elementNames)
				.skip(skip).top(top).build().execute(getProvidedClient()).asList(type);

		return page;
	}

	public void delete(T t) throws ODataException, IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException, InvocationTargetException {
		Map<String, Object> keys = getKeyMap(t);

		ODataDeleteRequestBuilder.withEntity(getServicePath(), param.getTable(), keys).build()
				.execute(getProvidedClient());
	}

	public void deleteById(K id) throws ODataException, IllegalArgumentException, IllegalAccessException {
		Map<String, Object> keys = getKeyMapById(id);
		try {
			ODataDeleteRequestBuilder.withEntity(getServicePath(), param.getTable(), keys).build()
					.execute(getProvidedClient());
		} catch (ODataExceptionInternal e) {

		}
	}

	private String getServicePath() {
		return param.getProtocol() + "://" + param.getHost() + param.getService();
	}

	private HttpClient getProvidedClient() {
		Header auth = new BasicHeader(HttpHeaders.AUTHORIZATION, param.getAuthorization());
		List<Header> headers = Lists.newArrayList(auth);
		return HttpClients.custom().setDefaultHeaders(headers).build();
	}

	/**
	 *
	 * @return
	 */
	private List<String> getAllClassElements() {
		List<String> elementNames = new ArrayList<>();

		Field[] fields = this.type.getDeclaredFields();
		for (Field f : fields) {
			ElementName elementName = f.getDeclaredAnnotation(ElementName.class);
			if (elementName != null) {
				elementNames.add(elementName.value());
			}
		}

		return elementNames;
	}

	/**
	 *
	 * @return
	 */
	private String getKeyElementName() {

		String keyElementName = null;

		Field[] fields = type.getDeclaredFields();
		for (Field f : fields) {
			Key key = f.getDeclaredAnnotation(Key.class);
			ElementName elementName = f.getDeclaredAnnotation(ElementName.class);
			if (key != null && elementName != null) {
				keyElementName = elementName.value();
				break;
			}
		}

		return keyElementName;
	}

	private Map<String, Object> getKeyMap(T t) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
		Map<String, Object> keys = new HashMap<>();
		Object keyElementValue = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(t, this.keyElementName);

		keys.put(this.keyElementName, keyElementValue);

		return keys;
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	private Map<String, Object> getKeyMapById(K id) {
		Map<String, Object> keys = new HashMap<>();
		keys.put(this.keyElementName, id);

		return keys;
	}

}

package com.echevarne.sap.cloud.facturacion.odata.processor.parser;

import com.echevarne.sap.cloud.facturacion.util.ConversionUtils;

import org.apache.olingo.odata2.api.edm.EdmElement;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.expression.BinaryExpression;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.LiteralExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodExpression;
import org.apache.olingo.odata2.api.uri.expression.PropertyExpression;
import org.apache.olingo.odata2.api.uri.expression.UnaryExpression;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

public class LiteralExpressionParser {

    public static final String EMPTY = "";
    public static final ThreadLocal<Integer> methodFlag = new ThreadLocal<Integer>();
    public static final Character[] EMPTY_CHARACTER_ARRAY = new Character[0];

    public static void formatLiteralValues(CommonExpression filter) throws ODataException {
        formatLiteralValues(filter, null, null);
    }

    public static void formatLiteralValues(CommonExpression filter, EdmMapping edmMapping, PropertyExpression property) throws ODataException {
        switch (filter.getKind()) {
        case UNARY:
            UnaryExpression unaryExpression = (UnaryExpression) filter;
            formatLiteralValues(unaryExpression.getOperand(), edmMapping, null);
            break;
        case FILTER:
            formatLiteralValues(((FilterExpression) filter).getExpression(), edmMapping, null);
            break;
        case BINARY:
            BinaryExpression binaryExpression = (BinaryExpression) filter;
            formatLiteralValues(binaryExpression.getLeftOperand(), edmMapping, null);
            edmMapping = getEdmMapping(binaryExpression);
            formatLiteralValues(binaryExpression.getRightOperand(), edmMapping, getPropertyForBinary(binaryExpression.getLeftOperand()));
            break;
        case PROPERTY:
            break;
        case MEMBER:
            break;
        case LITERAL:
            LiteralExpression literal = (LiteralExpression) filter;
            EdmSimpleType literalType = (EdmSimpleType) literal.getEdmType();
            EdmLiteral uriLiteral = EdmSimpleTypeKind.parseUriLiteral(literal.getUriLiteral());
            Class<?> edmMap = edmMapping != null ? ((JPAEdmMappingImpl) edmMapping).getJPAType() : null;
            changeLiteral(uriLiteral, literalType, edmMap, property);
            break;
        case METHOD:
            MethodExpression methodExpression = (MethodExpression) filter;
            formatLiteralValues(methodExpression.getParameters().get(0), edmMapping, null);
            if (methodExpression.getParameterCount() > 1)
                formatLiteralValues(methodExpression.getParameters().get(1), edmMapping, null);
            if (methodExpression.getParameterCount() > 2)
                formatLiteralValues(methodExpression.getParameters().get(2), edmMapping, null);
            break;
        default:
            break;
        }
    }

    private static PropertyExpression getPropertyForBinary(CommonExpression commonExpression) {
        if (commonExpression != null && commonExpression instanceof PropertyExpression) {
            return (PropertyExpression) commonExpression;
        }
        return null;
    }

    private static EdmMapping getEdmMapping(BinaryExpression binaryExpression) throws EdmException {
        if (binaryExpression != null && binaryExpression.getLeftOperand() instanceof PropertyExpression) {
            PropertyExpression left = (PropertyExpression) binaryExpression.getLeftOperand();
            if (left != null && left.getEdmProperty() instanceof EdmElement) {
                EdmElement property = (EdmElement) left.getEdmProperty();
                return property.getMapping();
            }
        }
        return null;
    }

    private static void changeLiteral(EdmLiteral uriLiteral, EdmSimpleType edmSimpleType,
            Class<?> edmMappedType, PropertyExpression property) throws ODataJPARuntimeException {
        String internalValue = ConversionUtils.applyAlphaConversionForField(property.getUriLiteral(), uriLiteral.getLiteral());
    }

}

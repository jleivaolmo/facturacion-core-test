package com.echevarne.sap.cloud.facturacion.odata.processor.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.olingo.odata2.api.edm.EdmElement;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.expression.BinaryExpression;
import org.apache.olingo.odata2.api.uri.expression.BinaryOperator;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionKind;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.LiteralExpression;
import org.apache.olingo.odata2.api.uri.expression.MemberExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodOperator;
import org.apache.olingo.odata2.api.uri.expression.PropertyExpression;
import org.apache.olingo.odata2.api.uri.expression.UnaryExpression;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

import com.echevarne.sap.cloud.facturacion.util.DateUtils;

public class QuerySQLExpressionParser {

    public static final String EMPTY = ""; //$NON-NLS-1$
    public static final ThreadLocal<Integer> methodFlag = new ThreadLocal<Integer>();
    public static final Character[] EMPTY_CHARACTER_ARRAY = new Character[0];

    /**
     * This method returns the parsed where condition corresponding to the filter
     * input in the user query.
     *
     * @param whereExpression
     * @param concurrentHashMap
     * @param index
     *
     * @return Parsed where condition String
     * @throws ODataException
     */
    public static Expression<?> parseToJPAExpression(final CommonExpression whereExpression, CriteriaBuilder cb,
            Root<?> root, EdmMapping edmMapping) throws ODataException {
        return parseToJPAExpression(whereExpression, cb, root, 1, new ConcurrentHashMap<Integer, Object>(), edmMapping);
    }

    public static Expression<?> parseToJPAExpression(final CommonExpression whereExpression, CriteriaBuilder cb,
            Root<?> root, int index, Map<Integer, Object> positionalParameters, EdmMapping edmMapping)
            throws ODataException {
        switch (whereExpression.getKind()) {
        case FILTER:
            return parseToJPAExpression(((FilterExpression) whereExpression).getExpression(), cb, root, index,
                    positionalParameters, edmMapping);
        case UNARY:
            return mapUnary((UnaryExpression) whereExpression, cb, root, index, positionalParameters, edmMapping);
        case BINARY:
            return mapBinary((BinaryExpression) whereExpression, cb, root, index, positionalParameters, edmMapping);
        case PROPERTY:
            return mapProperty(whereExpression, cb, root, index, positionalParameters, edmMapping);
        case MEMBER:
            return mapMember(whereExpression, cb, root, index, positionalParameters, edmMapping);
        case LITERAL:
            return mapLiteral((LiteralExpression) whereExpression, cb, root, index, positionalParameters, edmMapping);
        case METHOD:
            return mapMethod((MethodExpression) whereExpression, cb, root, index, positionalParameters, edmMapping);
        default:
            throw new ODataNotImplementedException();
        }
    }

    private static Path<?> mapProperty(CommonExpression whereExpression, CriteriaBuilder cb, Root<?> root, int index,
            Map<Integer, Object> positionalParameters, EdmMapping edmMapping) throws ODataException {
        return root.get(whereExpression.getUriLiteral());
    }

    private static Predicate mapUnary(final UnaryExpression unaryExpression, CriteriaBuilder cb, Root<?> root,
            int index, Map<Integer, Object> positionalParameters, EdmMapping edmMapping) throws ODataException {
        final Expression<?> operand = parseToJPAExpression(unaryExpression.getOperand(), cb, root, index,
                positionalParameters, edmMapping);
        switch (unaryExpression.getOperator()) {
        case NOT:

        case MINUS:

        default:
            throw new ODataNotImplementedException();
        }
    }

    private static Path<?> mapMember(CommonExpression whereExpression, CriteriaBuilder cb, Root<?> root, int index,
            Map<Integer, Object> positionalParameters, EdmMapping edmMapping) throws ODataException {
        String memberExpStr = EMPTY;
        int i = 0;
        MemberExpression member = null;
        CommonExpression tempExp = whereExpression;
        while (tempExp != null && tempExp.getKind() == ExpressionKind.MEMBER) {
            member = (MemberExpression) tempExp;
            if (i > 0) {
                memberExpStr = JPQLStatement.DELIMITER.PERIOD + memberExpStr;
            }
            i++;
            memberExpStr = getPropertyName(member.getProperty()) + memberExpStr;
            tempExp = member.getPath();
        }
        memberExpStr = getPropertyName(tempExp) + JPQLStatement.DELIMITER.PERIOD + memberExpStr;
        return root.get(memberExpStr);
    }

    private static Expression<?> mapLiteral(final LiteralExpression literal, CriteriaBuilder cb, Root<?> root,
            int index, Map<Integer, Object> positionalParameters, EdmMapping edmMapping) throws ODataException {
        final EdmSimpleType literalType = (EdmSimpleType) literal.getEdmType();
        EdmLiteral uriLiteral = EdmSimpleTypeKind.parseUriLiteral(literal.getUriLiteral());
        Class<?> edmMap = edmMapping != null ? ((JPAEdmMappingImpl) edmMapping).getJPAType() : null;
        return comparingExpression(uriLiteral.getLiteral(), cb, literalType, edmMap);
    }

    private static Predicate mapMethod(final MethodExpression methodExpression, CriteriaBuilder cb, Root<?> root,
            int index, Map<Integer, Object> positionalParameters, EdmMapping edmMapping) throws ODataException {
        Expression<?> first = parseToJPAExpression(methodExpression.getParameters().get(0), cb, root,
                getIndexValue(index, positionalParameters), positionalParameters, edmMapping);
        Expression<?> second = methodExpression.getParameterCount() > 1
                ? parseToJPAExpression(methodExpression.getParameters().get(1), cb, root,
                        getIndexValue(index, positionalParameters), positionalParameters, edmMapping)
                : null;
        Expression<?> third = methodExpression.getParameterCount() > 2
                ? parseToJPAExpression(methodExpression.getParameters().get(2), cb, root,
                        getIndexValue(index, positionalParameters), positionalParameters, edmMapping)
                : null;
        switch (methodExpression.getMethod()) {
        case SUBSTRING:
        case SUBSTRINGOF:
            return cb.like((Expression<String>) second, String.format("%%%s%%", methodExpression.getParameters().get(0).getUriLiteral().replace("'", "")));
        case TOLOWER:
        case TOUPPER:
        case STARTSWITH:
            return cb.like((Expression<String>) second, String.format("%s%%", methodExpression.getParameters().get(0).getUriLiteral().replace("'", "")));
        case ENDSWITH:
            return cb.like((Expression<String>) second, String.format("%%%s", methodExpression.getParameters().get(0).getUriLiteral().replace("'", "")));
        default:
            throw new ODataNotImplementedException();
        }
    }

    private static Predicate mapBinary(final BinaryExpression binaryExpression, CriteriaBuilder cb, Root<?> root,
            int index, Map<Integer, Object> positionalParameters, EdmMapping edmMapping) throws ODataException {
        MethodOperator operator = null;
        if (binaryExpression.getLeftOperand().getKind() == ExpressionKind.METHOD) {
            operator = ((MethodExpression) binaryExpression.getLeftOperand()).getMethod();
        }
        if (operator != null && ((binaryExpression.getOperator() == BinaryOperator.EQ)
                || (binaryExpression.getOperator() == BinaryOperator.NE))) {
            if (operator == MethodOperator.SUBSTRINGOF) {
                methodFlag.set(1);
            }
        }
        final Expression<?> left = parseToJPAExpression(binaryExpression.getLeftOperand(), cb, root,
                getIndexValue(index, positionalParameters), positionalParameters, edmMapping);
        // edmMapping = getEdmMapping(binaryExpression);
        final Expression<?> right = parseToJPAExpression(binaryExpression.getRightOperand(), cb, root,
                getIndexValue(index, positionalParameters), positionalParameters, edmMapping);
        // Special handling for STARTSWITH and ENDSWITH method expression
        // if (operator != null && (operator == MethodOperator.STARTSWITH || operator ==
        // MethodOperator.ENDSWITH)) {
        // if (!binaryExpression.getOperator().equals(BinaryOperator.EQ)
        // && !(binaryExpression.getRightOperand() instanceof LiteralExpression)
        // && ("true".equals(right) || "false".equals(right))) {
        // throw
        // ODataJPARuntimeException.throwException(ODataJPARuntimeException.OPERATOR_EQ_NE_MISSING
        // .addContent(binaryExpression.getOperator().toString()), null);
        // } else if (binaryExpression.getOperator().equals(BinaryOperator.EQ)) {
        // if ("false".equals(right)) {
        // return JPQLStatement.DELIMITER.PARENTHESIS_LEFT + left.replaceFirst("LIKE",
        // "NOT LIKE")
        // + JPQLStatement.DELIMITER.SPACE + JPQLStatement.DELIMITER.PARENTHESIS_RIGHT;
        // } else if ("true".equals(right)) {
        // return JPQLStatement.DELIMITER.PARENTHESIS_LEFT + left +
        // JPQLStatement.DELIMITER.SPACE
        // + JPQLStatement.DELIMITER.PARENTHESIS_RIGHT;
        // }
        // }
        // }
        return mapBinaryOperator(binaryExpression, cb, root, left, right, edmMapping);

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

    private static Predicate mapBinaryOperator(BinaryExpression binaryExpression, CriteriaBuilder cb, Root<?> root,
            Expression<?> left, Expression<?> right, EdmMapping edmMapping) throws ODataNotImplementedException {
        switch (binaryExpression.getOperator()) {
        case AND:
            return cb.and((Predicate) left, (Predicate) right);
        case OR:
            return cb.or((Predicate) left, (Predicate) right);
        case EQ:
            EdmSimpleType type = (EdmSimpleType) binaryExpression.getLeftOperand().getEdmType();
            if (EdmSimpleTypeKind.String.getEdmSimpleTypeInstance().isCompatible(type)) {
                if (edmMapping == null
                        || (edmMapping != null && !(((JPAEdmMappingImpl) edmMapping).getJPAType()).isEnum())) {
                    return cb.equal(left, right);
                }
            }
            return cb.equal(left, right);
        case NE:
            EdmSimpleType edmType = (EdmSimpleType) binaryExpression.getLeftOperand().getEdmType();
            if (EdmSimpleTypeKind.String.getEdmSimpleTypeInstance().isCompatible(edmType)) {
                return cb.notEqual(left, right);
            }
            return cb.notEqual(left, right);
        case LT:
            return cb.lessThan((Expression) left, (Expression) right);
        case LE:
            return cb.lessThanOrEqualTo((Expression) left, (Expression) right);
        case GT:
            return cb.greaterThan((Expression) left, (Expression) right);
        case GE:
            return cb.greaterThanOrEqualTo((Expression) left, (Expression) right);
        case PROPERTY_ACCESS:
            throw new ODataNotImplementedException();
        default:
            throw new ODataNotImplementedException();

        }
    }

    private static int getIndexValue(int index, Map<Integer, Object> map) {
        if (map != null && !map.isEmpty()) {
            for (Entry<Integer, Object> entry : map.entrySet()) {
                index = entry.getKey();
            }
            return index + 1;
        } else {
            return index;
        }
    }

    /**
     * This method converts String to Byte array
     *
     * @param uriLiteral
     */
    public static Byte[] toByteArray(String uriLiteral) {
        int length = uriLiteral.length();
        if (length == 0) {
            return new Byte[0];
        }
        byte[] byteValues = uriLiteral.getBytes();
        final Byte[] result = new Byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = new Byte(byteValues[i]);
        }
        return result;
    }

    /**
     * This method escapes the wildcards
     *
     * @param first
     */
    private static String updateValueIfWildcards(String value) {
        if (value != null) {
            value = value.replace("\\", "\\\\");
            value = value.replace("%", "\\%");
            // value = value.replace("_", "\\_");
        }
        return value;
    }

    /**
     * This method evaluated the where expression for read of an entity based on the
     * keys specified in the query.
     *
     * @param keyPredicates
     * @return the evaluated where expression
     */

    public static Expression<?> parseKeyPredicates(final List<KeyPredicate> keyPredicates, CriteriaBuilder cb,
            Root<?> root) throws ODataJPARuntimeException {
        Predicate keysExpression = null;
        for (KeyPredicate keyPredicate : keyPredicates) {
            if(keysExpression != null){
                keysExpression = cb.and(keysExpression, convertKeyToExpression(keyPredicate, cb, root));
            }else{
                keysExpression = convertKeyToExpression(keyPredicate, cb, root);
            }
        }
        return keysExpression;
    }

    public static Predicate convertKeyToExpression(final KeyPredicate keyPredicate, CriteriaBuilder cb,
            Root<?> root) throws ODataJPARuntimeException {
        try {
            String propertyName = keyPredicate.getProperty().getMapping().getInternalName();
            EdmSimpleType edmSimpleType = (EdmSimpleType) keyPredicate.getProperty().getType();
            Class<?> edmMappedType = ((JPAEdmMappingImpl) keyPredicate.getProperty().getMapping()).getJPAType();
            Expression<?> literal = comparingExpression(keyPredicate.getLiteral(), cb, edmSimpleType, edmMappedType);
            return cb.equal(root.get(propertyName), literal);
        } catch (EdmException e) {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()),
                    e);
        }
    }

    /**
     * Convert char array to Character Array
     */
    public static Character[] toCharacterArray(char[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHARACTER_ARRAY;
        }
        final Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = new Character(array[i]);
        }
        return result;
    }

    private static Expression<?> comparingExpression(String uriLiteral, CriteriaBuilder cb,
            final EdmSimpleType edmSimpleType, Class<?> edmMappedType) throws ODataJPARuntimeException {

        if (EdmSimpleTypeKind.String.getEdmSimpleTypeInstance().isCompatible(edmSimpleType)
                || EdmSimpleTypeKind.Guid.getEdmSimpleTypeInstance().isCompatible(edmSimpleType)) {
            uriLiteral = uriLiteral.replaceAll("'", "''");
            uriLiteral = updateValueIfWildcards(uriLiteral);
            Object literal = expressionForString(uriLiteral, edmMappedType);
            return cb.literal(literal);
        } else if (EdmSimpleTypeKind.DateTime.getEdmSimpleTypeInstance().isCompatible(edmSimpleType)
                || EdmSimpleTypeKind.DateTimeOffset.getEdmSimpleTypeInstance().isCompatible(edmSimpleType)) {
            try {
                Calendar datetime = (Calendar) edmSimpleType.valueOfString(uriLiteral, EdmLiteralKind.DEFAULT, null,
                        edmSimpleType.getDefaultType());
                String yearValue = String.format("%02d", datetime.get(Calendar.YEAR));
                String monthValue = String.format("%02d", datetime.get(Calendar.MONTH) + 1);
                String dayValue = String.format("%02d", datetime.get(Calendar.DAY_OF_MONTH));
                uriLiteral = yearValue + JPQLStatement.DELIMITER.HYPHEN + monthValue + JPQLStatement.DELIMITER.HYPHEN
                        + dayValue;
                return cb.literal(DateUtils.convertToCalendar(Date.valueOf(uriLiteral)));
            } catch (EdmSimpleTypeException e) {
                throw ODataJPARuntimeException
                        .throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
            }
        } else if (EdmSimpleTypeKind.Time.getEdmSimpleTypeInstance().isCompatible(edmSimpleType)) {
            try {
                Calendar time = (Calendar) edmSimpleType.valueOfString(uriLiteral, EdmLiteralKind.DEFAULT, null,
                        edmSimpleType.getDefaultType());
                String hourValue = String.format("%02d", time.get(Calendar.HOUR_OF_DAY));
                String minValue = String.format("%02d", time.get(Calendar.MINUTE));
                String secValue = String.format("%02d", time.get(Calendar.SECOND));
                uriLiteral = hourValue + JPQLStatement.DELIMITER.COLON + minValue + JPQLStatement.DELIMITER.COLON
                        + secValue;
                return cb.literal(Time.valueOf(uriLiteral));
            } catch (EdmSimpleTypeException e) {
                throw ODataJPARuntimeException
                        .throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
            }
        } else if (EdmSimpleTypeKind.Boolean.getEdmSimpleTypeInstance().isCompatible(edmSimpleType)) {
            return cb.literal(Boolean.valueOf(uriLiteral));
        } else {
            return expressionForNumbers(uriLiteral, cb, edmSimpleType);
        }

    }

    private static Expression<?> expressionForNumbers(String uriLiteral, CriteriaBuilder cb,
            EdmSimpleType edmSimpleType) {
        Class<? extends Object> type = edmSimpleType.getDefaultType();
        if (Long.class.equals(type) || long.class.equals(type)) {
            return cb.literal(Long.valueOf(uriLiteral));
        } else if (Double.class.equals(type) || double.class.equals(type)) {
            return cb.literal(Double.valueOf(uriLiteral));
        } else if (Integer.class.equals(type) || int.class.equals(type)) {
            return cb.literal(Integer.valueOf(uriLiteral));
        } else if (Byte.class.equals(type) || byte.class.equals(type)) {
            return cb.literal(Byte.valueOf(uriLiteral));
        } else if (Byte[].class.equals(type) || byte[].class.equals(type)) {
            return cb.literal(toByteArray(uriLiteral));
        } else if (Short.class.equals(type) || short.class.equals(type)) {
            return cb.literal(Short.valueOf(uriLiteral));
        } else if (BigDecimal.class.equals(type)) {
            return cb.literal(new BigDecimal(uriLiteral));
        } else if (BigInteger.class.equals(type)) {
            return cb.literal(new BigInteger(uriLiteral));
        } else if (Float.class.equals(type) || float.class.equals(type)) {
            return cb.literal(Float.valueOf(uriLiteral));
        }
        return cb.literal(uriLiteral);
    }

    @SuppressWarnings("unchecked")
    private static Object expressionForString(String uriLiteral, Class<?> edmMappedType) {
        if (edmMappedType.equals(char[].class)) {
            return uriLiteral.toCharArray();
        } else if (edmMappedType.equals(char.class)) {
            return uriLiteral.charAt(0);
        } else if (edmMappedType.equals(Character[].class)) {
            char[] charArray = uriLiteral.toCharArray();
            Character[] charObjectArray = toCharacterArray(charArray);
            return charObjectArray;
        } else if (edmMappedType.equals(Character.class)) {
            return (Character) uriLiteral.charAt(0);
        } else if (edmMappedType.equals(UUID.class)) {
            return UUID.fromString(uriLiteral);
        } else if (edmMappedType.isEnum()) {
            Class enCl = (Class<? extends Enum>) edmMappedType;
            return Enum.valueOf(enCl, (String) uriLiteral);
        } else {
            return String.valueOf(uriLiteral);
        }
    }

    private static String getPropertyName(final CommonExpression whereExpression)
            throws EdmException, ODataJPARuntimeException {
        EdmTyped edmProperty = ((PropertyExpression) whereExpression).getEdmProperty();
        EdmMapping mapping;
        if (edmProperty instanceof EdmNavigationProperty) {
            EdmNavigationProperty edmNavigationProperty = (EdmNavigationProperty) edmProperty;
            mapping = edmNavigationProperty.getMapping();
        } else if (edmProperty instanceof EdmProperty) {
            EdmProperty property = (EdmProperty) edmProperty;
            mapping = property.getMapping();
        } else {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL, null);
        }

        return mapping != null ? mapping.getInternalName() : edmProperty.getName();
    }

}

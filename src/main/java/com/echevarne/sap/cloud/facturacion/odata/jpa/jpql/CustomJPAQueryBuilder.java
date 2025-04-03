package com.echevarne.sap.cloud.facturacion.odata.jpa.jpql;

import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.swing.SortOrder;

import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.CountField;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.CustomSelectEntity;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.FetchField;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.GroupByField;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.OverrideEntity;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.SumField;
import com.echevarne.sap.cloud.facturacion.odata.processor.parser.QuerySQLExpressionParser;

import org.apache.commons.collections.CollectionUtils;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.SelectItem;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderExpression;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLJoinContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLJoinSelectSingleContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLSelectContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLSelectSingleContextView;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomJPAQueryBuilder {

    /***
     *
     * P U B L I C M E T H O D S
     *
     */
    public Query buildQuery(GetEntitySetUriInfo uriInfo, EntityManager em) throws ODataException {
        Class<?> ec = getJpaEntity(uriInfo);
        if (hasOverride(ec)) {
            if(!(hasSelectAnnotation(ec) && uriInfo.getSelect().isEmpty())){
                return constructOverrideQuery(ec, em, uriInfo.getFilter(), uriInfo.getKeyPredicates(),
                        uriInfo.getOrderBy(), uriInfo.getSelect(), uriInfo.getTargetEntitySet().getEntityType().getMapping());
            }
        }
        JPQLContextType contextType;
        if (!uriInfo.getStartEntitySet().getName().equals(uriInfo.getTargetEntitySet().getName())) {
            contextType = JPQLContextType.JOIN;
        } else {
            contextType = JPQLContextType.SELECT;
        }
        CustomJPQLContext jpqlContext = CustomJPQLContext.createBuilder(contextType, uriInfo).build();
        CustomJPQLStatement jpqlStatement = CustomJPQLStatement.createBuilder(jpqlContext).build();
        Query query = em.createQuery(jpqlStatement.toString(), ec);
        getParameterizedQuery(contextType, jpqlContext, jpqlStatement, query);
        return query;
    }

    public Query buildQuery(GetEntitySetCountUriInfo uriInfo, EntityManager em) throws ODataException {
        Class<?> ec = getJpaEntity(uriInfo);
        if (hasOverride(ec)) {
            return constructOverrideCountQuery(ec, em, uriInfo.getFilter(), uriInfo.getKeyPredicates());
        } else {
            JPQLContextType contextType;
            if (!uriInfo.getStartEntitySet().getName().equals(uriInfo.getTargetEntitySet().getName())) {
                contextType = JPQLContextType.JOIN;
            } else {
                contextType = JPQLContextType.SELECT;
            }
            CustomJPQLContext jpqlContext = CustomJPQLContext.createBuilder(contextType, uriInfo).build();
            CustomJPQLStatement jpqlStatement = CustomJPQLStatement.createBuilder(jpqlContext).build();
            Query query = em.createQuery(jpqlStatement.toString(), ec);
            getParameterizedQuery(contextType, jpqlContext, jpqlStatement, query);
            return query;
        }
    }

    public Query buildQuery(GetEntityCountUriInfo uriInfo, EntityManager em) throws ODataException {
        Class<?> ec = getJpaEntity(uriInfo);
        if (hasOverride(ec)) {
            return constructOverrideCountQuery(ec, em, uriInfo.getFilter(), uriInfo.getKeyPredicates());
        } else {
            JPQLContextType contextType;
            if (!uriInfo.getStartEntitySet().getName().equals(uriInfo.getTargetEntitySet().getName())) {
                contextType = JPQLContextType.JOIN;
            } else {
                contextType = JPQLContextType.SELECT;
            }
            CustomJPQLContext jpqlContext = CustomJPQLContext.createBuilder(contextType, uriInfo).build();
            CustomJPQLStatement jpqlStatement = CustomJPQLStatement.createBuilder(jpqlContext).build();
            Query query = em.createQuery(jpqlStatement.toString(), ec);
            getParameterizedQuery(contextType, jpqlContext, jpqlStatement, query);
            return query;
        }
    }

    public Query buildQuery(GetEntityUriInfo uriInfo, EntityManager em) throws ODataException {
        Class<?> ec = getJpaEntity(uriInfo);
        if (hasOverride(ec)) {
            return constructOverrideQuery(ec, em, uriInfo.getFilter(), uriInfo.getKeyPredicates(), null, uriInfo.getSelect(),
                    uriInfo.getTargetEntitySet().getEntityType().getMapping());
        } else {
            JPQLContextType contextType;
            if (!uriInfo.getStartEntitySet().getName().equals(uriInfo.getTargetEntitySet().getName())) {
                contextType = JPQLContextType.JOIN;
            } else {
                contextType = JPQLContextType.SELECT;
            }
            CustomJPQLContext jpqlContext = CustomJPQLContext.createBuilder(contextType, uriInfo).build();
            CustomJPQLStatement jpqlStatement = CustomJPQLStatement.createBuilder(jpqlContext).build();
            Query query = em.createQuery(jpqlStatement.toString(), ec);
            getParameterizedQuery(contextType, jpqlContext, jpqlStatement, query);
            return query;
        }
    }

    public Query buildConstructQuery(JPQLContextType contextType, Class<?> ec, EntityManager em,
            FilterExpression filters, List<KeyPredicate> predicates, OrderByExpression order) throws ODataException {
        if (hasOverride(ec)) {
            return constructOverrideQuery(ec, em, filters, predicates, order);
        }
        return null;
    }

    /***
     *
     * I N T E R N A L M E T H O D S
     *
     */
    private Query constructOverrideCountQuery(Class<?> ec, EntityManager em, FilterExpression filters,
            List<KeyPredicate> predicates) throws ODataException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        OverrideEntity annotation = ec.getDeclaredAnnotation(OverrideEntity.class);
        CriteriaQuery<?> cq = cb.createQuery(ec);
        Root<?> root = cq.from(annotation.entityClass());
        fillSelectCountFields(ec, cq, cb, root);
        fillGroupByFields(ec, cq, cb, root);
        fillWhereFields(ec, cq, cb, root, filters, predicates, null);
        fillFetchFields(ec, cq, cb, root);
        TypedQuery<?> query = em.createQuery(cq);
        return query;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void fillSelectCountFields(Class<?> ec, CriteriaQuery cq, CriteriaBuilder cb, Root<?> root) {
        cq.select(cb.count(root));
    }

    private Query constructOverrideQuery(Class<?> ec, EntityManager em, FilterExpression filters,
            List<KeyPredicate> predicates, OrderByExpression order) throws ODataException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        OverrideEntity annotation = ec.getDeclaredAnnotation(OverrideEntity.class);
        CriteriaQuery<?> cq = cb.createQuery(ec);
        // CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<?> root = cq.from(annotation.entityClass());
        fillSelectFields(ec, cq, cb, root);
        fillOrderByFields(ec, cq, cb, root, order);
        fillGroupByFields(ec, cq, cb, root);
        fillWhereFields(ec, cq, cb, root, filters, predicates, null);
        fillFetchFields(ec, cq, cb, root);
        TypedQuery<?> query = em.createQuery(cq);
        return query;
    }

    private Query constructOverrideQuery(Class<?> ec, EntityManager em, FilterExpression filters,
    List<KeyPredicate> predicates, OrderByExpression order, List<SelectItem> select, EdmMapping edmMapping) throws ODataException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        OverrideEntity annotation = ec.getDeclaredAnnotation(OverrideEntity.class);
        TypedQuery<?> query;
        if(hasSelectAnnotation(ec) && !select.isEmpty()){
            CriteriaQuery<Tuple> cqt = cb.createTupleQuery();
            Root<?> root = cqt.from(annotation.entityClass());
            fillSelectFields(ec, cqt, cb, root, select);
            fillOrderByFields(ec, cqt, cb, root, order);
            fillGroupByFields(ec, cqt, cb, root, select);
            fillWhereFields(ec, cqt, cb, root, filters, predicates, edmMapping);
            fillFetchFields(ec, cqt, cb, root);
            query = em.createQuery(cqt);
        } else {
            CriteriaQuery<?> cq = cb.createQuery(ec);
            Root<?> root = cq.from(annotation.entityClass());
            fillSelectFields(ec, cq, cb, root);
            fillOrderByFields(ec, cq, cb, root, order);
            fillGroupByFields(ec, cq, cb, root);
            fillWhereFields(ec, cq, cb, root, filters, predicates, edmMapping);
            fillFetchFields(ec, cq, cb, root);
            query = em.createQuery(cq);
        }
        return query;
    }

    private void fillFetchFields(Class<?> ec, CriteriaQuery<?> cq, CriteriaBuilder cb, Root<?> root) {
        Field[] fields = ec.getDeclaredFields();
        for (Field field : fields) {
            if (field.getDeclaredAnnotation(FetchField.class) != null) {
                root.fetch(field.getName());
            }
        }
    }

    private void fillWhereFields(Class<?> ec, CriteriaQuery<?> cq, CriteriaBuilder cb, Root<?> root,
            FilterExpression filters, List<KeyPredicate> predicates, EdmMapping edmMapping) throws ODataException {
        if (filters == null && CollectionUtils.isEmpty(predicates))
            return;
        Predicate where = null;
        if (CollectionUtils.isNotEmpty(predicates)) {
            where = (Predicate) QuerySQLExpressionParser.parseKeyPredicates(predicates, cb, root);
        }
        if (filters != null) {
            if (where == null)
                where = (Predicate) QuerySQLExpressionParser.parseToJPAExpression(filters.getExpression(), cb, root, edmMapping);
            else
                where = cb.and(where,
                        (Predicate) QuerySQLExpressionParser.parseToJPAExpression(filters.getExpression(), cb, root, edmMapping));
        }
        cq.where(where);
    }

    private void fillGroupByFields(Class<?> ec, CriteriaQuery<?> cq, CriteriaBuilder cb, Root<?> root) {
        Field[] fields = ec.getDeclaredFields();
        List<Expression<?>> listGroup = new ArrayList<Expression<?>>();
        for (Field field : fields) {
            if (field.getDeclaredAnnotation(GroupByField.class) != null) {
                GroupByField group = field.getDeclaredAnnotation(GroupByField.class);
                listGroup.add(root.get(group.fieldName()));
            }
        }
        cq.groupBy(listGroup);
    }

    private void fillGroupByFields(Class<?> ec, CriteriaQuery<?> cq, CriteriaBuilder cb, Root<?> root, List<SelectItem> select) {
        Field[] fields = ec.getDeclaredFields();
        List<Expression<?>> listGroup = new ArrayList<Expression<?>>();
        for (Field field : fields) {
            if (fieldInSelection(select, field)) {
                if (field.getDeclaredAnnotation(GroupByField.class) != null) {
                    GroupByField group = field.getDeclaredAnnotation(GroupByField.class);
                    listGroup.add(root.get(group.fieldName()));
                }
            }
        }
        cq.groupBy(listGroup);
    }

    private void fillOrderByFields(Class<?> ec, CriteriaQuery<?> cq, CriteriaBuilder cb, Root<?> root,
            OrderByExpression orders) {
        if (orders == null)
            return;
        List<Order> orderList = cq.getOrderList();
        if (CollectionUtils.isEmpty(orderList)) {
            orderList = new ArrayList<>();
        }
        Field[] fields = ec.getDeclaredFields();
        for (Field field : fields) {
            for (OrderExpression order : orders.getOrders()) {
                if(field.getName().equals(order.getExpression().getUriLiteral())){
                    Path<?> fieldPath = root.get(order.getExpression().getUriLiteral());
                    Order newOrder = null;
                    if (order.getSortOrder().name().equals("asc")) {
                        newOrder = cb.asc(fieldPath);
                        if (field.getDeclaredAnnotation(SumField.class) != null) {
                            newOrder = cb.asc(cb.sum(root.get(field.getName())));
                        }
                        if (field.getDeclaredAnnotation(CountField.class) != null) {
                            newOrder = cb.asc(cb.sum(root.get(field.getName())));
                        }
                    } else {
                        newOrder = cb.desc(fieldPath);
                        if (field.getDeclaredAnnotation(SumField.class) != null) {
                            newOrder = cb.desc(cb.sum(root.get(field.getName())));
                        }
                        if (field.getDeclaredAnnotation(CountField.class) != null) {
                            newOrder = cb.desc(cb.sum(root.get(field.getName())));
                        }
                    }
                    orderList.add(newOrder);
                }
            }
        }
        cq.orderBy(orderList);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void fillSelectFields(Class<?> ec, CriteriaQuery cq, CriteriaBuilder cb, Root<?> root) {
        Field[] fields = ec.getDeclaredFields();
        List<Selection<?>> selectionList = new ArrayList<Selection<?>>();
        for (Field field : fields) {
            if (field.getDeclaredAnnotation(SumField.class) != null) {
                SumField sum = field.getDeclaredAnnotation(SumField.class);
                selectionList.add(cb.sum(root.get(sum.fieldName())));
                continue;
            }
            if (field.getDeclaredAnnotation(CountField.class) != null) {
                CountField sum = field.getDeclaredAnnotation(CountField.class);
                selectionList.add(cb.count(root.get(sum.fieldName())));
                continue;
            }
            if (field.getDeclaredAnnotation(GroupByField.class) != null) {
                GroupByField group = field.getDeclaredAnnotation(GroupByField.class);
                selectionList.add(root.get(group.fieldName()));
                continue;
            }
        }
        Selection<?>[] selections = selectionList.toArray(new Selection<?>[selectionList.size()]);
        // CompoundSelection<?> selects = cb.construct(ec, selections);
        // cq.select(selects);
        cq.multiselect(selections);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void fillSelectFields(Class<?> ec, CriteriaQuery cq, CriteriaBuilder cb, Root<?> root, List<SelectItem> select) {
        Field[] fields = ec.getDeclaredFields();
        List<Selection<?>> selectionList = new ArrayList<Selection<?>>();
        for (Field field : fields) {
            if (fieldInSelection(select, field)) {
                if (field.getDeclaredAnnotation(SumField.class) != null) {
                    SumField sum = field.getDeclaredAnnotation(SumField.class);
                    selectionList.add(cb.sum(root.get(sum.fieldName())));
                    continue;
                }
                if (field.getDeclaredAnnotation(CountField.class) != null) {
                    CountField sum = field.getDeclaredAnnotation(CountField.class);
                    selectionList.add(cb.count(root.get(sum.fieldName())));
                    continue;
                }
                if (field.getDeclaredAnnotation(GroupByField.class) != null) {
                    GroupByField group = field.getDeclaredAnnotation(GroupByField.class);
                    selectionList.add(root.get(group.fieldName()));
                    continue;
                }
                selectionList.add(root.get(field.getName()));
            }
        }
        Selection<?>[] selections = selectionList.toArray(new Selection<?>[selectionList.size()]);
        // CompoundSelection<?> selects = cb.construct(ec, selections);
        // cq.select(selects);
        cq.multiselect(selections);
    }

    private boolean hasOverride(Class<?> ec) {
        return ec.getDeclaredAnnotation(OverrideEntity.class) != null;
    }

    private boolean hasSelectAnnotation(Class<?> ec) {
        return ec.getDeclaredAnnotation(CustomSelectEntity.class)!=null;
    }

    public static boolean fieldInSelection(List<SelectItem> select, Field field){
        if (select.isEmpty())
            return true;
        return select.stream().anyMatch(s -> {
            try {
                return s.getProperty().getName().equals(field.getName());
            } catch (EdmException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    /**
     * @param contextType
     * @param jpqlContext
     * @param jpqlStatement
     * @param query
     */
    private Query getParameterizedQuery(JPQLContextType contextType, CustomJPQLContext jpqlContext,
            CustomJPQLStatement jpqlStatement, Query query) {
        Map<String, Map<Integer, Object>> parameterizedMap = null;
        if (contextType == JPQLContextType.JOIN || contextType == JPQLContextType.JOIN_COUNT) {
            parameterizedMap = ((JPQLJoinContextView) jpqlContext).getParameterizedQueryMap();
        } else if (contextType == JPQLContextType.JOIN_SINGLE) {
            parameterizedMap = ((JPQLJoinSelectSingleContextView) jpqlContext).getParameterizedQueryMap();
        } else if (contextType == JPQLContextType.SELECT || contextType == JPQLContextType.SELECT_COUNT) {
            parameterizedMap = ((JPQLSelectContextView) jpqlContext).getParameterizedQueryMap();
        } else if (contextType == JPQLContextType.SELECT_SINGLE) {
            parameterizedMap = ((JPQLSelectSingleContextView) jpqlContext).getParameterizedQueryMap();
        }
        if (parameterizedMap != null && parameterizedMap.size() > 0) {
            for (Entry<String, Map<Integer, Object>> parameterEntry : parameterizedMap.entrySet()) {
                if (jpqlStatement.toString().contains(parameterEntry.getKey())) {
                    Map<Integer, Object> positionalParameters = parameterEntry.getValue();
                    for (Entry<Integer, Object> param : positionalParameters.entrySet()) {
                        if (param.getValue() instanceof Calendar || param.getValue() instanceof Timestamp) {
                            query.setParameter(param.getKey(), (Calendar) param.getValue(), TemporalType.TIMESTAMP);
                        } else if (param.getValue() instanceof Time) {
                            query.setParameter(param.getKey(), (Time) param.getValue(), TemporalType.TIME);
                        } else {
                            query.setParameter(param.getKey(), param.getValue());
                        }
                    }
                }
            }
        }
        return query;
    }

    /**
     *
     * @param uriInfo
     * @return
     * @throws EdmException
     */
    private Class<?> getJpaEntity(GetEntitySetUriInfo uriInfo) throws EdmException {
        return getJpaEntity(uriInfo.getTargetEntitySet());
    }

    /**
     *
     * @param uriInfo
     * @return
     * @throws EdmException
     */
    private Class<?> getJpaEntity(GetEntitySetCountUriInfo uriInfo) throws EdmException {
        return getJpaEntity(uriInfo.getTargetEntitySet());
    }

    /**
     *
     * @param uriInfo
     * @return
     * @throws EdmException
     */
    private Class<?> getJpaEntity(GetEntityUriInfo uriInfo) throws EdmException {
        return getJpaEntity(uriInfo.getTargetEntitySet());
    }

    /**
     *
     * @param uriInfo
     * @return
     * @throws EdmException
     */
    private Class<?> getJpaEntity(GetEntityCountUriInfo uriInfo) throws EdmException {
        return getJpaEntity(uriInfo.getTargetEntitySet());
    }

    /**
     *
     * @param entitySet
     * @return
     * @throws EdmException
     */
    private Class<?> getJpaEntity(EdmEntitySet entitySet) throws EdmException {
        return ((JPAEdmMappingImpl) entitySet.getEntityType().getMapping()).getJPAType();
    }

}

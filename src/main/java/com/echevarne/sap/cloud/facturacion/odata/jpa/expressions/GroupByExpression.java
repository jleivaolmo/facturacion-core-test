package com.echevarne.sap.cloud.facturacion.odata.jpa.expressions;

import java.util.List;

public interface GroupByExpression {

    /**
     * @return Returns the $filter expression string used to build the expression
     *         tree
     */
    String getExpressionString();

    /**
     * @return Returns a grouped list of group expressions contained in the $groupby
     *         expression string
     *         <p>
     *         <b>For example</b>: The groupby expression build from "$groupby=name
     *         asc, age desc" would contain to group expression.
     */
    public List<GroupExpression> getGroups();

    /**
     * @return Returns the count of group expressions contained in the $groupby
     *         expression string
     */
    public int getGroupCount();

    public void addGroup(GroupExpression group);

}

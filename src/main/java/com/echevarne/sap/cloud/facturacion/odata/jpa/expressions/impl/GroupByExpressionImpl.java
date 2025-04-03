package com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.impl;

import java.util.ArrayList;
import java.util.List;

import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupByExpression;
import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupExpression;

public class GroupByExpressionImpl implements GroupByExpression {

  private String groupbyString;
  List<GroupExpression> groups;

  public GroupByExpressionImpl() {
    groups = new ArrayList<GroupExpression>();
  }

  @Override
  public String getExpressionString() {
    return groupbyString;
  }

  @Override
  public List<GroupExpression> getGroups() {
    return groups;
  }

  @Override
  public int getGroupCount() {
    return groups.size();
  }

  @Override
  public void addGroup(final GroupExpression groupNode) {
    groups.add(groupNode);
  }

}

package com.echevarne.sap.cloud.facturacion.gestioncambios;

import com.echevarne.sap.cloud.facturacion.model.GestionCambioData.Action;

/**
 *
 * @author Steven Mendez Brenes
 *
 */
public class EntityComparatorItem {

  private String id;

  private String className;

  private String attributeName;

  private Action diffType;

  private String valueA;

  private String valueB;

  public EntityComparatorItem(String id, String className, String attributeName, String a, String b, Action diffType) {
    this.id = id;
    this.className = className;
    this.attributeName = attributeName;
    this.valueA = a;
    this.valueB = b;
    this.diffType = diffType;
  }

  public Action getDiffType() {
    return diffType;
  }

  public String getClassName() {
    return className;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public String getValueA() {
    return valueA;
  }

  public String getValueB() {
    return valueB;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return "EntityComparatorItem [id=" + id + ", className=" + className + ", attributeName=" + attributeName
            + ", diffType=" + diffType.name() + ", valueA=" + valueA + ", valueB=" + valueB
            + "]";
  }

}

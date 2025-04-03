package com.echevarne.sap.cloud.facturacion.odata.annotations.enums;

public enum FilterRestrictionsEnum {
	
	Single("single-value"), // Only a single "eq" clause is possible.
	Multiple("multi-value"), // Several "eq" clauses, separated by or, are possible. 
	Interval("interval"), //At most one "ge" and one "le" clause, separated by "and", alternatively a single "eq" clause.
	None("");
	
	public final String restriction;
	 
    private FilterRestrictionsEnum(String restriction) {
        this.restriction = restriction;
    }
    
    public String toString() {
        return this.restriction;
    }
}
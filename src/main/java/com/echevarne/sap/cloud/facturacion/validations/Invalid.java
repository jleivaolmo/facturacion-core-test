package com.echevarne.sap.cloud.facturacion.validations;

import java.util.Optional;

final class Invalid implements ValidationResult{
	private final String reason;
	
    Invalid(String reason){
        this.reason = reason;
    }

    public boolean isValid(){
        return false;
    }

    public Optional<String> getReason(){
        return Optional.of(reason);
    }
    
    @Override    
	public int hashCode() {
    	return reason != null ? reason.hashCode() : 7;
	}
    
    @Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof Invalid) {
			Invalid e = (Invalid) o;
			String myReason = getReason().get();
			String otherReason = e.getReason().get();
			if (myReason != null && otherReason != null) {
				return myReason.equals(otherReason) && getClass().equals(e.getClass());
			}
		}
		return false;
	}
}
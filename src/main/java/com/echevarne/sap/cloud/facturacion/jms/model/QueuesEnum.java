package com.echevarne.sap.cloud.facturacion.jms.model;

public enum QueuesEnum {

    NOTIFICATION("a/b/c/notifications"),
    TRADE("a/b/c/trades"),
    S4COMPANY("a/b/c/s4companies"),
	UPDATEPETICIONESBYPARAMS("a/b/c/UPBP"),
	DEADLETTER("a/b/c/DeadLetter");

    public String queueName;

    QueuesEnum(String queueName){ 
        this.queueName = queueName; 
    }

}

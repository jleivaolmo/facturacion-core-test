package com.echevarne.sap.cloud.facturacion.jms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An example message:
 *
 * <trade>
 *   <stock>AMZN</stock>
 *   <quantity>100</quantity>
 *   <action>BUY</action>
 * </trade>
 * 
 * { // add header '_type': 'trade'
 *   "stock": "AMZN",
 *   "quantity": "100",
 *   "action": "BUY"
 * }
 * 
 */
@Data
@ToString
@NoArgsConstructor
public class Trade implements TypedJsonMessage {

	public static String MESSAGE_TYPE = "trade";

	@Override
	@JsonIgnore	
	public String getMessageType() {
		return MESSAGE_TYPE;
	}

    private String stock;
    private Integer quantity;
    private Action action;

    public static enum  Action {
        SELL, BUY,
    }
}


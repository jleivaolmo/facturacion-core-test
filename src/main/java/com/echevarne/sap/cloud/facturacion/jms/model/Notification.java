package com.echevarne.sap.cloud.facturacion.jms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An example message:
 * 
  // add header '_type': 'notification'
  {
   "email": "example@gmail.com",
   "status": "ORDER_DISPATCHED"
  }
 * 
 */

@Data
@ToString
@NoArgsConstructor
public class Notification implements TypedJsonMessage{
	public static String MESSAGE_TYPE = "notification";	
	@Override
	@JsonIgnore	
	public String getMessageType() {
		return MESSAGE_TYPE;
	}
    public String email;
    public String status;

}

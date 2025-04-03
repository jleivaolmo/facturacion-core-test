package com.echevarne.sap.cloud.facturacion.jms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An example message:
 *     "_type": "s4companies"
  { 
	"mandt": "123",
	"bukrs": "bukr",	
	"butxt": "valor de butxt",
	"land1": "FR",
	"ort01": "valor de ort01",
	"spras": "E",
	"waers": "12345"
  }
 * 
 */

@Data
@ToString
@NoArgsConstructor
public class S4Companies implements TypedJsonMessage{
	public static String MESSAGE_TYPE = "s4companies";	
	@Override
	@JsonIgnore	
	public String getMessageType() {
		return MESSAGE_TYPE;
	}	
	private String mandt;
	private String bukrs;	
	private String butxt;
	private String land1;
	private String ort01;
	private String spras;
	private String waers;
}

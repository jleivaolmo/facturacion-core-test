package com.echevarne.sap.cloud.facturacion.util;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomLocalTimeDeserializer extends StdDeserializer<Time> {
    
	public CustomLocalTimeDeserializer() {
		this(null);
	}
	
	
	protected CustomLocalTimeDeserializer(Class<?> vc) {
		super(vc);
	}

	private static final long serialVersionUID = 1L;

    
    @Override    
    public Time deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {

        String date = parser.getText();
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        long ms=0L;
		try {
			ms = sdf.parse(date).getTime();
		} catch (ParseException e) {
			
		}
        return new Time(ms);

    }
}
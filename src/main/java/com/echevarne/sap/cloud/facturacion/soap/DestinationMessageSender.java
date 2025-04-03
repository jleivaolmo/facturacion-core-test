package com.echevarne.sap.cloud.facturacion.soap;

import lombok.Getter;
import lombok.Setter;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Getter
@Setter
public class DestinationMessageSender extends HttpComponentsMessageSender {

    private String destinationName;
    private String destinationURL;

}

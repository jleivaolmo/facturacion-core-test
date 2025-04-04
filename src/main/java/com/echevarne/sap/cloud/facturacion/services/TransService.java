package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.transformacion.Trans;
import com.echevarne.sap.cloud.facturacion.transformacion.rules.TransformerFunctions;
import com.echevarne.sap.cloud.facturacion.transformacion.rules.TransformerProcessor;

public interface TransService extends CrudService<Trans, Long> {

    TransformerProcessor getTransformerProcessorWithTransList(final TransformerFunctions customFunctions);

}

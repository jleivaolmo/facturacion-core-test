package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZPETIVALO;

public interface ZPETIVALOService extends CrudService<ZPETIVALO, Long> {
	
	List<ZPETIVALO> findByVKORGAndKUNNRAndVKBURAndZZREMNRAndZZCIANRAndZZCARGOAndZZTIPOAndSTCD1AndDATABLessThanEqual(String VKORG, String KUNNR, String VKBUR, String ZZREMNR,
			String ZZCIANR, String ZZCARGO, String ZZTIPO, String STCD1, String DATAB);

}

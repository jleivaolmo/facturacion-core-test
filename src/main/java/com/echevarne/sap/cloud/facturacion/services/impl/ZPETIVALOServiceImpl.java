package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZPETIVALO;
import com.echevarne.sap.cloud.facturacion.repositories.ZPETIVALORep;
import com.echevarne.sap.cloud.facturacion.services.ZPETIVALOService;

@Service("ZPETIVALOService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ZPETIVALOServiceImpl extends CrudServiceImpl<ZPETIVALO, Long> implements ZPETIVALOService {

	private final ZPETIVALORep rep;

	@Autowired
	public ZPETIVALOServiceImpl(ZPETIVALORep rep) {
		super(rep);
		this.rep = rep;
	}

	@Override
	public List<ZPETIVALO> findByVKORGAndKUNNRAndVKBURAndZZREMNRAndZZCIANRAndZZCARGOAndZZTIPOAndSTCD1AndDATABLessThanEqual(String VKORG, String KUNNR, String VKBUR,
			String ZZREMNR, String ZZCIANR, String ZZCARGO, String ZZTIPO, String STCD1, String DATAB) {
		return rep.findByVKORGAndKUNNRAndVKBURAndZZREMNRAndZZCIANRAndZZCARGOAndZZTIPOAndSTCD1AndDATABLessThanEqual(VKORG, KUNNR, VKBUR, ZZREMNR, ZZCIANR, ZZCARGO, ZZTIPO,
				STCD1, DATAB);
	}

}

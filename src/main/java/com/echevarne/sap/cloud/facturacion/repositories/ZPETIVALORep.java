package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZPETIVALO;

@Repository("ZPETIVALORep")
public interface ZPETIVALORep extends JpaRepository<ZPETIVALO, Long> {

	List<ZPETIVALO> findByVKORGAndKUNNRAndVKBURAndZZREMNRAndZZCIANRAndZZCARGOAndZZTIPOAndSTCD1AndDATABLessThanEqual(String VKORG, String KUNNR, String VKBUR, String ZZREMNR,
			String ZZCIANR, String ZZCARGO, String ZZTIPO, String STCD1, String DATAB);
}

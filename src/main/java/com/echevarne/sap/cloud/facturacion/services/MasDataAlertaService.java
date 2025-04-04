package com.echevarne.sap.cloud.facturacion.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataAlerta;

/**
 * Business Services logic for Model: {@link MasDataAlerta}
 * @author Hernan Girardi
 * @since 25/08/2020
 */
public interface MasDataAlertaService extends CrudService<MasDataAlerta, Long>, MasDataBaseService<MasDataAlerta, Long> {

	Optional<MasDataAlerta> findByCodigoAlerta(String codigoAlerta);

    List<MasDataAlerta> findDistinctAlertasBloquean(Date fechaPeticion);

    List<MasDataAlerta> findDistinctAlertasPrivados();

    void reloadData();

}

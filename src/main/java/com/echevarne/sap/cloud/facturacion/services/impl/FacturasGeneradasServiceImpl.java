package com.echevarne.sap.cloud.facturacion.services.impl;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.facturacion.FacturasGeneradas;
import com.echevarne.sap.cloud.facturacion.model.logAuditoria.LogAuditoriaFacturacion;
import com.echevarne.sap.cloud.facturacion.repositories.FacturasGeneradasRep;
import com.echevarne.sap.cloud.facturacion.repositories.LogAuditoriaFacturacionRepository;
import com.echevarne.sap.cloud.facturacion.services.FacturasGeneradasService;

@Service("facturasGeneradasSrv")
public class FacturasGeneradasServiceImpl extends CrudServiceImpl<FacturasGeneradas, Long> implements FacturasGeneradasService {

    private final FacturasGeneradasRep facturasGeneradasRep;
    
    private final LogAuditoriaFacturacionRepository logAuditoriaFacturacionRep;

    @Autowired
    public FacturasGeneradasServiceImpl(final FacturasGeneradasRep facturasGeneradasRep, LogAuditoriaFacturacionRepository logAuditoriaFacturacionRep) {
        super(facturasGeneradasRep);
        this.facturasGeneradasRep = facturasGeneradasRep;
		this.logAuditoriaFacturacionRep = logAuditoriaFacturacionRep;
    }

    @Override
    public <T extends BasicMessagesEntity> void saveGenerada(FacturasGeneradas periodo) {
        create(periodo);
    }

    @Override
    public List<FacturasGeneradas> getByAgrupacion(Long idAgrupacion) {
        return facturasGeneradasRep.findByIdAgrupacion(idAgrupacion);
    }

    @Override
    public Optional<FacturasGeneradas> getBySalesOrder(String salesOrder) {
        return facturasGeneradasRep.findBySalesOrder(salesOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDocuments(String codigoPedido, String codigoFactura, String documentoFinanciero, Date fechaFactura, Date fechaVencimiento, String formaPago) {
        Optional<FacturasGeneradas> generadaOpt = getBySalesOrder(codigoPedido);
        if(generadaOpt.isPresent()){
            FacturasGeneradas generada = generadaOpt.get();
            generada.setBillingDocument(codigoFactura);
            generada.setAccountingDocument(documentoFinanciero);
            generada.setFechaFactura(fechaFactura);
            generada.setFechaVencimiento(fechaVencimiento);
            generada.setFormaPago(formaPago);
            this.update(generada);
		}

		List<LogAuditoriaFacturacion> logs = logAuditoriaFacturacionRep.findBySalesOrder(codigoPedido);
		boolean logEncontrado = false;
		for (LogAuditoriaFacturacion item : logs) {
			if (StringUtils.isBlank(item.getCodFactura())) {
				item.setCodFactura(codigoFactura);
				Calendar cal = Calendar.getInstance();
				cal.setTime(fechaFactura);
				item.setFecha(cal);
				item.setHora(new Time(cal.getTimeInMillis()));
				logEncontrado = true;
			}
		}
		
		if(logEncontrado)
			logAuditoriaFacturacionRep.saveAllAndFlush(logs);
	}

	@Override
	public Optional<FacturasGeneradas> getByUUID(String UUID) {	
		return facturasGeneradasRep.findByUUID(UUID);
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSalesOrder(String salesOrder, String UUID) {
        Optional<FacturasGeneradas> generadaOpt = getByUUID(UUID);
        if(generadaOpt.isPresent()){
            FacturasGeneradas generada = generadaOpt.get();
            generada.setSalesOrder(salesOrder);
            this.update(generada);
		}
	}
}

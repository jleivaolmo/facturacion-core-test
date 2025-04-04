package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Table(name = "VT_CONTRATOS")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class ContratoText implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6827425300710814647L;

	@Id
	@Sap(text="tipoContrato")
	private Long id;

    @Sap(filterable = true)
	private String tipoContrato;

    @Sap(filterable = true)
    private String codigoCliente;
    
    @Sap(filterable = true)
    private String organizacionVentas;
    
    @Sap(filterable = true)
    private String canalDistribucion;
    
    @Sap(filterable = true)
    private String sectorVentas;
    
    @Sap(filterable = true)
    private Timestamp validoDesde;
    
    @Sap(filterable = true)
    private Timestamp validoHasta;
    
    @Sap(filterable = true)
    private int tipoPeticion;
    
    @Sap(filterable = true)
    private String delegacionOrigen;
    
    @Sap(filterable = true)
    private String delegacionEmisora;
    
    @Sap(filterable = true)
    private String provinciaDelegacion;
    
    @Sap(filterable = true)
    private String provinciaRemitente;
    
    @Sap(filterable = true)
    private String codigoTarifa;
    
    @Sap(filterable = true)
    private String codigoPais;
    
    @Sap(filterable = true)
    private String codigoReferenciaCliente;
    
    @Sap(filterable = true)
    private int periodicidad;
    
    @Sap(filterable = true)
    private int maximoPeticion;
    
    @Sap(filterable = true)
    private boolean facturaUnica;
    
    @Sap(filterable = true)
    private boolean envioPrefactura;
    
    @Sap(filterable = true)
    private BigDecimal importeContrato;
    
    @Sap(filterable = true)
    private BigDecimal importe2Peticion;
    
    @Sap(filterable = true)
    private BigDecimal importe3Peticion;
    
    @Sap(filterable = true)
    private BigDecimal importe4Peticion;
    
    @Sap(filterable = true)
    private BigDecimal importe5Peticion;
    
    @Sap(filterable = true)
    private int dias;
    
    @Sap(filterable = true)
    private String monedaContrato;
    
    @Sap(filterable = true)
    private String textoFactura;

    @Sap(filterable = true)
    private String nombreCliente;

    @Sap(filterable = true)
    private String descripcionContrato;
    
    @Sap(filterable = true)
    private int prioridad;
}

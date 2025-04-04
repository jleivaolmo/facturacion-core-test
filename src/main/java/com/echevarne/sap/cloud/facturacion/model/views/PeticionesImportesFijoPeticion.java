package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity 
@Table(name = "V_PETICIONESIMPORTESFP")
@ToString(callSuper = false, includeFieldNames = false)
@Getter
@Setter
public class PeticionesImportesFijoPeticion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1015803710939890410L;
	
	@EmbeddedId
	private PeticionesImportesKey petImpKey;
	
	/*@Id
	private Long idSolInd;
	
	@Basic
	private Long idContrato;*/
	
	
	@Basic
	private String codigoPeticion;
	
	@Column(precision = 16, scale = 3)
	private BigDecimal importePeticion;
	
	@Column(precision = 16, scale = 3)
	private BigDecimal importeContrato;
	
	@Column(precision = 5, scale = 2)
    private BigDecimal importe2Peticion;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal importe3Peticion;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal importe4Peticion;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal importe5Peticion;
    
    @Column(length = 4)
    private int dias;
    
    @Basic
    private Integer indiceImporte;
    
    @Basic
	private Timestamp fechaPeticion;
    
    @Basic
	private String codigoCliente;
	
}

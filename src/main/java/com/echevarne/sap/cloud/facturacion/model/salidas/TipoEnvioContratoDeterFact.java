package com.echevarne.sap.cloud.facturacion.model.salidas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;

import lombok.*;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_TIPO_ENVIO_CONTRATO_DETER_FACT")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class TipoEnvioContratoDeterFact implements Serializable {

	private static final long serialVersionUID = -6319200465904192295L;

	@Id
	@Basic
	private Long asignacionId;

	@Basic
	private String tipoReferencia;

	@Basic
	private Long idReferencia;
	
	@Basic
	private Long contratoId;
	
	@Basic
	private Long tipologiaEnvio;
	
	@Basic
	private String descripcion;
	
	@Basic
	private Integer prioridad;
	
	@Basic
	private Boolean prefactura;
	
	@Basic
	private Boolean enviaPDF;
	
	@Basic
	private String formatoPDF;
	
	@Basic
	private Boolean enviaTXT;
	
	@Basic
	private String formatoTXT;
	
	@Basic
	private Boolean enviaExcel;
	
	@Basic
	private String formatoExcel;
	
	@Basic
	private Boolean enviaDocumentoWinDream;
	
	@Basic
	private String metodoEnvio;
	
	@Basic
	private String medioEnvio;
	
	@Basic
	private String destinatarioEmail;
	
	@Basic
	private String ubicacionFicheroActividad;
	
	@Basic
	private String passwordEncriptacion;
	
	@Basic
	private Boolean envioActividadRectificada;
	
	@Basic
	private String contratoCanalDistribucion;
	
	@Basic
	private String contratoCodigoCliente;
	
	@Basic
	private String contratoCodigoPais;
	
	@Basic
	private String contratoCodigoReferenciaCliente;
	
	@Basic
	private String contratoCodigoTarifa;
	
	@Basic
	private String contratoDelegacionEmisora;
	
	@Basic
	private String contratoDelegacionOrigen;
	
	@Basic
	private String contratoDescripcionContrato;
	
	@Basic
	private Integer contratoDias;
	
	@Basic
	private Boolean contratoEnvioPrefactura;
	
	@Basic
	private Boolean contratoFacturaUnica;
	
	@Column(precision = 5, scale = 2)
	private BigDecimal contratoImporte2Peticion;
	
	@Column(precision = 5, scale = 2)
	private BigDecimal contratoImporte3Peticion;
	
	@Column(precision = 5, scale = 2)
	private BigDecimal contratoImporte4Peticion;
	
	@Column(precision = 5, scale = 2)
	private BigDecimal contratoImporte5Peticion;
	
	@Column(precision = 16, scale = 2)
	private BigDecimal contratoImporteContrato;
	
	@Basic
	private Integer contratoMaximoPeticion;
	
	@Basic
	private String contratoMonedaContrato;
	
	@Basic
	private String contratoOrganizacionVentas;
	
	@Basic
	private Integer contratoPeriodicidad;
	
	@Basic
	private String contratoProvinciaDelegacion;
	
	@Basic
	private String contratoProvinciaRemitente;
	
	@Basic
	private String contratoSectorVentas;
	
	@Basic
	private String contratoTextoFactura;
	
	@Basic
	private String contratoTipoContrato;
	
	@Basic
	private Integer contratoTipoPeticion;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar contratoValidoDesde;
	
	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar contratoValidoHasta;
	
	@Basic
	private Integer contratoPrioridad;
	
	@Basic
	private String determinacionFacturaCodigoCliente;
	
	@Basic
	private String determinacionFacturaOrganizacionVentas;
	
	@Basic
	private String determinacionFacturaOficinaVentas;
	
	@Basic
	private String determinacionFacturaProvinciaRemitente;
	
	@Basic
	private String determinacionFacturaCodigoPeticion;
	
	@Basic
	private String determinacionFacturaTipoPeticion;
	
	@Basic
	private String determinacionFacturaMuestraRemitida;
	
	@Basic
	private String determinacionFacturaTarifa;
	
	@Basic
	private String determinacionFacturaPrueba;
	
	@Basic
	private String determinacionFacturaGrupoSector;
	
	@Basic
	private String determinacionFacturaConceptoFacturacion;
	
	@Basic
	private String determinacionFacturaUnidadProductiva;
	
	@Basic
	private String determinacionFacturaNoBaremada;
	
	@Basic
	private String determinacionFacturaEspecialidadCliente;
	
	@Basic
	private String determinacionFacturaCodigoMoneda;
	
	@Basic
	private String determinacionFacturaCodigoOperacion;
	
	@Basic
	private String determinacionFacturaCodigoReferenciaCliente;
	
	@Basic
	private String determinacionFacturaDocumentoUnico;
	
	@Basic
	private String determinacionFacturaCodigoPoliza;

	@Basic
	private Long reglaFacturacionId;

	@Basic
	private String reglaFacturacionOrganizacionVentas;

	@Basic
	private String reglaFacturacionDescripcionRegla;

	@Basic
	private String reglaFacturacionCodigoCliente;

	@Basic
	private String reglaFacturacionOficinaVentas;

	@Basic
	private String reglaFacturacionDelegacionEmisora;

	@Basic
	private String reglaFacturacionTextoFactura;

	@Basic
	private Boolean reglaFacturacionEnvioPrefactura;

	@Basic
	private String reglaFacturacionRemitente;

	@Basic
	private String reglaFacturacionCompania;

	@Basic
	private String reglaFacturacionProvinciaRemitente;

	@Basic
	private String reglaFacturacionTipoPeticion;
	
}

package com.echevarne.sap.cloud.facturacion.model.salidas;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.MuestraRemitidaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

import static com.google.common.base.Objects.equal;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "T_DETERMINACIONFACTURA")
@Cacheable(false)
@SapEntitySet(creatable = true, updatable = true, searchable = false)
public class DeterminacionFactura extends BasicEntity implements IFactura {

    private static final long serialVersionUID = -610975917022983423L;

    @Basic
    @ValueList(
            CollectionPath = ValueListEntitiesEnum.S4Customers,
            Label = "Clientes",
            Parameters = {
                    @ValueListParameter(
                            ValueListParameterInOut = {
                                    @ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoCliente")
                            },
                            ValueListParameterDisplayOnly = {
                                    @ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente")
                            }
                    )
            }
    )
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "clienteText/nombreCliente")
    private String codigoCliente;

    @Basic
    @ValueList(
            CollectionPath = ValueListEntitiesEnum.S4SalesOrganization,
            Label = "Organización de ventas",
            Parameters = {
                    @ValueListParameter(
                            ValueListParameterInOut = {
                                    @ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas")
                            },
                            ValueListParameterDisplayOnly = {
                                    @ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion")
                            }
                    )
            }
    )
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
    private String organizacionVentas;

    @Basic
    private String oficinaVentas;

    @Basic
    private String provinciaRemitente;

    @Basic
    private String codigoPeticion;

    @Basic
    private String tipoPeticion;

    @ValueList(
            CollectionPath = ValueListEntitiesEnum.MuestraRemitida,
            Label = "Muestra remitida",
            Parameters = {
                    @ValueListParameter(
                            ValueListParameterInOut = {
                                    @ValueListParameterInOut(ValueListProperty = "muestraRemitida", LocalDataProperty = "muestraRemitida")
                            },
                            ValueListParameterDisplayOnly = {
                                    @ValueListParameterDisplayOnly(ValueListProperty = "nombreMuestraRemitida")
                            }
                    )
            }
    )
    private String muestraRemitida;

    @Basic
    private String tarifa;

    @Basic
    private String prueba;

    @Basic
    private String grupoSector;

    @Basic
    private String conceptoFacturacion;

    @Basic
    private String unidadProductiva;

    @Basic
    private String noBaremada;

    @Basic
    private String especialidadCliente;

    @Basic
    private String codigoMoneda;

    @Basic
    private String codigoOperacion;

    @Basic
    private String codigoReferenciaCliente;

    @Basic
    private String documentoUnico;

    @Basic
    private String codigoPoliza;

    /*
     * Associations Texts
     *
     ********************************************/
    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(
            name = "organizacionVentas",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private OrganizacionVentaText organizacionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(
            name = "codigoCliente",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private ClientesText clienteText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(
            name = "muestraRemitida",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private MuestraRemitidaText muestraRemitidaText;

    @Override
    public boolean onEquals(Object o) {
        return equals(o);
    }

    public boolean determinate(IFactura factura) {
        return (
                (equal(getCodigoCliente(), StringUtils.stripStart(factura.getCodigoCliente(), "0")) || "*".equals(getCodigoCliente())) &&
                (equal(getOrganizacionVentas(), factura.getOrganizacionVentas())            || "*".equals(getOrganizacionVentas())) &&
                (equal(getOficinaVentas(), factura.getOficinaVentas())                      || "*".equals(getOficinaVentas())) &&
                (equal(getProvinciaRemitente(), factura.getProvinciaRemitente())            || "*".equals(getProvinciaRemitente())) &&
                (equal(getCodigoPeticion(), factura.getCodigoPeticion())                    || "*".equals(getCodigoPeticion())) &&
                (equal(getTipoPeticion(), factura.getTipoPeticion())                        || "0".equals(getTipoPeticion())) &&
                (equal(getMuestraRemitida(), factura.getMuestraRemitida())                  || "*".equals(getMuestraRemitida())) &&
                (equal(getTarifa(), factura.getTarifa())                                    || "*".equals(getTarifa())) &&
                (equal(getPrueba(), factura.getPrueba())                                    || "*".equals(getPrueba())) &&
                (equal(getGrupoSector(), factura.getGrupoSector())                          || "*".equals(getGrupoSector())) &&
                (equal(getConceptoFacturacion(), factura.getConceptoFacturacion())          || "*".equals(getConceptoFacturacion())) &&
                (equal(getUnidadProductiva(), factura.getUnidadProductiva())                || "*".equals(getUnidadProductiva())) &&
                (equal(getNoBaremada(), factura.getNoBaremada())                            || "*".equals(getNoBaremada())) &&
                (equal(getEspecialidadCliente(), factura.getEspecialidadCliente())          || "*".equals(getEspecialidadCliente())) &&
                (equal(getCodigoMoneda(), factura.getCodigoMoneda())                        || "EUR".equals(getCodigoMoneda())) &&
                (equal(getCodigoOperacion(), factura.getCodigoOperacion())                  || "*".equals(getCodigoOperacion())) &&
                (equal(getCodigoReferenciaCliente(), factura.getCodigoReferenciaCliente())  || "*".equals(getCodigoReferenciaCliente())) &&
                (equal(getDocumentoUnico(), factura.getDocumentoUnico())                    || "*".equals(getDocumentoUnico())) &&
                (equal(getCodigoPoliza(), factura.getCodigoPoliza())                        || "*".equals(getCodigoPoliza()))
        );
    }

}

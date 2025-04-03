package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.*;

/**
 * Class for the Entity {@link PeticionMuestreoItems}.
 * @author Hernan Girardi
 * @since 06/07/2020
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
	name = ConstEntities.ENTIDAD_PETICIONMUESTREOITEMS,
	indexes={
		@Index(name = "PeticionMuestreoItems_byIDItem",  columnList="fk_PeticionMuestreo,idItem", unique=true),
		@Index(name = "PeticionMuestreoItems_byCodigoPrueba",  columnList="codigoPrueba", unique=false),
})
public class PeticionMuestreoItems extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = 6165972512288538569L;

	@Basic
	private int idItem;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private int idParent;

	@Basic
	private String codigoPrueba;

	@Basic
	private String codigoMaterial;

	@Basic
	private String descripcionMaterial;

	@Column(precision = 16, scale = 3)
	private BigDecimal cantidadRequerida;

	@Basic
	private String tipoMuestra;

	@Basic
	private String codigoMaterialFacturacion;

	@Basic
	private String descripcionMaterialFacturacion;

	@Basic
	private String unidadMaterial;

	@Basic
	private String delegacionProductiva;

	@Basic
	private String codigoCargo;

	@Basic
	private String codigoCliente;

	@Basic
	private String nifCliente;

	@Basic
	private String codigoLote;

	@Basic
	private String codigoDocumento;

	@Basic
	@ColumnDefault("false")
	private boolean esPrivado;

	@Basic
	@ColumnDefault("false")
	private boolean esFacturable;

	@Basic
	@ColumnDefault("false")
	private boolean esTerceros;

	@Basic
	@ColumnDefault("false")
	private boolean esInformada;

	@Basic
	@ColumnDefault("false")
	private boolean esPerfil;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean dependeResultado;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private int tipoPosicion;

	@Basic
	private String motivoRechazo;

	@Basic
	private String numeroOperacion;

	@Basic
	private String codigoMaterialFacturacionPerfil;

	@Basic
	private String numeroAutorizacion;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean bloqueoAutomatico;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@JsonBackReference
	private PeticionMuestreo peticion;

	/**
	 * @return the idItem
	 */
	public int getIdItem() {
		return idItem;
	}

	/**
	 * @param idItem the idItem to set
	 */
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	/**
	 * @return the idParent
	 */
	public int getIdParent() {
		return idParent;
	}

	/**
	 * @param idParent the idParent to set
	 */
	public void setIdParent(int idParent) {
		this.idParent = idParent;
	}

	/**
	 * @return the codigoPrueba
	 */
	public String getCodigoPrueba() {
		return this.codigoPrueba;
	}

	/**
	 * @param codigoPrueba
	 *            the codigoPrueba to set
	 */
	public void setCodigoPrueba(String codigoPrueba) {
		this.codigoPrueba = codigoPrueba;
	}

	/**
	 * @return the cantidadRequerida
	 */
	public BigDecimal getCantidadRequerida() {
		return this.cantidadRequerida;
	}

	/**
	 * @param cantidadRequerida
	 *            the cantidadRequerida to set
	 */
	public void setCantidadRequerida(BigDecimal cantidadRequerida) {
		this.cantidadRequerida = cantidadRequerida;
	}

	/**
	 * @return the codigoCargo
	 */
	public String getCodigoCargo() {
		return this.codigoCargo;
	}

	/**
	 * @param codigoCargo
	 *            the codigoCargo to set
	 */
	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	/**
	 * @return the codigoCliente
	 */
	public String getCodigoCliente() {
		return this.codigoCliente;
	}

	/**
	 * @param codigoCliente
	 *            the codigoCliente to set
	 */
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	/**
	 * @return the codigoLote
	 */
	public String getCodigoLote() {
		return this.codigoLote;
	}

	/**
	 * @param codigoLote
	 *            the codigoLote to set
	 */
	public void setCodigoLote(String codigoLote) {
		this.codigoLote = codigoLote;
	}

	/**
	 * @return the codigoMaterial
	 */
	public String getCodigoMaterial() {
		return this.codigoMaterial;
	}

	/**
	 * @param codigoMaterial
	 *            the codigoMaterial to set
	 */
	public void setCodigoMaterial(String codigoMaterial) {
		this.codigoMaterial = codigoMaterial;
	}

	/**
	 * @return the codigoMaterialFacturacion
	 */
	public String getCodigoMaterialFacturacion() {
		return this.codigoMaterialFacturacion;
	}

	/**
	 * @param codigoMaterialFacturacion
	 *            the codigoMaterialFacturacion to set
	 */
	public void setCodigoMaterialFacturacion(String codigoMaterialFacturacion) {
		this.codigoMaterialFacturacion = codigoMaterialFacturacion;
	}

	/**
	 * @return the delegacionProductiva
	 */
	public String getDelegacionProductiva() {
		return this.delegacionProductiva;
	}

	/**
	 * @param delegacionProductiva
	 *            the delegacionProductiva to set
	 */
	public void setDelegacionProductiva(String delegacionProductiva) {
		this.delegacionProductiva = delegacionProductiva;
	}

	/**
	 * @return the descripcionMaterial
	 */
	public String getDescripcionMaterial() {
		return this.descripcionMaterial;
	}

	/**
	 * @param descripcionMaterial
	 *            the descripcionMaterial to set
	 */
	public void setDescripcionMaterial(String descripcionMaterial) {
		this.descripcionMaterial = descripcionMaterial;
	}

	/**
	 * @return the descripcionMaterialFacturacion
	 */
	public String getDescripcionMaterialFacturacion() {
		return this.descripcionMaterialFacturacion;
	}

	/**
	 * @param descripcionMaterialFacturacion
	 *            the descripcionMaterialFacturacion to set
	 */
	public void setDescripcionMaterialFacturacion(String descripcionMaterialFacturacion) {
		this.descripcionMaterialFacturacion = descripcionMaterialFacturacion;
	}

	/**
	 * @return the esFacturable
	 */
	public boolean getEsFacturable() {
		return this.esFacturable;
	}

	/**
	 * @param esFacturable
	 *            the esFacturable to set
	 */
	public void setEsFacturable(boolean esFacturable) {
		this.esFacturable = esFacturable;
	}

	/**
	 * @return the esInformada
	 */
	public boolean getEsInformada() {
		return this.esInformada;
	}

	/**
	 * @param esInformada
	 *            the esInformada to set
	 */
	public void setEsInformada(boolean esInformada) {
		this.esInformada = esInformada;
	}

	/**
	 * @return the esPerfil
	 */
	public boolean getEsPerfil() {
		return this.esPerfil;
	}

	/**
	 * @param esPerfil
	 *            the esPerfil to set
	 */
	public void setEsPerfil(boolean esPerfil) {
		this.esPerfil = esPerfil;
	}

	/**
	 * @return the esPrivado
	 */
	public boolean getEsPrivado() {
		return this.esPrivado;
	}

	/**
	 * @param esPrivado
	 *            the esPrivado to set
	 */
	public void setEsPrivado(boolean esPrivado) {
		this.esPrivado = esPrivado;
	}

	/**
	 * @return the esTerceros
	 */
	public boolean getEsTerceros() {
		return this.esTerceros;
	}

	/**
	 * @param esTerceros
	 *            the esTerceros to set
	 */
	public void setEsTerceros(boolean esTerceros) {
		this.esTerceros = esTerceros;
	}

	/**
	 * @return the nifCliente
	 */
	public String getNifCliente() {
		return this.nifCliente;
	}

	/**
	 * @param nifCliente
	 *            the nifCliente to set
	 */
	public void setNifCliente(String nifCliente) {
		this.nifCliente = nifCliente;
	}

	/**
	 * @return the tipoMuestra
	 */
	public String getTipoMuestra() {
		return this.tipoMuestra;
	}

	/**
	 * @param tipoMuestra
	 *            the tipoMuestra to set
	 */
	public void setTipoMuestra(String tipoMuestra) {
		this.tipoMuestra = tipoMuestra;
	}

	/**
	 * @return the unidadMaterial
	 */
	public String getUnidadMaterial() {
		return this.unidadMaterial;
	}

	/**
	 * @param unidadMaterial
	 *            the unidadMaterial to set
	 */
	public void setUnidadMaterial(String unidadMaterial) {
		this.unidadMaterial = unidadMaterial;
	}

	public boolean isDependeResultado() {
		return dependeResultado;
	}

	public void setDependeResultado(boolean dependeResultado) {
		this.dependeResultado = dependeResultado;
	}

	public int getTipoPosicion() {
		return tipoPosicion;
	}

	public void setTipoPosicion(int tipoPosicion) {
		this.tipoPosicion = tipoPosicion;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getNumeroOperacion() {
		return numeroOperacion;
	}

	public void setNumeroOperacion(String numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	public String getCodigoMaterialFacturacionPerfil() {
		return codigoMaterialFacturacionPerfil;
	}

	public void setCodigoMaterialFacturacionPerfil(String codigoMaterialFacturacionPerfil) {
		this.codigoMaterialFacturacionPerfil = codigoMaterialFacturacionPerfil;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	/**
	 * @return the peticion
	 */
	public PeticionMuestreo getPeticion() {
		return peticion;
	}

	/**
	 * @param peticion
	 *            the peticion to set
	 */
	public void setPeticion(PeticionMuestreo peticion) {
		this.peticion = peticion;
	}

	/**
	 * @return the bloqueoAutomatico
	 */
	public boolean isBloqueoAutomatico() {
		return bloqueoAutomatico;
	}

	/**
	 * @param bloqueoAutomatico the bloqueoAutomatico to set
	 */
	public void setBloqueoAutomatico(boolean bloqueoAutomatico) {
		this.bloqueoAutomatico = bloqueoAutomatico;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		PeticionMuestreoItems other = (PeticionMuestreoItems) obj;
		return 	Objects.equals(cantidadRequerida, other.cantidadRequerida) &&
				Objects.equals(codigoCargo, other.codigoCargo) &&
				Objects.equals(codigoCliente, other.codigoCliente) &&
				Objects.equals(codigoLote, other.codigoLote) &&
				Objects.equals(codigoMaterial, other.codigoMaterial) &&
				Objects.equals(codigoMaterialFacturacion, other.codigoMaterialFacturacion) &&
				Objects.equals(codigoMaterialFacturacionPerfil, other.codigoMaterialFacturacionPerfil) &&
				Objects.equals(codigoPrueba, other.codigoPrueba) &&
			
				Objects.equals(delegacionProductiva, other.delegacionProductiva) &&
				dependeResultado == other.dependeResultado &&
				Objects.equals(descripcionMaterial, other.descripcionMaterial) &&
				Objects.equals(descripcionMaterialFacturacion, other.descripcionMaterialFacturacion) &&
				esFacturable == other.esFacturable &&
				esInformada == other.esInformada &&
				esPerfil == other.esPerfil &&
				esPrivado == other.esPrivado &&
				
				esTerceros == other.esTerceros &&
				idItem == other.idItem &&
				idParent == other.idParent &&
				Objects.equals(motivoRechazo, other.motivoRechazo) &&
				Objects.equals(nifCliente, other.nifCliente) &&
				Objects.equals(numeroAutorizacion, other.numeroAutorizacion) &&
				Objects.equals(numeroOperacion, other.numeroOperacion) &&
				
				Objects.equals(tipoMuestra, other.tipoMuestra) &&
				tipoPosicion == other.tipoPosicion &&
				Objects.equals(unidadMaterial, other.unidadMaterial);
	}


	@Override
	public void setCabecera(BasicEntity cabecera) {
		this.setPeticion((PeticionMuestreo) cabecera);
	}

	

}

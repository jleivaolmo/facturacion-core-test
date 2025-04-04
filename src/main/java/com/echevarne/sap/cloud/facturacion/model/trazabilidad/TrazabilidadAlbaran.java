package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import static java.util.Comparator.comparing;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.gestionestados.Mutable;
import com.echevarne.sap.cloud.facturacion.gestionestados.MutableHistory;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link TrazabilidadAlbaran}.
 *
 * <p>
 * The persistent class. . .TrazabilidadAlbaran
 * </p>
 * <p>
 * This is a simple description of the class. . .
 * </p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 15/02/2021
 *
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_TrazabilidadAlbaran")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class TrazabilidadAlbaran extends BasicEntity implements Mutable<TrazabilidadAlbaranEstHistory> {

	/**
	 *
	 */
	private static final long serialVersionUID = 8612445017828724547L;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudMuestreo")
	@JsonIgnore
	private SolicitudMuestreo solicitudRec;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trazabilidad")
	@JsonIgnore
	private Set<TrazabilidadAlbaranEstHistory> estados = new HashSet<>();

	/***************************************************
	 *
	 * Equals and HashCode
	 *
	 **************************************************/
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TrazabilidadAlbaran other = (TrazabilidadAlbaran) obj;
		if (solicitudRec == null) {
			if (other.solicitudRec != null)
				return false;
		} else if (!solicitudRec.equals(other.solicitudRec))
			return false;
		return true;
	}

	/***************************************************
	 *
	 * Métodos de apoyo
	 *
	 **************************************************/

	/**
	 *
	 * @param trzEstHist
	 */
	public void addEstado(TrazabilidadAlbaranEstHistory trzEstHist) {
		estados.add(trzEstHist);
	}

	@JsonGetter(value = "ultimoEstado")
	public String getUltimoEstado() {
		Optional<TrazabilidadAlbaranEstHistory> last = getLastEstado();
		if (last.isPresent()) {
			return getLastEstado().get().getEstado().getCodeEstado();
		}
		return "";
	}

	/**
	 *
	 * @return
	 */
	@Transient
	@JsonIgnore
	public Optional<TrazabilidadAlbaranEstHistory> getLastEstado() {
		if (!this.estados.isEmpty())
			return this.estados.stream()
					.filter(trzHist -> trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
					.max(comparing(TrazabilidadAlbaranEstHistory::getSequenceOrder));
		else
			return Optional.empty();
	}

	/**
	 *
	 * @return
	 */
	@Transient
	@JsonIgnore
	public Optional<TrazabilidadAlbaranEstHistory> getAnteUltimoEstado() {
		return this.estados.stream()
				.filter(trzHist -> trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
				.sorted(comparing(TrazabilidadAlbaranEstHistory::getSequenceOrder).reversed()).skip(1).findFirst();
	}

	/**
	 *
	 * @param estadoTofind
	 * @return
	 */
	public Optional<TrazabilidadAlbaranEstHistory> getLastEstado(MasDataEstado estadoTofind) {
		if (!this.estados.isEmpty())
			return this.estados.stream()
					.filter(trzHist -> trzHist.getEstado().equals(estadoTofind)
							&& trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
					.max(comparing(TrazabilidadAlbaranEstHistory::getSequenceOrder));
		else
			return Optional.empty();
	}

	/**
	 *
	 * Añadimos el orden correspondiente
	 *
	 * @return
	 */
	@Transient
	public int getOrder() {
		Optional<TrazabilidadAlbaranEstHistory> lastEstado = this.getLastEstado();
		if (lastEstado.isPresent()) {
			return lastEstado.get().getSequenceOrder() + 1;
		} else {
			return 1;
		}
	}

	@Override
	public TrazabilidadAlbaranEstHistory createHistory() {
		return new TrazabilidadAlbaranEstHistory();
	}

	@Override
	public Date getFechaEstado(String codeEstado) {
		return null;
	}

	@Override
	public List<MutableHistory> obtieneEstados() {
		return this.getEstados().stream().collect(Collectors.toList());
	}

	@Override
	public Optional<TrazabilidadAlbaranEstHistory> newInstance(MasDataEstado estado, MasDataMotivosEstado motivo,
			boolean automatico, boolean afectaImporte) {
		TrazabilidadAlbaranEstHistory history = new TrazabilidadAlbaranEstHistory();
		history.setSequenceOrder(getOrder());
		history.setMotivo(motivo);
		history.setAutomatico(automatico);
		history.setTrazabilidad(this);
		history.setEstado(estado);
		return Optional.of(history);
	}

	@Override
	public void addHistory(TrazabilidadAlbaranEstHistory history) {
		addEstado(history);
	}

	@Override
	public String getMessageArgs() {
		return null;
	}

	@Override
	@Transient
	public String getMessageId() {
		return "0106";
	}

	@Override
	public Optional<TrazabilidadAlbaranEstHistory> newInstanceV2(MasDataEstado estado, MasDataMotivosEstado motivo, boolean automatico, boolean afectaImporte,
			Integer sequenceOrder) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}

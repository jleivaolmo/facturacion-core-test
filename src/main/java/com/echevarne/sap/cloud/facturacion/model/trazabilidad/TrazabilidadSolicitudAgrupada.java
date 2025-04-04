package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import com.echevarne.sap.cloud.facturacion.gestionestados.Mutable;
import com.echevarne.sap.cloud.facturacion.gestionestados.MutableHistory;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "T_TrazabilidadSolicitudAgrup")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class TrazabilidadSolicitudAgrupada extends BasicEntity implements Mutable<TrazabilidadSolAgrEstHistory> {

    @OneToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "fk_SolicitudesAgrupadas")
    @JsonIgnore
    private SolicitudesAgrupadas solicitudesAgrupadas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trazabilidadSolicitudAgrupada")
    @JsonIgnore
    private Set<TrazabilidadSolAgrEstHistory> estados = new HashSet<>();


    public SolicitudesAgrupadas getSolicitudesAgrupadas() {
        return solicitudesAgrupadas;
    }

    public void setSolicitudesAgrupadas(SolicitudesAgrupadas solicitudesAgrupadas) {
        this.solicitudesAgrupadas = solicitudesAgrupadas;
    }

    public Set<TrazabilidadSolAgrEstHistory> getEstados() {
        return estados;
    }

    public void setEstados(Set<TrazabilidadSolAgrEstHistory> estados) {
        this.estados = estados;
    }

    /**
     *
     * Añadimos el orden correspondiente
     * @return
     */
    @Transient
    public int getOrder() {
        Optional<TrazabilidadSolAgrEstHistory> lastEstado = this.getLastEstado();
        if (lastEstado.isPresent()) {
            return lastEstado.get().getSequenceOrder() + 1;
        } else {
            return 1;
        }
    }

    @Override
    public TrazabilidadSolAgrEstHistory createHistory() {
        TrazabilidadSolAgrEstHistory res = new TrazabilidadSolAgrEstHistory();
        res.setTrazabilidadSolicitudAgrupada(this);
        return res;
    }

    @Override
    public Date getFechaEstado(String codeEstado) {
        Optional<TrazabilidadSolAgrEstHistory> estado =getEstados().stream().filter(x -> x.getEstado().getCodeEstado().equals(codeEstado)).findFirst();
        if (estado.isPresent()) {
            return estado.get().getFechaEstado();
        }
        return null;
    }

    @Override
    @Transient
    public List<MutableHistory> obtieneEstados() {
        return this.getEstados().stream().collect(Collectors.toList());
    }

	@Override
	public Optional<TrazabilidadSolAgrEstHistory> newInstance(MasDataEstado estado, MasDataMotivosEstado motivo,
			boolean automatico, boolean afectaImporte) {
		TrazabilidadSolAgrEstHistory history = new TrazabilidadSolAgrEstHistory();
		history.setSequenceOrder(getOrder());
		history.setMotivo(motivo);
		history.setAutomatico(automatico);
		history.setTrazabilidadSolicitudAgrupada(this);
		history.setEstado(estado);
		return Optional.of(history);
	}

    @Override
    public void addHistory(TrazabilidadSolAgrEstHistory history) {
        addEstado(history);
    }

    @Override
    @Transient
    public String getMessageArgs() {
        if (getSolicitudesAgrupadas() != null)
            return getSolicitudesAgrupadas().getSalesOrderAgr();

        return null;
    }

    @Override
    @Transient
    public String getMessageId() {
        return "0108";
    }

    public void addEstado(TrazabilidadSolAgrEstHistory trzEstHist) {
        if (estados == null)
            estados = new HashSet<>();
        estados.add(trzEstHist);
    }


    /*****************
     * Custom Methods
     *
     ********************************************/
    @JsonGetter(value = "ultimoEstado")
    public String getUltimoEstado() {
        Optional<TrazabilidadSolAgrEstHistory> last = getLastEstado();
        if (last.isPresent()) {
            return getLastEstado().get().getEstado().getCodeEstado();
        }
        return "";
    }

    @Transient
    @JsonIgnore
    public Optional<TrazabilidadSolAgrEstHistory> getLastEstado() {
        if (this.estados != null)
            return this.estados.stream()
                    .filter(trzHist -> trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
                    .max(comparing(TrazabilidadSolAgrEstHistory::getSequenceOrder));
        else
            return Optional.empty();
    }

    @Transient
    @JsonIgnore
    public Optional<TrazabilidadSolAgrEstHistory> getAnteUltimoEstado() {
        return this.estados.stream()
                .filter(trzHist -> trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
                .sorted(comparing(TrazabilidadSolAgrEstHistory::getSequenceOrder).reversed()).skip(1).findFirst();
    }

    @Transient
    @JsonIgnore
    public Optional<TrazabilidadSolAgrEstHistory> getLastEstado(MasDataEstado estadoTofind) {
        if (this.estados != null)
            return this.estados.stream()
                    .filter(trzHist -> trzHist.getEstado().equals(estadoTofind)
                            && trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
                    .max(comparing(TrazabilidadSolAgrEstHistory::getSequenceOrder));
        else
            return Optional.empty();
    }

    @Override
    public boolean onEquals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        TrazabilidadSolicitudAgrupada other = (TrazabilidadSolicitudAgrupada) obj;
        return Objects.equals(solicitudesAgrupadas, other.solicitudesAgrupadas) && Objects.equals(estados, other.estados);
    }

	@Override
	public Optional<TrazabilidadSolAgrEstHistory> newInstanceV2(MasDataEstado estado, MasDataMotivosEstado motivo, boolean automatico, boolean afectaImporte,
			Integer sequenceOrder) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}

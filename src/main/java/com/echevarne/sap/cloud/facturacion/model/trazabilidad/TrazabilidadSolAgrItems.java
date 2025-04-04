package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import com.echevarne.sap.cloud.facturacion.gestionestados.Mutable;
import com.echevarne.sap.cloud.facturacion.gestionestados.MutableHistory;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItems;
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
@Table(name = "T_TRAZABILIDADSOLAGRIT")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class TrazabilidadSolAgrItems  extends BasicEntity implements Mutable<TrazabilidadSolAgrItemsEstHistory> {

    @OneToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "fk_SolAgrItems")
    @JsonIgnore
    private SolAgrItems solAgrItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trazabilidadSolAgrItems")
    @JsonIgnore
    private Set<TrazabilidadSolAgrItemsEstHistory> estados = new HashSet<>();

    public Set<TrazabilidadSolAgrItemsEstHistory> getEstados() {
        return estados;
    }

    public void setEstados(Set<TrazabilidadSolAgrItemsEstHistory> estados) {
        this.estados = estados;
    }

    /**
     *
     * Añadimos el orden correspondiente
     * @return
     */
    @Transient
    public int getOrder() {
        Optional<TrazabilidadSolAgrItemsEstHistory> lastEstado = this.getLastEstado();
        if (lastEstado.isPresent()) {
            return lastEstado.get().getSequenceOrder() + 1;
        } else {
            return 1;
        }
    }


	@Override
	public Optional<TrazabilidadSolAgrItemsEstHistory> newInstance(MasDataEstado estado, MasDataMotivosEstado motivo,
			boolean automatico, boolean afectaImporte) {
		TrazabilidadSolAgrItemsEstHistory history = new TrazabilidadSolAgrItemsEstHistory();
		history.setSequenceOrder(getOrder());
		history.setMotivo(motivo);
		history.setAutomatico(automatico);
		history.setTrazabilidadSolAgrItems(this);
		history.setEstado(estado);
		return Optional.of(history);
	}

    @JsonGetter(value = "ultimoEstado")
    public String getUltimoEstado() {
        Optional<TrazabilidadSolAgrItemsEstHistory> last = getLastEstado();
        if (last.isPresent()) {
            return getLastEstado().get().getEstado().getCodeEstado();
        }
        return "";
    }

    @Override
    public Optional<TrazabilidadSolAgrItemsEstHistory> getLastEstado() {
        if (this.estados != null)
            return this.estados.stream()
                    .filter(trzHist -> trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
                    .max(comparing(TrazabilidadSolAgrItemsEstHistory::getSequenceOrder));
        else
            return Optional.empty();
    }

    @Override
    public Optional<TrazabilidadSolAgrItemsEstHistory> getAnteUltimoEstado() {
        return this.estados.stream()
                .filter(trzHist -> trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
                .sorted(comparing(TrazabilidadSolAgrItemsEstHistory::getSequenceOrder).reversed()).skip(1).findFirst();
    }

    @Override
    public Optional<TrazabilidadSolAgrItemsEstHistory> getLastEstado(MasDataEstado estadoTofind) {
        if (this.estados != null)
            return this.estados.stream()
                    .filter(trzHist -> trzHist.getEstado().equals(estadoTofind)
                            && trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
                    .max(comparing(TrazabilidadSolAgrItemsEstHistory::getSequenceOrder));
        else
            return Optional.empty();
    }

    @Override
    public TrazabilidadSolAgrItemsEstHistory createHistory() {
        return new TrazabilidadSolAgrItemsEstHistory();
    }


    @Override
    public void addHistory(TrazabilidadSolAgrItemsEstHistory history) {
        addEstado(history);
    }


    public void addEstado(TrazabilidadSolAgrItemsEstHistory trzEstHist) {
        if (estados == null)
            estados = new HashSet<>();
        estados.add(trzEstHist);
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
    public String getMessageArgs() {
        if(this.getSolAgrItems() != null)
            return getSolAgrItems().getMaterial();

        return null;
    }

    @Override
    @Transient
    public String getMessageId() {
        return "0107";
    }

    @Override
    public boolean onEquals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        TrazabilidadSolAgrItems other = (TrazabilidadSolAgrItems) obj;
        return Objects.equals(solAgrItems, other.solAgrItems) && Objects.equals(estados, other.estados);
    }

	@Override
	public Optional<TrazabilidadSolAgrItemsEstHistory> newInstanceV2(MasDataEstado estado, MasDataMotivosEstado motivo, boolean automatico,
			boolean afectaImporte, Integer sequenceOrder) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}

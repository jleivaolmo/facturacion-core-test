package com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros;

import static javax.persistence.CascadeType.ALL;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(
		name = "T_SolAgrPruebaInformada",
		indexes = {
				@Index(name = "IDX_byPedidoCompras", columnList = "pedidoCompras", unique = false),
		}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolAgrPruebaInformada extends SolAgrPruebaBaseEntity {

	private static final long serialVersionUID = -755747265562142234L;

	@OneToMany(cascade = ALL, mappedBy = "agrupacion")
	@JsonManagedReference
	private Set<SolAgrPruebaInformadaItems> items;

	public SolAgrPruebaInformada() {
		items = new HashSet<SolAgrPruebaInformadaItems>();
	}

	@Override
	public void addItem(SolAgrPruebaItemBaseEntity item) {
		this.items.add((SolAgrPruebaInformadaItems) item);
	}

	@Override
	public Set<SolAgrPruebaInformadaItems> getItems() {
		return items;
	}
}

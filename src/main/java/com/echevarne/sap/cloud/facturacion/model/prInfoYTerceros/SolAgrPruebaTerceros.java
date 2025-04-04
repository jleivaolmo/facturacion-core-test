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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
		name = "T_SolAgrPruebaTerceros",
		indexes = {
				@Index(name = "SolAgrPruebaTerceros_byPedidoCompras", columnList = "pedidoCompras", unique = false),
		}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolAgrPruebaTerceros extends SolAgrPruebaBaseEntity {

	private static final long serialVersionUID = -680112637591952368L;

	@OneToMany(cascade = ALL, mappedBy = "agrupacion")
	@JsonManagedReference
	private Set<SolAgrPruebaTercerosItems> items;

	public SolAgrPruebaTerceros() {
        items = new HashSet<>();
    }

	@Override
	public void addItem(SolAgrPruebaItemBaseEntity item) {
		this.items.add((SolAgrPruebaTercerosItems) item);
	}

	@Override
	public Set<SolAgrPruebaTercerosItems> getItems() {
		return items;
	}
}

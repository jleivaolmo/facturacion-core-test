package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.priority.Priorizable;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link PeticionMuestreo}.
 *
 * <p>
 * The persistent class. . .T_PeticionMuestreo
 * </p>
 * <p>
 * This is a simple description of the class. . .
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_PeticionProdAdicionales", indexes={@Index(name = "PeticionProdAdicionales_byCodigoPeticion",  columnList="fk_SolicitudMuestreo,codigoProducto", unique=true)})
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class ProductosAdicionales extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	@Priorizable
    private String codigoProducto;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudMuestreo", nullable = false)
	@JsonBackReference
	private SolicitudMuestreo solicitud;

     /**
      * Equals And HashCode
      */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        ProductosAdicionales other = (ProductosAdicionales) obj;
        if (codigoProducto == null) {
            if (other.codigoProducto != null)
                return false;
        } else if (!codigoProducto.equals(other.codigoProducto))
            return false;
        return true;
	}

	@Override
	public void setCabecera(BasicEntity cabecera) {
		this.setSolicitud((SolicitudMuestreo) cabecera);
	}

}

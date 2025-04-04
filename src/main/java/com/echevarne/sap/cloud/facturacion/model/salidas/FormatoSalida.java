package com.echevarne.sap.cloud.facturacion.model.salidas;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "T_FORMATOSALIDA")
@Cacheable(false)
@SapEntitySet(creatable = true, updatable = true, searchable = false)
public class FormatoSalida extends BasicEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8126031976407143613L;

	public enum TipoDocumentoSalida {
        FACTURA,
        DETALLEACTIVIDADTXT,
        DETALLEACTIVIDADEXCEL
    }

    @Basic
    private String formato;

    @Basic
    private String datasource;

    @Basic
    @Enumerated(EnumType.STRING)
    private TipoDocumentoSalida tipoDocumento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FormatoSalida that = (FormatoSalida) o;
        return Objects.equals(getFormato(), that.getFormato()) && Objects.equals(getDatasource(), that.getDatasource()) && getTipoDocumento() == that.getTipoDocumento();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFormato(), getDatasource(), getTipoDocumento());
    }

    @Override
    public boolean onEquals(Object o) {
        return equals(o);
    }
}

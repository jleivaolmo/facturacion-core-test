package com.echevarne.sap.cloud.facturacion.model.facturacion;

import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "T_FACTURATRAK")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class FacturaTrak extends BasicEntity 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8304144516219956040L;
	
	public static final Integer PENDIENTE = 0;
	public static final Integer FINALIZADO = 1;
	public static final Integer ERROR = 2;
	public static final Integer EN_CURSO = 3;
	
	@Basic
	private Integer estado;
	
	@Basic
	String codigoPedido;
	
	@Basic
	String numeroFactura; 
	
	@Basic
	Timestamp facturaDate;
	
	@Basic
	String indicador;
	
	@Basic
	String codigoPeticion;
	
	@Basic
	String rectificativa;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacturaTrak other = (FacturaTrak) obj;
		return Objects.equals(codigoPedido, other.codigoPedido) && Objects.equals(codigoPeticion, other.codigoPeticion) && Objects.equals(estado, other.estado)
				&& Objects.equals(facturaDate, other.facturaDate) && Objects.equals(indicador, other.indicador)
				&& Objects.equals(numeroFactura, other.numeroFactura) && Objects.equals(rectificativa, other.rectificativa);
	}
}

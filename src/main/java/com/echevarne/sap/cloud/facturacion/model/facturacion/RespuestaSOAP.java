package com.echevarne.sap.cloud.facturacion.model.facturacion;

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
@Table(name = "T_RESPUESTASOAP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class RespuestaSOAP extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4057514781413859804L;

	public static final Integer PENDIENTE = 0;
	public static final Integer FINALIZADO = 1;
	public static final Integer ERROR = 2;
	public static final Integer EN_CURSO = 3;

	@Basic
	private String uuid;

	@Basic
	private String salesOrder;

	@Basic
	private Integer estado;

	@Basic
	private String uuidInstance;
	
	@Basic
	private Long numLineasPedido;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		RespuestaSOAP other = (RespuestaSOAP) obj;
		return Objects.equals(estado, other.estado) && Objects.equals(salesOrder, other.salesOrder) && Objects.equals(uuid, other.uuid)
				&& Objects.equals(uuidInstance, other.uuidInstance);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(estado, salesOrder, uuid, uuidInstance);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RespuestaSOAP other = (RespuestaSOAP) obj;
		return Objects.equals(estado, other.estado) && Objects.equals(salesOrder, other.salesOrder) && Objects.equals(uuid, other.uuid)
				&& Objects.equals(uuidInstance, other.uuidInstance);
	}

}

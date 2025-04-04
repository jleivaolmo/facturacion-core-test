package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Table(name = "VT_TIPO_DESCUENTO")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class TipoDescuentoText implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2826597125050336283L;
	
	public static final String TIPO_FIJO 		= "Fijo";
	public static final String TIPO_PORCENTUAL 	= "Porcentual";
	
	@Id
	//@Column(columnDefinition="VARCHAR(255)")
	private String tipo;

}

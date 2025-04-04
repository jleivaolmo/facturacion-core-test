package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.odata.annotations.JPAExit;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
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
@Table(name = "VT_REMITENTES")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
@JPAExit(allowAll = true, allowEmpty = true, fieldId = "codigoRemitente", fieldDescription = "nombreRemitente")
public class RemitentesText implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1033538473284312048L;

	@Id
	@Sap(text="nombreRemitente")
	//@Column(columnDefinition="VARCHAR(255)")
	private String codigoRemitente;
	
	@Basic
	@Sap(filterable = true)
	private String nombreRemitente;

}

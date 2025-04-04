package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Embeddable
@ToString(callSuper = false, includeFieldNames = false)
@EqualsAndHashCode(callSuper = false)
public class PeticionesImportesKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3752564886312808522L;
	
	@Basic
	private Long idSolInd;
	
	@Basic
	private Long idContrato;

}

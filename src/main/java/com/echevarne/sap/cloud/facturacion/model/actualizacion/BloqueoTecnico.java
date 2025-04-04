package com.echevarne.sap.cloud.facturacion.model.actualizacion;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor()
@Table(name = "T_BloqueoTecnico")
@ToString
public class BloqueoTecnico extends BasicEntity {

    private static final long serialVersionUID = -602105439782345558L;

    @OneToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "fk_trazabilidad_solicitud", nullable = false)
    @JsonIgnore
    private TrazabilidadSolicitud trazabilidadSolicitud;

    @Column(name = "inicio_bloqueo", nullable = false)
    private Timestamp inicioBloqueo;

    @Column(name = "fin_bloqueo", nullable = true)
    private Timestamp finBloqueo;
    
    @Basic
	private String uuidInstance;

	@Override
	public boolean onEquals(Object o) {
		if (o == null || !(o instanceof BloqueoTecnico))
			return false;
		BloqueoTecnico that = (BloqueoTecnico) o;
		return Objects.equals(trazabilidadSolicitud, that.trazabilidadSolicitud) && Objects.equals(inicioBloqueo, that.inicioBloqueo)
				&& Objects.equals(finBloqueo, that.finBloqueo) && Objects.equals(uuidInstance, that.uuidInstance);
	}
}

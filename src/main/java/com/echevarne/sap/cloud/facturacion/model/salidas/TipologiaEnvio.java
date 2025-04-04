package com.echevarne.sap.cloud.facturacion.model.salidas;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "T_TIPOLOGIAENVIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@SapEntitySet(searchable = false)
public class TipologiaEnvio extends BasicEntity {

    static final long serialVersionUID = -4615149692930554310L;

    @Column(name = "DESCRIPCION", length = 450)
    private String descripcion;

    @Column(name = "PRIORIDAD")
    private int prioridad;

    @Column(name = "PREFACTURA")
    @ColumnDefault("false")
    private boolean prefactura;

    @Column(name = "ENVIAPDF")
    @ColumnDefault("false")
    private boolean enviaPdf;

    @Column(name = "FORMATOPDF")
    private String formatoPdf;

    @Column(name = "ENVIATXT")
    @ColumnDefault("false")
    private boolean enviaTxt;

    @Column(name = "FORMATOTXT")
    private String formatoTxt;

    @Column(name = "ENVIAEXCEL")
    @ColumnDefault("false")
    private boolean enviaExcel;

    @Column(name = "FORMATOEXCEL")
    private String formatoExcel;

    @Column(name = "ENVIADOCUMENTOWINDREAM")
    @ColumnDefault("false")
    private boolean enviaDocumentoWindream;

    @Column(name = "METODOENVIO")
    private String metodoEnvio;

    @Column(name = "MEDIOENVIO")
    private String medioEnvio;

    @Column(name = "DESTINATARIOEMAIL", length = 450)
    private String destinatarioEmail;

    @Column(name = "UBICACIONFICHEROACTIVIDAD", length = 450)
    private String ubicacionFicheroActividad;

    @Column(name = "PASSWORDENCRIPTACION", length = 450)
    private String passwordEncriptacion;

    @Column(name = "ENVIOACTIVIDADRECTIFICADA")
    @ColumnDefault("false")
    private boolean envioActividadRectificada;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TipologiaEnvio that = (TipologiaEnvio) o;
        return isEnviaPdf() == that.isEnviaPdf()
                && isEnviaTxt() == that.isEnviaTxt()
                && isEnviaExcel() == that.isEnviaExcel()
                && isEnviaDocumentoWindream() == that.isEnviaDocumentoWindream()
                && isEnvioActividadRectificada() == that.isEnvioActividadRectificada()
                && Objects.equals(getDescripcion(), that.getDescripcion())
                && Objects.equals(getPrioridad(), that.getPrioridad())
                && Objects.equals(getFormatoPdf(), that.getFormatoPdf())
                && Objects.equals(getFormatoTxt(), that.getFormatoTxt())
                && Objects.equals(getFormatoExcel(), that.getFormatoExcel())
                && Objects.equals(getMetodoEnvio(), that.getMetodoEnvio())
                && Objects.equals(getMedioEnvio(), that.getMedioEnvio())
                && Objects.equals(getDestinatarioEmail(), that.getDestinatarioEmail())
                && Objects.equals(getUbicacionFicheroActividad(), that.getUbicacionFicheroActividad())
                && Objects.equals(getPasswordEncriptacion(), that.getPasswordEncriptacion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getDescripcion(),
                getPrioridad(),
                isEnviaPdf(),
                getFormatoPdf(),
                isEnviaTxt(),
                getFormatoTxt(),
                isEnviaExcel(),
                getFormatoExcel(),
                isEnviaDocumentoWindream(),
                getMetodoEnvio(),
                getMedioEnvio(),
                getDestinatarioEmail(),
                getUbicacionFicheroActividad(),
                getPasswordEncriptacion(),
                isEnvioActividadRectificada()
        );
    }

    @Override
    public boolean onEquals(Object o) {
        return this.equals(o);
    }
}

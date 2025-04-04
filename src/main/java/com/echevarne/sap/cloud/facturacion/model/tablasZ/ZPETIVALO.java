package com.echevarne.sap.cloud.facturacion.model.tablasZ;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;

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
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "T_ZPETIVALO")
public class ZPETIVALO extends BasicEntity implements Serializable {

	private static final long serialVersionUID = -8339142344011621315L;

	@Basic
	private String VKORG;
	@Basic
	private String KUNNR;
	@Basic
	private String VKBUR;
	@Basic
	private String ZZREMNR;
	@Basic
	private String ZZCIANR;
	@Basic
	private String ZZCARGO;
	@Basic
	private String ZZTIPO;
	@Basic
	private String STCD1;
	@Basic
	private String DATAB;
	@Basic
	private String DATBI;
	@Basic
	private String ZZACTI;
	@Basic
	private String ZZMETODO;
	@Basic
	private String ZZDEVICE;
	@Basic
	private String ZZPATH;
	@Basic
	private String ZZIMPRE;
	@Basic
	private String ZZPDF;
	@Basic
	private String ZZFTEXT;
	@Basic
	private String VSTPZ;
	@Basic
	private String ZZBRUTO;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZPETIVALO other = (ZPETIVALO) obj;
		return Objects.equals(DATAB, other.DATAB) && Objects.equals(DATBI, other.DATBI) && Objects.equals(KUNNR, other.KUNNR)
				&& Objects.equals(STCD1, other.STCD1) && Objects.equals(VKBUR, other.VKBUR) && Objects.equals(VKORG, other.VKORG)
				&& Objects.equals(VSTPZ, other.VSTPZ) && Objects.equals(ZZACTI, other.ZZACTI) && Objects.equals(ZZBRUTO, other.ZZBRUTO)
				&& Objects.equals(ZZCARGO, other.ZZCARGO) && Objects.equals(ZZCIANR, other.ZZCIANR) && Objects.equals(ZZDEVICE, other.ZZDEVICE)
				&& Objects.equals(ZZFTEXT, other.ZZFTEXT) && Objects.equals(ZZIMPRE, other.ZZIMPRE) && Objects.equals(ZZMETODO, other.ZZMETODO)
				&& Objects.equals(ZZPATH, other.ZZPATH) && Objects.equals(ZZPDF, other.ZZPDF) && Objects.equals(ZZREMNR, other.ZZREMNR)
				&& Objects.equals(ZZTIPO, other.ZZTIPO);
	}
}

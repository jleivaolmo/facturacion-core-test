package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui.Identification;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui.LineItem;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui.SelectionFields;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Class for the Entity {@link MasDataSistemasLims}.
 * @author Hernan Girardi
 * @since 23/04/2020
 */
@Entity
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = ConstEntities.ENTIDAD_MASDATASISTEMASLIMS)
@SapEntitySet(creatable = true, deletable = true, searchable = false, updatable = true)
public class MasDataSistemasLims extends BasicMasDataEntity {

  /**
   * Universal serial ID for serialization
   */
  private static final long serialVersionUID = 1796792611309234697L;

  @Basic
  @SelectionFields
  @LineItem
  @Identification
  private String nombreSistema;


  @Override
  public boolean onEquals(Object obj) {
    return this.equals(obj);
  }

}

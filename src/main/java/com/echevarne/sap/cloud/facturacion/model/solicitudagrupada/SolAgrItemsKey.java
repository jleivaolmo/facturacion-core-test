package com.echevarne.sap.cloud.facturacion.model.solicitudagrupada;

import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_EMPRESA;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_PACIENTE;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_PROFESIONAL;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_VISITADOR;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.gestionestados.Excluida;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndPartner;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;

import lombok.Getter;
import lombok.var;

/**
 * Clase que se usa como clave en un Map para el proceso de agrupacion
 */
@Getter
public class SolAgrItemsKey {

	private String tipoPosicion;
	private String material;
	private String priceReferenceMaterial;
	private String productionPlant;
	private String profitCenter;
	private String delProductiva;
	private String codigoDelegacion;
	private String grupoPrecio;
	private String listaPrecio;
	private int tipoPeticion;
	private SolAgrItems perfilPadre;
	private String organizationDivision;
	private Set<SolAgrItems> pruebas = new HashSet<>();
	private final Set<SolAgrItemPricing> prices = new HashSet<>();
	private final Set<SolAgrItemPartner> partners = new HashSet<>();
	private final Set<SolAgrItemPricing> pricesPerfilPadre = new HashSet<>();
	private final Set<SolAgrItemPartner> partnersPerfilPadre = new HashSet<>();
	private final EntityManager entityManager;

	/**
	 *
	 * @param solIndItems
	 */
	public SolAgrItemsKey(SolIndItems solIndItems, String tipoContrato, EntityManager entityManager) {
		this.tipoPosicion = solIndItems.getSalesOrderItemCategory();
		this.material = solIndItems.getMaterial();
		this.priceReferenceMaterial = solIndItems.getPriceReferenceMaterial();
		this.productionPlant = solIndItems.getProductionPlant();
		this.profitCenter = solIndItems.getProfitCenter();
		this.delProductiva = solIndItems.getDelProductiva();
		this.codigoDelegacion = solIndItems.getSolicitudInd().getSalesOffice();
		this.grupoPrecio = solIndItems.getSolicitudInd().getGrupoPrecioCliente();
		this.listaPrecio = solIndItems.getSolicitudInd().getTarifa();
		this.tipoPeticion = solIndItems.getSolicitudInd().getTipoPeticion();
		this.organizationDivision = solIndItems.getOrganizationDivision();
		this.entityManager = entityManager;
		if (ConstFacturacion.TIPO_POSICION_PERFIL.equals(solIndItems.getSalesOrderItemCategory())) {
			mapPruebasPerfil(solIndItems, solIndItems.getSolicitudInd());
		}
		if (ConstFacturacion.TIPO_POSICION_PRUEBAPERFIL.equals(solIndItems.getSalesOrderItemCategory())){
			mapPerfilDePrueba(solIndItems, solIndItems.getSolicitudInd(), tipoContrato);
		}
		if (tipoContrato == null) {
			mapIndPartnersToAgrPartner(solIndItems.getSolicitudInd().getPartners());
		}
	}
	
	public SolAgrItemsKey(SolIndItems solIndItems, String tipoContrato) {
		this.entityManager = null;
		this.tipoPosicion = solIndItems.getSalesOrderItemCategory();
		this.material = solIndItems.getMaterial();
		this.priceReferenceMaterial = solIndItems.getPriceReferenceMaterial();
		this.productionPlant = solIndItems.getProductionPlant();
		this.profitCenter = solIndItems.getProfitCenter();
		this.delProductiva = solIndItems.getDelProductiva();
		this.codigoDelegacion = solIndItems.getSolicitudInd().getSalesOffice();
		this.grupoPrecio = solIndItems.getSolicitudInd().getGrupoPrecioCliente();
		this.listaPrecio = solIndItems.getSolicitudInd().getTarifa();
		this.tipoPeticion = solIndItems.getSolicitudInd().getTipoPeticion();
		this.organizationDivision = solIndItems.getOrganizationDivision();
	}

	private void mapPerfilDePrueba(SolIndItems solIndItems, SolicitudIndividual solicitud, String tipoContrato) {
		int idPerfil = solIndItems.getHigherLevelltem();
		Optional<SolIndItems> optPerfil = solicitud.getItems().stream().filter(x -> x.getSalesOrderIndItem() == idPerfil).findAny();
		if (optPerfil.isPresent()) {
			mapPerfilPadre(optPerfil.get());
			mapPruebasPerfil(optPerfil.get(), solicitud);
			if (tipoContrato == null) {
				mapPerfilAgrPartner(optPerfil.get().getSolicitudInd().getPartners());
			}
		}
	}

	private void mapPerfilPadre(SolIndItems solIndItemPerfil) {
		SolAgrItems perfilPadre = new SolAgrItems();
		perfilPadre.setMaterial(solIndItemPerfil.getMaterial());
		perfilPadre.setPriceReferenceMaterial(solIndItemPerfil.getPriceReferenceMaterial());
		perfilPadre.setProductionPlant(solIndItemPerfil.getProductionPlant());
		perfilPadre.setProfitCenter(solIndItemPerfil.getProfitCenter());
		perfilPadre.setDelProductiva(solIndItemPerfil.getDelProductiva());
		perfilPadre.setOficinaVentas(solIndItemPerfil.getSolicitudInd().getSalesOffice());
		perfilPadre.setGrupoPrecio(solIndItemPerfil.getSolicitudInd().getGrupoPrecioCliente());
		perfilPadre.setListaPrecio(solIndItemPerfil.getSolicitudInd().getTarifa());
		perfilPadre.setTipoPeticion(solIndItemPerfil.getSolicitudInd().getTipoPeticion());
		this.perfilPadre = perfilPadre;
	}

	private void mapPerfilAgrPartner(Set<SolIndPartner> partners) {
		for (SolIndPartner solIndPartner : partners) {
			if (!solIndPartner.getPartnerFunction().equals(ROL_INTERLOCUTOR_PACIENTE)
					&& !solIndPartner.getPartnerFunction().equals(ROL_INTERLOCUTOR_PROFESIONAL)
					&& !solIndPartner.getPartnerFunction().equals(ROL_INTERLOCUTOR_VISITADOR)
					&& !solIndPartner.getPartnerFunction().equals(ROL_INTERLOCUTOR_EMPRESA)) {
				SolAgrItemPartner solAgrPartner = new SolAgrItemPartner();
				solAgrPartner.setPartnerFunction(solIndPartner.getPartnerFunction());
				solAgrPartner.setCustomer(solIndPartner.getCustomer());
				partnersPerfilPadre.add(solAgrPartner);
			}
		}
	}

	/**
	 *
	 * Añadimos las pruebas en caso de que sea un perfil para que sea comparado
	 */
	private void mapPruebasPerfil(SolIndItems solIndItems, SolicitudIndividual solicitud) {
		var items = solicitud.getItems();
		items.stream().filter(x -> x.getHigherLevelltem() == solIndItems.getSalesOrderIndItem()
				&& !esPruebaExcluida(x.getTrazabilidad())).forEach(x -> {
					mapPruebaPerfil(x);
				});
	}
	
	private boolean esPruebaExcluida(Trazabilidad trazabilidad) {
		String sql = null;
		var testVariable = System.getProperty("test.environment");
		if ("true".equals(testVariable)) {
			sql = "SELECT e.CODEESTADO  FROM T_TRAZABILIDAD t LEFT JOIN T_TRAZABILIDADESTHISTORY tt ON tt.FK_TRAZABILIDAD = t.ID "
					+ " LEFT JOIN T_MASDATAESTADO e ON tt.FK_ESTADO = e.ID WHERE t.ID = " + trazabilidad.getId() + " ORDER BY tt.SEQUENCEORDER DESC LIMIT 1";

		} else {
			sql = "SELECT TOP 1 e.CODEESTADO  FROM T_TRAZABILIDAD t LEFT JOIN T_TRAZABILIDADESTHISTORY tt ON tt.FK_TRAZABILIDAD = t.ID "
					+ " LEFT JOIN T_MASDATAESTADO e ON tt.FK_ESTADO = e.ID WHERE t.ID = " + trazabilidad.getId() + " ORDER BY tt.SEQUENCEORDER DESC";
		}
		String result = (String) entityManager.createNativeQuery(sql).getSingleResult();
		return Excluida.CODIGO.equals(result);
	}

	/**
	 *
	 * Añadimos las pruebas en caso de que sea un perfil
	 */
	private void mapPruebaPerfil(SolIndItems solIndItem) {
		SolAgrItems pruebaPerfil = new SolAgrItems();
		pruebaPerfil.setMaterial(solIndItem.getMaterial());
		pruebaPerfil.setSalesOrderItemCategory(solIndItem.getSalesOrderItemCategory());
		pruebaPerfil.setTransactionCurrency(solIndItem.getTransactionCurrency());
		pruebaPerfil.setUnidadProductiva(solIndItem.getUnidadProductiva());
		pruebaPerfil.setOficinaVentas(solIndItem.getSolicitudInd().getSalesOffice());
		pruebaPerfil.setDelProductiva(solIndItem.getDelProductiva());
		pruebaPerfil.setTipoPeticion(this.tipoPeticion);
		pruebaPerfil.setGrupoPrecio(this.grupoPrecio);
		pruebaPerfil.setListaPrecio(this.listaPrecio);
		pruebaPerfil.setOrganizationDivision(solIndItem.getOrganizationDivision());
		this.pruebas.add(pruebaPerfil);
	}

	private void mapIndPartnersToAgrPartner(Set<SolIndPartner> solIndPartners) {
		for (SolIndPartner solIndPartner : solIndPartners) {
			if (!solIndPartner.getPartnerFunction().equals(ROL_INTERLOCUTOR_PACIENTE)
					&& !solIndPartner.getPartnerFunction().equals(ROL_INTERLOCUTOR_PROFESIONAL)
					&& !solIndPartner.getPartnerFunction().equals(ROL_INTERLOCUTOR_VISITADOR)
					&& !solIndPartner.getPartnerFunction().equals(ROL_INTERLOCUTOR_EMPRESA)) {
				SolAgrItemPartner solAgrPartner = new SolAgrItemPartner();
				solAgrPartner.setPartnerFunction(solIndPartner.getPartnerFunction());
				solAgrPartner.setCustomer(solIndPartner.getCustomer());
				this.partners.add(solAgrPartner);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + ((material == null) ? 0 : material.hashCode());
		result = prime * result + ((codigoDelegacion == null) ? 0 : codigoDelegacion.hashCode());
		result = prime * result + ((delProductiva == null) ? 0 : delProductiva.hashCode());
		result = prime * result + ((grupoPrecio == null) ? 0 : grupoPrecio.hashCode());
		result = prime * result + ((listaPrecio == null) ? 0 : listaPrecio.hashCode());
		result = prime * result + ((partners == null) ? 0 : partners.hashCode());
		result = prime * result + ((priceReferenceMaterial == null) ? 0 : priceReferenceMaterial.hashCode());
		result = prime * result + ((prices == null) ? 0 : prices.hashCode());
		result = prime * result + ((pruebas == null) ? 0 : pruebas.hashCode());
		result = prime * result + ((productionPlant == null) ? 0 : productionPlant.hashCode());
		result = prime * result + ((profitCenter == null) ? 0 : profitCenter.hashCode());
		result = prime * result + tipoPeticion;
		result = prime * result + ((tipoPosicion == null) ? 0 : tipoPosicion.hashCode());
        result = prime * result + ((perfilPadre == null) ? 0 : perfilPadre.hashCode());
        result = prime * result + ((pricesPerfilPadre == null) ? 0 : pricesPerfilPadre.hashCode());
        result = prime * result + ((partnersPerfilPadre == null) ? 0 : partnersPerfilPadre.hashCode());
		result = prime * result + ((organizationDivision == null) ? 0 : organizationDivision.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		SolAgrItemsKey other = (SolAgrItemsKey) obj;
		return Objects.equals(material, other.material)
				&& Objects.equals(priceReferenceMaterial, other.priceReferenceMaterial)
				&& Objects.equals(prices, other.prices) && Objects.equals(partners, other.partners)
				&& Objects.equals(pruebas, other.pruebas) && Objects.equals(productionPlant, other.productionPlant)
				&& Objects.equals(profitCenter, other.profitCenter)
				&& Objects.equals(codigoDelegacion, other.codigoDelegacion)
				&& Objects.equals(delProductiva, other.delProductiva)
				&& Objects.equals(grupoPrecio, other.grupoPrecio) && Objects.equals(listaPrecio, other.listaPrecio)
				&& Objects.equals(tipoPosicion, other.tipoPosicion) && Objects.equals(tipoPeticion, other.tipoPeticion)
                && Objects.equals(perfilPadre, other.perfilPadre)
                && Objects.equals(pricesPerfilPadre, other.pricesPerfilPadre)
                && Objects.equals(partnersPerfilPadre, other.partnersPerfilPadre)
				&& Objects.equals(organizationDivision, other.organizationDivision);
	}

}

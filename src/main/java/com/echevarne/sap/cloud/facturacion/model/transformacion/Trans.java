package com.echevarne.sap.cloud.facturacion.model.transformacion;

import java.util.List;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;


/**
 * The Class Trans.
 */
@Entity
@Table(
	name = "T_Transformacion",
	indexes = {
		@Index(name = "Transformacion_byName", columnList = "name", unique = true),
	}
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = false)
public class Trans  extends BasicEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6452037167353082593L;

	/** The name. */
	@Basic
	@NaturalId
	private String name;

	/** The in class. */
	@Basic
	private String inClass;

	/** The in alias. */
	@Basic
	private String inAlias;

	/** The out class. */
	@Basic
    private String outClass;

	/** The out alias. */
	@Basic
	private String outAlias;

	/** The rules. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trans", fetch = FetchType.EAGER)
	@JsonManagedReference
	@BatchSize(size = 100)
	@OrderBy("exceOrder ASC")
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<TransRule> rules;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the in class.
	 *
	 * @return the in class
	 */
	public String getInClass() {
		return inClass;
	}

	/**
	 * Sets the in class.
	 *
	 * @param inClass the new in class
	 */
	public void setInClass(String inClass) {
		this.inClass = inClass;
	}

	/**
	 * Gets the in alias.
	 *
	 * @return the in alias
	 */
	public String getInAlias() {
		return inAlias;
	}

	/**
	 * Sets the in alias.
	 *
	 * @param inAlias the new in alias
	 */
	public void setInAlias(String inAlias) {
		this.inAlias = inAlias;
	}

	/**
	 * Gets the out class.
	 *
	 * @return the out class
	 */
	public String getOutClass() {
		return outClass;
	}

	/**
	 * Sets the out class.
	 *
	 * @param outClass the new out class
	 */
	public void setOutClass(String outClass) {
		this.outClass = outClass;
	}

	/**
	 * Gets the out alias.
	 *
	 * @return the out alias
	 */
	public String getOutAlias() {
		return outAlias;
	}

	/**
	 * Sets the out alias.
	 *
	 * @param outAlias the new out alias
	 */
	public void setOutAlias(String outAlias) {
		this.outAlias = outAlias;
	}

	/**
	 * Gets the rules.
	 *
	 * @return the rules
	 */
	public List<TransRule> getRules() {
		return rules;
	}

	/**
	 * Sets the rules.
	 *
	 * @param rules the new rules
	 */
	public void setRules(List<TransRule> rules) {
		this.rules = rules;
	}


	/**
	 * On equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Trans other = (Trans) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (inAlias == null) {
			if (other.inAlias != null)
				return false;
		} else if (!inAlias.equals(other.inAlias))
			return false;
		if (inClass == null) {
			if (other.inClass != null)
				return false;
		} else if (!inClass.equals(other.inClass))
			return false;
		if (outAlias == null) {
			if (other.outAlias != null)
				return false;
		} else if (!outAlias.equals(other.outAlias))
			return false;
		if (outClass == null) {
			if (other.outClass != null)
				return false;
		} else if (!outClass.equals(other.outClass))
			return false;
		return true;
	}





}

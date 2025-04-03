package com.echevarne.sap.cloud.facturacion.gestioncambios;

import java.util.Set;

/**
 * Class for the Entity {@link SetComparatorResults}.
 * 
 * <p>. . .</p>
 * <p>This is the container of the differences in the sets</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 * @param <T>
 * 
 */
public class SetComparatorResults<T> {

	private Set<T> newSet;
	private Set<T> equalSet;
	private Set<T> deletedSet;
	private Set<T> modifiedSet;

	/**
	 * 
	 * @param newSet
	 * @param equalSet
	 * @param deletedSet
	 * @param modifiedSet
	 */
	public SetComparatorResults(Set<T> newSet, Set<T> equalSet, Set<T> deletedSet, Set<T> modifiedSet) {
		this.newSet = newSet;
		this.equalSet = equalSet;
		this.deletedSet = deletedSet;
		this.modifiedSet = modifiedSet;
	}

	/**
	 * 
	 * @return
	 */
	public Set<T> getNewSet() {
		return newSet;
	}

	/**
	 * 
	 * @return
	 */
	public Set<T> getEqualSet() {
		return equalSet;
	}

	/**
	 * 
	 * @return
	 */
	public Set<T> getDeletedSet() {
		return deletedSet;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<T> getModifiedSet() {
		return modifiedSet;
	}

}

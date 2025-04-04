package com.echevarne.sap.cloud.facturacion.transformacion.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;


/**
 * Regla de transformacion.
 *
 * @author Steven Mendez
 */
public class TransformerRule {

	/** The process. */
	private String process;
	
	/** The sequence. */
	private int sequence;
	
	/** The outcome. */
	private Function<SolicitudMuestreo,Object> outcome;
	
	/** The criteria. */
	private List<Function<SolicitudMuestreo,Boolean>> criteria = new ArrayList<>();

	/**
	 * Gets the process.
	 *
	 * @return the process
	 */
	public String getProcess() {
		return process;
	}

	/**
	 * Sets the process.
	 *
	 * @param process the new process
	 */
	public void setProcess(String process) {
		this.process = process;
	}

	/**
	 * Gets the sequence.
	 *
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * Sets the sequence.
	 *
	 * @param sequence the new sequence
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * Gets the criteria.
	 *
	 * @return the criteria
	 */
	public List<Function<SolicitudMuestreo,Boolean>> getCriteria() {
		return criteria;
	}

	/**
	 * Sets the criteria.
	 *
	 * @param criteria the criteria
	 */
	public void setCriteria(List<Function<SolicitudMuestreo,Boolean>> criteria) {
		this.criteria = criteria;
	}

	/**
	 * Gets the outcome.
	 *
	 * @return the outcome
	 */
	public Function<SolicitudMuestreo,Object> getOutcome() {
		return outcome;
	}

	/**
	 * Sets the outcome.
	 *
	 * @param outcome the outcome
	 */
	public void setOutcome(Function<SolicitudMuestreo,Object> outcome) {
		this.outcome = outcome;
	}

	/**
	 * Adds the criterion.
	 *
	 * @param c the c
	 */
	public void addCriterion(Function<SolicitudMuestreo,Boolean> c) {
		this.criteria.add(c);

	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "DynamicClassRule [process=" + process + ", sequence=" + sequence + ", outcome=" + outcome
				+ ", criteria=" + criteria + "]";
	}

}

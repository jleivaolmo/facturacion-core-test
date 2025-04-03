package com.echevarne.sap.cloud.facturacion.util;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Class for the Entity {@link SetUtil}.
 * 
 * <p>. . .</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 25/05/2020
 * 
 */
public class SetUtil {

	private SetUtil() {}
	
	/**
	 * 
	 * @param hashSet
	 * @return
	 */
	public static <T> Set<T> convertSetToTreeSet(Set<T> hashSet){
		Set<T> treeSet = new TreeSet<>();
		treeSet.addAll(hashSet);
		return treeSet;
	}
	
	/**
	 * 
	 * @param set
	 * @param comparing
	 * @return
	 */
	public static <T> SortedSet<T> sortSet(Set<T> set, Comparator<T> comparing) {
		SortedSet<T> sorted = new TreeSet<>(comparing);
		sorted.addAll(set);
		return sorted;
	}
	
	public static <T> List<T> sortSetToList(Set<T> set, Comparator<T> comparing) {
		SortedSet<T> sorted = new TreeSet<>(comparing);
		sorted.addAll(set);
		List<T> sortedList = new ArrayList<T>();
		sortedList.addAll(set);
		return sortedList;
	}

	public static <T> Collection<List<T>> partitionBasedOnSize(List<T> inputList, int size) {
		final AtomicInteger counter = new AtomicInteger(0);
		return inputList.stream()
				.collect(Collectors.groupingBy(s -> counter.getAndIncrement()/size))
				.values();
	}
	
}

package com.echevarne.sap.cloud.facturacion.gestioncambios;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Class for the Entity {@link SetComparator}.
 *
 * <p>. . .</p>
 * <p>This is the container of the differences in the sets collections</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Slf4j
public class SetComparator {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> SetComparatorResults<T> compare(Class<T> clazz, Set<T> setA, Set<T> setB) {

		List<String> ids = AnnotationUtil.getUniqueIndexFields(clazz);

		if (ids.size() == 0) {
			throw new RuntimeException("Can not compare sets without unique index on class " + clazz.getCanonicalName());
		}

		Map<List<Object>, ?> mapA = EntityMapUtil.getMap(ids, setA);
		Map<List<Object>, ?> mapB = EntityMapUtil.getMap(ids, setB);

		Set<?> newSet = getNewSet(mapA, mapB);
		Set<?> equalSet = getEqualSet(mapA, mapB);
		Set<?> deletedSet = getDeletedSet(mapA, mapB);
		Set<?> modifiedSet = getModifiedSet(ids, mapB, equalSet, newSet);

		return new SetComparatorResults(newSet, equalSet, deletedSet, modifiedSet);
	}

	private Set<?> getNewSet(Map<List<Object>, ?> mapA, Map<List<Object>, ?> mapB) {
		Set<Object> newSet = new HashSet<>();

		for (Entry<List<Object>, ?> entry : mapB.entrySet()) {
			List<Object> key = entry.getKey();
			if (!mapA.containsKey(key)) {
				newSet.add(entry.getValue());
			}
		}

		return newSet;
	}

	private Set<?> getDeletedSet(Map<List<Object>, ?> mapA, Map<List<Object>, ?> mapB) {

		Set<Object> deletedSet = new HashSet<>();

		for (Entry<List<Object>, ?> entry : mapA.entrySet()) {
			List<Object> key = entry.getKey();
			if (!mapB.containsKey(key)) {
				deletedSet.add(entry.getValue());
			}
		}

		return deletedSet;
	}

	private Set<?> getEqualSet(Map<List<Object>, ?> mapA, Map<List<Object>, ?> mapB) {
		Set<Object> equalSet = new HashSet<>();

		for (Entry<List<Object>, ?> entry : mapA.entrySet()) {
			List<Object> key = entry.getKey();
			if (mapB.containsKey(key)) {
				Object tA = entry.getValue();
				Object tB = mapB.get(key);
				if (isEntityOrObjectEqual(tA, tB)) {
					equalSet.add(tB);
				}
			}
		}

		return equalSet;
	}

	private Set<?> getModifiedSet(List<String> ids, Map<List<Object>, ?> mapB, Set<?> equalSet, Set<?> newSet) {
		Set<List<Object>> equalsKeysSet = createKeysSet(ids, equalSet);
		Set<List<Object>> newKeysSet = createKeysSet(ids, newSet);

		Set<List<Object>> modifiedKeysSet = mapB.keySet();
		modifiedKeysSet.removeAll(equalsKeysSet);
		modifiedKeysSet.removeAll(newKeysSet);

		return modifiedKeysSet.stream()
				.map(mapB::get)
				.collect(Collectors.toSet());
	}

	private boolean isEntityOrObjectEqual(Object tA, Object tB) {
		boolean isEqual;
		if (tA instanceof BasicEntity && tB instanceof BasicEntity) {
			EntityComparator entityComparator = new EntityComparator(tA.getClass());
			entityComparator.compare(tA, tB);

			isEqual = entityComparator.getResult().getDiffs().isEmpty();
		} else {
			isEqual = tA.equals(tB);
		};

		return isEqual;
	}



	private Set<List<Object>> createKeysSet(List<String> ids, Collection<?> objects) {
		return objects.stream()
				.map((object) -> EntityMapUtil.getKey(ids, object))
				.collect(Collectors.toSet());
	}
}

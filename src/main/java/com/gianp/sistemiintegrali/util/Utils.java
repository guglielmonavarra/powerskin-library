package com.gianp.sistemiintegrali.util;

import com.gianp.sistemiintegrali.model.MyNode;
import com.google.common.collect.Maps;
import org.paukov.combinatorics3.Generator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

	public static int nElementsOverThr(List<MyNode> collection, double threshold){
		return (int) collection.stream()
				.map(MyNode::getMyValue)
				.filter(val -> val >= threshold)
				.count()
				;
	}

	private static Map<String, List<List<Integer>>> combsCache = Maps.newHashMap();

	//Gets all the combinations of length k from 1 to maxValue (exlusive)
	public static List<List<Integer>> getAllCombs(int maxValue, int k){
		String cacheKey = maxValue + "-" + k;
		List<List<Integer>> cacheObj = combsCache.get(cacheKey);
		if (cacheObj != null) return cacheObj;

		List<Integer> v = IntStream.range(1, maxValue).boxed().collect(Collectors.toList());
		List<List<Integer>> result = Generator.combination(v).simple(k).stream().collect(Collectors.toList());
		combsCache.put(cacheKey, result);
		return result;
	}

//	public static Map<Integer, Map<String, Double>> mergeMapOfMaps(Map<Integer, Map<String, Double>> m1, Map<Integer, Map<String, Double>> m2){
//		Map<Integer, Map<String, Double>> result = Maps.newHashMap();
//
//		m1.forEach((integer, map) -> {
//			result.put(integer, Maps.newHashMap(map));
//		});
//
//		m2.forEach((integer, map) -> {
//			Map<String, Double> orDefault = result.getOrDefault(integer, Maps.newHashMap());
//			orDefault.putAll(map);
//			result.put(integer, orDefault);
//		});
//
//		return result;
//	}

	public static <T> Map<Integer, Map<String, T>> mergeMapOfMaps(Map<Integer, Map<String, T>> m1, Map<Integer, Map<String, T>> m2){
		Map<Integer, Map<String, T>> result = Maps.newHashMap();

		m1.forEach((integer, map) -> {
			result.put(integer, Maps.newHashMap(map));
		});

		m2.forEach((integer, map) -> {
			Map<String, T> orDefault = result.getOrDefault(integer, Maps.newHashMap());
			orDefault.putAll(map);
			result.put(integer, orDefault);
		});

		return result;
	}

//	public static List<Double> fillStakes(List<Double> stakes, double defaultStake) {
//		return stakes.stream()
//				.map(st -> st == null ? defaultStake : st)
//				.collect(Collectors.toList())
//				;
//	}

	public static String getNameFromNumber(int index) {
		switch (index) {
			case 4:  return "Quadruple";
			case 3:  return "Triple";
			case 2:  return "Double";
			case 1:  return "Single";
			default: return index + "-fold";
		}
	}
}

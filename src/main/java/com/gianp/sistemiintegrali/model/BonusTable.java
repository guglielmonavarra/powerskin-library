package com.gianp.sistemiintegrali.model;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

@Getter
public class BonusTable {
	private List<Double> multipliers = Lists.newArrayList();
	private Double oddsThr;

	public static BonusTable getTestInstance(){
		BonusTable result = new BonusTable();

		result.multipliers.add(1.0);
		result.multipliers.add(1.0);
		result.multipliers.add(1.0);
		result.multipliers.add(1.0);
		result.multipliers.add(1.0);
		result.multipliers.add(1.05);
		result.multipliers.add(1.08);
		result.multipliers.add(1.11);
		result.multipliers.add(1.14);
		result.multipliers.add(1.17);
		result.multipliers.add(1.2);
		result.multipliers.add(1.23);
		result.multipliers.add(1.26);
		result.multipliers.add(1.29);
		result.multipliers.add(1.32);
		result.multipliers.add(1.35);
		result.multipliers.add(1.38);
		result.multipliers.add(1.41);
		result.multipliers.add(1.44);
		result.multipliers.add(1.47);
		result.multipliers.add(1.5);
		result.multipliers.add(1.53);
		result.multipliers.add(1.56);
		result.multipliers.add(1.59);
		result.multipliers.add(1.62);
		result.multipliers.add(1.65);
		result.multipliers.add(1.68);
		result.multipliers.add(1.71);
		result.multipliers.add(1.74);
		result.multipliers.add(1.77);
		result.multipliers.add(1.8);

		result.oddsThr = 1.5;

		return result;
	}

}

package com.gianp.sistemiintegrali.model;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OutputObj {

	private List<OutputRow> outputRowList = Lists.newArrayList();
	private double minOdds;
	private double maxOdds;
	private double minBonus;
	private double maxBonus;
	private double minWin;
	private double maxWin;


}

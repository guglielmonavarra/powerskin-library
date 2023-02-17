package com.gianp.sistemiintegrali.model;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class InputObj {

	private List<InputRow> oddsRows = Lists.newArrayList();
	private int defaultStake = 1;
	private List<Integer> stakes = Lists.newArrayList();

}

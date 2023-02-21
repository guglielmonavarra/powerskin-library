package com.gianp.sistemiintegrali.model;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class InputObj {

	private List<InputRow> oddsRows = Lists.newArrayList();
	private List<Double> stakes = Lists.newArrayList();

}

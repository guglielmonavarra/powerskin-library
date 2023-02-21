package com.gianp.sistemiintegrali.model;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class InputRow {

	private boolean fixed;
	private List<Double> list = Lists.newArrayList();

}

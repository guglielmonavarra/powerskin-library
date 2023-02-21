package com.gianp.sistemiintegrali.model;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class OutputRow {

	private String type;
	private Integer n;
	private Double maxWin;
	private Double minWin;
	private Double toPay;

}

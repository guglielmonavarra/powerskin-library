package com.gianp.sistemiintegrali.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
public class MyNode {

	private String ID; // key
	private int myEvent;
	private Double myValue;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MyNode myNode = (MyNode) o;
		return Objects.equals(ID, myNode.ID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}
}

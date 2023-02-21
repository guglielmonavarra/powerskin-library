package com.gianp.sistemiintegrali.model;

import com.gianp.sistemiintegrali.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.YenKShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.SimpleGraph;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class MyTree {

	private Graph<MyNode, DefaultEdge> dag = new DirectedAcyclicGraph<>(DefaultEdge.class);

	private double possibleWin = 0.0;
	private BonusTable bonusTable;
	private Map<Integer, List<Double>> rowWins = Maps.newHashMap(); //1-index
	private double bonusAdded = 0.0;


	public int countPaths(MyNode v1, MyNode v2){
		YenKShortestPath<MyNode, DefaultEdge> pathCounter = new YenKShortestPath<>(dag);
		List<GraphPath<MyNode, DefaultEdge>> pathsList = pathCounter.getPaths(v1, v2, Integer.MAX_VALUE);

		for (GraphPath<MyNode, DefaultEdge> path : pathsList) {
			double cumOdds = 1.0;
			List<MyNode> nodeChain = path.getVertexList();
			for (MyNode myNode : nodeChain) {
				cumOdds *= myNode.getMyValue();
			}
			double multiplier = getMultiplierFromBonusTable(Utils.nElementsOverThr(nodeChain, bonusTable.getOddsThr()));
			possibleWin += (cumOdds * multiplier);
			bonusAdded += (cumOdds * (multiplier - 1));
			List<Double> rowWin = rowWins.getOrDefault(nodeChain.size(), Lists.newArrayList());
			rowWin.add(cumOdds * multiplier);
			rowWins.put(nodeChain.size(), rowWin);
			// log combz (vba: isSaveCombz)
			StringBuffer tempStr = new StringBuffer();
			nodeChain.stream().map(MyNode::getID).forEach(id -> tempStr.append(id+"/"));
			//log.debug( (tempStr.toString() + (cumOdds*multiplier)).replace(".",",") );
		}

		return pathsList.size();
	}

	public double getMultiplierFromBonusTable(int position){
		try{
			return bonusTable.getMultipliers().get(position);
		} catch (IndexOutOfBoundsException e){
			return 1.0;
		}
	}


	public void addVertex(MyNode node) {
		dag.addVertex(node);
	}

	public void addEdge(MyNode n1, MyNode n2) {
		dag.addEdge(n1, n2);
	}

	public Set<MyNode> vertexSet() {
		return dag.vertexSet();
	}
}

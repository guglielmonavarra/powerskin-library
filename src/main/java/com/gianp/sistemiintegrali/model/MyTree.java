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
import java.util.stream.Collectors;

@Data
public class MyTree {

	private Graph<MyNode, DefaultEdge> dag = new DirectedAcyclicGraph<>(DefaultEdge.class);

	private double possibleWin = 0.0;
	private BonusTable bonusTable;
	private Map<Integer, Map<String, Double>> rowWins = Maps.newHashMap(); //1-index
	private double bonusAdded = 0.0;

	private List<Double> bonuses = Lists.newArrayList();
	private Map<Integer, Double> maxOdds = Maps.newHashMap(); //1-index
	private Map<Integer, Map<String, List<MyNode>>> combz = Maps.newHashMap(); //1-index


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
			bonuses.add( (cumOdds * (multiplier - 1)) );

			Map<String, Double> rowWin = rowWins.getOrDefault(nodeChain.size(), Maps.newHashMap());
			rowWin.put(nodeChainToStr(nodeChain), cumOdds * multiplier);
			rowWins.put(nodeChain.size(), rowWin);

			Double maxOddOrDefault = maxOdds.getOrDefault(nodeChain.size(), 0.0);
			maxOdds.put(nodeChain.size(), Double.max(maxOddOrDefault, nodeChainMax(nodeChain)));

			Map<String, List<MyNode>> combAtRow = combz.getOrDefault(nodeChain.size(), Maps.newHashMap());
			combAtRow.put(nodeChainToStr(nodeChain), nodeChain);
			combz.put(nodeChain.size(), combAtRow);

			// log combz (vba: isSaveCombz)
//			StringBuffer tempStr = new StringBuffer();
//			nodeChain.stream().map(MyNode::getID).forEach(id -> tempStr.append(id+"/"));
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

	private String nodeChainToStr(List<MyNode> nodeChain){
		return nodeChain.stream().map(MyNode::getID).collect(Collectors.joining("/"));
	}

	private Double nodeChainMax(List<MyNode> nodeChain){
		return nodeChain.stream().mapToDouble(MyNode::getMyValue).max().getAsDouble();
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

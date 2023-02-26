package com.gianp.sistemiintegrali.model;

import com.gianp.sistemiintegrali.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class MySlip {

	private MyTree completeDag = new MyTree();
	private List<List<MyNode>> slipRows = Lists.newArrayList(); //0-index
	private Set<Integer> fixedRows = Sets.newHashSet(); //1-index
	private List<Double> stakes = Lists.newArrayList(); //0-indexed
	private Double possibleWinC = 0.0;
	private Double bonusAddedC = 0.0;

	private List<Double> bonuses = Lists.newArrayList();

	public int getNevent(){
		return slipRows.size();
	}


	public static MySlip buildFromRange(List<InputRow> range){
		MySlip result = new MySlip();

		int eventNumber = 0;
		for (InputRow row : range) {
			eventNumber++;
			if (row.isFixed()) {
				result.fixedRows.add(eventNumber);
			}
			List<MyNode> slipColumnsOfRow = Lists.newArrayList();
			result.slipRows.add(slipColumnsOfRow);

//			for (Double d : row.getList()) {
			for(int id = 0; id < row.getList().size(); id++){
				Double d = row.getList().get(id);
				MyNode tempNode = new MyNode(eventNumber + "," + (id+1), eventNumber, d);
				result.completeDag.addVertex(tempNode);
				slipColumnsOfRow.add(tempNode);

				if (eventNumber != range.size()){ //se non è l'ultima row
					InputRow row1 = range.get(eventNumber); //prendo la riga successiva
//					for (Double d1 : row1.getList()) {
					for(int id1 = 0; id1 < row1.getList().size(); id1++){
						Double d1 = row1.getList().get(id1);
						MyNode tempNode1 = new MyNode((eventNumber + 1) + "," + (id1 + 1), (eventNumber + 1), d1);
						result.completeDag.addVertex(tempNode1);
						result.completeDag.addEdge(tempNode, tempNode1);
					}
				}
			}

		}


		return result;
	}

	//Builds the slip from a starting range, only for events with a given ID
	public static MyTree buildFromRangeOnlyEvents(List<InputRow> range, List<Integer> evIDs){
		MyTree result = new MyTree();

		for (int i = 0; i < evIDs.size() - 1; i++){
			Integer evID = evIDs.get(i);
			InputRow row = range.get(evID - 1);

//			for (Double d : row.getList()) {
			for(int id = 0; id < row.getList().size(); id++){
				Double d = row.getList().get(id);
				MyNode tempNode = new MyNode(evID + "," + (id+1), evID, d);
				result.addVertex(tempNode);

				//slipColumnsOfRow.add(tempNode); //non dovrebbe servire. a me sembra una banale sovrascrittura

				Integer evID1 = evIDs.get(i + 1); //prendo la riga successiva
				InputRow row1 = range.get(evID1 - 1);
//				for (Double d1 : row1.getList()) {
				for(int id1 = 0; id1 < row1.getList().size(); id1++){
					Double d1 = row1.getList().get(id1);
					MyNode tempNode1 = new MyNode(evID1 + "," + (id1 + 1), evID1, d1);
					result.addVertex(tempNode1);
					result.addEdge(tempNode, tempNode1);
				}
			}

		}

		return result;
	}

	//Returns the number of combinations associated with a kple
	// k 1-index
	public int getKpla(int k, List<InputRow> range){
		//Combinations with a number of elements less than the fixed rows are not possible
		int result = 0;

		if (k < fixedRows.size()) return 0;

		if (k == slipRows.size() /* nEvents */){
			for (MyNode key : slipRows.get(0)) {
				for (MyNode key1 : slipRows.get( slipRows.size()-1 )) {
					result += completeDag.countPaths(key, key1);
				}
			}
			possibleWinC += (completeDag.getPossibleWin() * getStake(0)); // getStake(k-1));
			bonusAddedC += (completeDag.getBonusAdded() * getStake(0)); // getStake(k-1));
			bonuses.addAll(completeDag.getBonuses());
			return result;
		}

		//Special case, single
		if (k == 1){
			completeDag.getRowWins().put(1, Maps.newHashMap());
			if (fixedRows.isEmpty()){
				result = completeDag.vertexSet().size();
				for (List<MyNode> slipRow : slipRows) {
					double tempWin = 0.0;
					double tempBonus = 0.0;
					for (MyNode myNode : slipRow) {
						double multiplier = completeDag.getMultiplierFromBonusTable(Utils.nElementsOverThr(Lists.newArrayList(myNode), completeDag.getBonusTable().getOddsThr()));
						completeDag.getRowWins().get(1).put(myNode.getID(), myNode.getMyValue() * multiplier);

						Double maxOddOrDefault = completeDag.getMaxOdds().getOrDefault(1, 0.0);
						completeDag.getMaxOdds().put(1, Double.max(maxOddOrDefault,  myNode.getMyValue() * multiplier));

						Map<String, List<MyNode>> combAtRow = completeDag.getCombz().getOrDefault(1, Maps.newHashMap());
						combAtRow.put(myNode.getID(), Lists.newArrayList(myNode));
						completeDag.getCombz().put(1, combAtRow);
						//log combz
						//log.debug( (myNode.getID()+ "/" + (myNode.getMyValue() * multiplier)).replace(".",",") );
						tempWin += (myNode.getMyValue() * multiplier);
						tempBonus += (myNode.getMyValue() * (multiplier-1) );
						bonuses.add(myNode.getMyValue() * (multiplier-1) );
					}
					possibleWinC += (tempWin * getStake(getNevent()-1 ) ); //getStake(0) );
					bonusAddedC += (tempBonus * getStake(getNevent()-1 ) ); //getStake(0) );
				}
			} else {
				for (Integer i : fixedRows) {
					double tempWin = 0.0;
					double tempBonus = 0.0;
					List<MyNode> nodeList = slipRows.get(i - 1);
					result += nodeList.size();
					for (MyNode myNode : nodeList) {
						double multiplier = completeDag.getMultiplierFromBonusTable(Utils.nElementsOverThr(Lists.newArrayList(myNode), completeDag.getBonusTable().getOddsThr()));
						completeDag.getRowWins().get(1).put(myNode.getID(), myNode.getMyValue() * multiplier);

						Double maxOddOrDefault = completeDag.getMaxOdds().getOrDefault(1, 0.0);
						completeDag.getMaxOdds().put(1, Double.max(maxOddOrDefault,  myNode.getMyValue() * multiplier));

						Map<String, List<MyNode>> combAtRow = completeDag.getCombz().getOrDefault(1, Maps.newHashMap());
						combAtRow.put(myNode.getID(), Lists.newArrayList(myNode));
						completeDag.getCombz().put(1, combAtRow);
						//log combz
						//log.debug( (myNode.getID()+ "/" + (myNode.getMyValue() * multiplier)).replace(".",",") );
						tempWin += (myNode.getMyValue() * multiplier);
						tempBonus += (myNode.getMyValue() * (multiplier-1) );
						bonuses.add(myNode.getMyValue() * (multiplier-1) );
					}
					possibleWinC += (tempWin * getStake(getNevent()-1 ) ); //getStake(0) );
					bonusAddedC += (tempBonus * getStake(getNevent()-1 ) ); //getStake(0) );
				}
			}
			return result;
		}



		List<List<Integer>> combz = Utils.getAllCombs(slipRows.size()+1, k);

		for (List<Integer> comb : combz) {
			//Skip combinations not containing the fixed rows
			if (!fixedRows.isEmpty()){
				int j = 0;
				for (Integer i : comb) {
					if (fixedRows.contains(i)) j++;
				}
				if (j < fixedRows.size()) continue;
			}

			MyTree tempDag = buildFromRangeOnlyEvents(range, comb);
			tempDag.setBonusTable(completeDag.getBonusTable());
			for (MyNode myNode : slipRows.get(comb.get(0) - 1)) {
				for (MyNode myNode1 : slipRows.get(comb.get(k - 1) - 1)) {
					result += tempDag.countPaths(myNode, myNode1);
				}
			}
			completeDag.setRowWins(Utils.mergeMapOfMaps(completeDag.getRowWins(), tempDag.getRowWins()));
			completeDag.getMaxOdds().putAll(tempDag.getMaxOdds());
			completeDag.setCombz(Utils.mergeMapOfMaps(completeDag.getCombz(), tempDag.getCombz()));
			possibleWinC += (tempDag.getPossibleWin() * getStake(getNevent()-k)); //getStake(k-1));
			bonusAddedC += (tempDag.getBonusAdded() * getStake(getNevent()-k)); //getStake(k-1));
			bonuses.addAll(tempDag.getBonuses());
		}

		return result;
	}

	public OutputObj writeNple(List<InputRow> oddsRows){
		OutputObj result = new OutputObj();
		double minOdds = Double.MAX_VALUE;
		double maxOdds = Double.MIN_VALUE;

		for (int i = slipRows.size(); i > 0; i--){
			int kpla = getKpla(i, oddsRows);
			Double currentStake = getStake(slipRows.size() - i);//getStake(i - 1);
			double maxWin = 0.0;

			if (kpla > 0){
				minOdds = Double.min(minOdds, completeDag.getRowWins().get(i).values().stream().mapToDouble(Double::doubleValue).min().getAsDouble());
				maxOdds = Double.max(maxOdds, completeDag.getRowWins().get(i).values().stream().mapToDouble(Double::doubleValue).max().getAsDouble());
				if (!isSystemIntegral(oddsRows)) {
					result.getOutputRowList().add(new OutputRow(
							Utils.getNameFromNumber(i),
							kpla,
							completeDag.getRowWins().get(i).values().stream().mapToDouble(Double::doubleValue).sum() * currentStake, //todo rundown2
							completeDag.getRowWins().get(i).values().stream().mapToDouble(Double::doubleValue).min().getAsDouble() * currentStake,
							kpla * currentStake
					));
				} else {

				}
			}

			result.getOutputRowList().add(new OutputRow(
					Utils.getNameFromNumber(i),
					kpla,
					kpla > 0 ? completeDag.getRowWins().get(i).stream().mapToDouble(Double::doubleValue).max().getAsDouble() * currentStake : null,
					kpla > 0 ? completeDag.getRowWins().get(i).stream().mapToDouble(Double::doubleValue).min().getAsDouble() * currentStake : null,
					kpla * currentStake
			));
		}

		result.setPossibleWin(possibleWinC);
		result.setBonusAdded(bonusAddedC);
		return result;
	}

	private boolean isSystemIntegral(List<InputRow> oddsRows) {
		for (InputRow oddsRow : oddsRows) {
			if (oddsRow.getList().size() > 1) return true;
		}
		return false;
	}

	private Double getStake(int index){
		try {
			return stakes.get(index);
		} catch (IndexOutOfBoundsException e){
			return 0.0;
		}
	}






}

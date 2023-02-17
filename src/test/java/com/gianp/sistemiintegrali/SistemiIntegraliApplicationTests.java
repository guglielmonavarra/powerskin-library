package com.gianp.sistemiintegrali;

import com.gianp.sistemiintegrali.engine.Engine;
import com.gianp.sistemiintegrali.model.*;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SistemiIntegraliApplicationTests {

	private Engine engine = new Engine();


	@Test
	void testEdges() {
		MyTree myTree = new MyTree();
		MyNode n1 = new MyNode("1", 0, 0.0);
		MyNode n2 = new MyNode("2", 0, 0.0);
		MyNode n3 = new MyNode("3", 0, 0.0);
		myTree.addVertex(n1);
		myTree.addVertex(n2);
		myTree.addVertex(n3);
		myTree.addEdge(n1,n2);
		myTree.addEdge(n1,n3);
		myTree.addEdge(n1,n3);
		System.out.println(myTree.getDag());

	}

	@Test
	void test1(){
		InputObj r = new InputObj();
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(1.45, 2.01) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.6) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.1) ));
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(3.5, 1.3, 1.7) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.65) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.01, 1.35) ));

		r.getStakes().add(2);
		r.getStakes().add(null);
		r.getStakes().add(null);
		r.getStakes().add(null);
		r.getStakes().add(null);
		r.getStakes().add(null);

		OutputObj run = engine.run(r);
		assertEquals(1973.925205434, run.getPossibleWin(), 0.0001);
		assertEquals(9.46328783400001, run.getBonusAdded(), 0.0001);

		assertEquals(6, run.getOutputRowList().size());

		assertEquals("6-fold", run.getOutputRowList().get(0).getType());
		assertEquals(12, run.getOutputRowList().get(0).getN());
		assertEquals(70.0647272325, run.getOutputRowList().get(0).getMaxWin(), 0.0001);
		assertEquals(12.00867525, run.getOutputRowList().get(0).getMinWin(), 0.0001);
		assertEquals(12, run.getOutputRowList().get(0).getToPay());

		assertEquals("5-fold", run.getOutputRowList().get(1).getType());
		assertEquals(42, run.getOutputRowList().get(1).getN());
		assertEquals(63.695206575, run.getOutputRowList().get(1).getMaxWin(), 0.0001);
		assertEquals(4.61872125, run.getOutputRowList().get(1).getMinWin(), 0.0001);
		assertEquals(42, run.getOutputRowList().get(1).getToPay());

		assertEquals("Quadruple", run.getOutputRowList().get(2).getType());
		assertEquals(54, run.getOutputRowList().get(2).getN());
		assertEquals(36.76491, run.getOutputRowList().get(2).getMaxWin(), 0.0001);
		assertEquals(2.799225, run.getOutputRowList().get(2).getMinWin(), 0.0001);
		assertEquals(54, run.getOutputRowList().get(2).getToPay());

		assertEquals("Triple", run.getOutputRowList().get(3).getType());
		assertEquals(30, run.getOutputRowList().get(3).getN());
		assertEquals(18.291, run.getOutputRowList().get(3).getMaxWin(), 0.0001);
		assertEquals(2.0735, run.getOutputRowList().get(3).getMinWin(), 0.0001);
		assertEquals(30, run.getOutputRowList().get(3).getToPay());

		assertEquals("Double", run.getOutputRowList().get(4).getType());
		assertEquals(6, run.getOutputRowList().get(4).getN());
		assertEquals(7.035, run.getOutputRowList().get(4).getMaxWin(), 0.0001);
		assertEquals(1.885, run.getOutputRowList().get(4).getMinWin(), 0.0001);
		assertEquals(6, run.getOutputRowList().get(4).getToPay());

		assertEquals("Single", run.getOutputRowList().get(5).getType());
		assertEquals(0, run.getOutputRowList().get(5).getN());
		assertNull(run.getOutputRowList().get(5).getMaxWin());
		assertNull(run.getOutputRowList().get(5).getMinWin());
		assertEquals(0, run.getOutputRowList().get(5).getToPay());

	}

	@Test
	void test2(){
		InputObj r = new InputObj();
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(1.45, 2.01) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.6) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.1) ));
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(3.5, 1.3, 1.7) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.65) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.01, 1.35) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(3.0, 2.5, 6.0) ));

		r.getStakes().add(null);
		r.getStakes().add(2);
		r.getStakes().add(null);
		r.getStakes().add(null);
		r.getStakes().add(4);
		r.getStakes().add(3);
		r.getStakes().add(null);

		OutputObj run = engine.run(r);
		assertEquals(67853.22204, run.getPossibleWin(), 0.0001);
		assertEquals(1284.945548, run.getBonusAdded(), 0.0001);

		assertEquals(7, run.getOutputRowList().size());

		assertEquals("7-fold", run.getOutputRowList().get(0).getType());
		assertEquals(36, run.getOutputRowList().get(0).getN());
		assertEquals(432.399459, run.getOutputRowList().get(0).getMaxWin(), 0.0001);
		assertEquals(30.021688, run.getOutputRowList().get(0).getMinWin(), 0.0001);
		assertEquals(36, run.getOutputRowList().get(0).getToPay());

		assertEquals("6-fold", run.getOutputRowList().get(1).getType());
		assertEquals(138, run.getOutputRowList().get(1).getN());
		assertEquals(1179.27125, run.getOutputRowList().get(1).getMaxWin(), 0.0001);
		assertEquals(34.640409, run.getOutputRowList().get(1).getMinWin(), 0.0001);
		assertEquals(414, run.getOutputRowList().get(1).getToPay());

		assertEquals("5-fold", run.getOutputRowList().get(2).getType());
		assertEquals(204, run.getOutputRowList().get(2).getN());
		assertEquals(926.47573, run.getOutputRowList().get(2).getMaxWin(), 0.0001);
		assertEquals(18.474885, run.getOutputRowList().get(2).getMinWin(), 0.0001);
		assertEquals(816, run.getOutputRowList().get(2).getToPay());

		assertEquals("Quadruple", run.getOutputRowList().get(3).getType());
		assertEquals(144, run.getOutputRowList().get(3).getN());
		assertEquals(109.746, run.getOutputRowList().get(3).getMaxWin(), 0.0001);
		assertEquals(2.799225, run.getOutputRowList().get(3).getMinWin(), 0.0001);
		assertEquals(144, run.getOutputRowList().get(3).getToPay());

		assertEquals("Triple", run.getOutputRowList().get(4).getType());
		assertEquals(48, run.getOutputRowList().get(4).getN());
		assertEquals(42.21, run.getOutputRowList().get(4).getMaxWin(), 0.0001);
		assertEquals(2.0735, run.getOutputRowList().get(4).getMinWin(), 0.0001);
		assertEquals(48, run.getOutputRowList().get(4).getToPay());

		assertEquals("Double", run.getOutputRowList().get(5).getType());
		assertEquals(6, run.getOutputRowList().get(5).getN());
		assertEquals(14.07, run.getOutputRowList().get(5).getMaxWin(), 0.0001);
		assertEquals(3.77, run.getOutputRowList().get(5).getMinWin(), 0.0001);
		assertEquals(12, run.getOutputRowList().get(5).getToPay());

		assertEquals("Single", run.getOutputRowList().get(6).getType());
		assertEquals(0, run.getOutputRowList().get(6).getN());
		assertNull(run.getOutputRowList().get(6).getMaxWin());
		assertNull(run.getOutputRowList().get(6).getMinWin());
		assertEquals(0, run.getOutputRowList().get(6).getToPay());

	}

	@Test
	void test3(){
		InputObj r = new InputObj();
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(1.45, 2.01) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.6) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.1) ));
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(3.5, 1.3, 1.7) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.65) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.01, 1.35) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(3.0, 2.5, 6.0) ));
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(2.55, 1.58) ));

		r.getStakes().add(null);
		r.getStakes().add(null);
		r.getStakes().add(2);
		r.getStakes().add(null);
		r.getStakes().add(null);
		r.getStakes().add(4);
		r.getStakes().add(3);
		r.getStakes().add(null);

		OutputObj run = engine.run(r);
		assertEquals(288342.83233803, run.getPossibleWin(), 0.0001);
		assertEquals(13415.85041, run.getBonusAdded(), 0.0001);

		assertEquals(8, run.getOutputRowList().size());

		assertEquals("8-fold", run.getOutputRowList().get(0).getType());
		assertEquals(72, run.getOutputRowList().get(0).getN());
		assertEquals(1133.24692, run.getOutputRowList().get(0).getMaxWin(), 0.0001);
		assertEquals(47.4342672, run.getOutputRowList().get(0).getMinWin(), 0.0001);
		assertEquals(72, run.getOutputRowList().get(0).getToPay());

		assertEquals(276, run.getOutputRowList().get(1).getN());
		assertEquals(3090.67341, run.getOutputRowList().get(1).getMaxWin(), 0.0001);
		assertEquals(54.73184, run.getOutputRowList().get(1).getMinWin(), 0.0001);
		assertEquals(828, run.getOutputRowList().get(1).getToPay());

		assertEquals(408, run.getOutputRowList().get(2).getN());
		assertEquals(2430.01349, run.getOutputRowList().get(2).getMaxWin(), 0.0001);
		assertEquals(29.1903183, run.getOutputRowList().get(2).getMinWin(), 0.0001);
		assertEquals(1632, run.getOutputRowList().get(2).getToPay());

		assertEquals(288, run.getOutputRowList().get(3).getN());
		assertEquals(293.844915, run.getOutputRowList().get(3).getMaxWin(), 0.0001);
		assertEquals(4.4227755, run.getOutputRowList().get(3).getMinWin(), 0.0001);
		assertEquals(288, run.getOutputRowList().get(3).getToPay());

		assertEquals(96, run.getOutputRowList().get(4).getN());
		assertEquals(107.6355, run.getOutputRowList().get(4).getMaxWin(), 0.0001);
		assertEquals(3.27613, run.getOutputRowList().get(4).getMinWin(), 0.0001);
		assertEquals(96, run.getOutputRowList().get(4).getToPay());

		assertEquals(12, run.getOutputRowList().get(5).getN());
		assertEquals(35.8785, run.getOutputRowList().get(5).getMaxWin(), 0.0001);
		assertEquals(5.9566, run.getOutputRowList().get(5).getMinWin(), 0.0001);
		assertEquals(24, run.getOutputRowList().get(5).getToPay());

		assertEquals(0, run.getOutputRowList().get(6).getN());
		assertNull(run.getOutputRowList().get(6).getMaxWin());
		assertNull(run.getOutputRowList().get(6).getMinWin());
		assertEquals(0, run.getOutputRowList().get(6).getToPay());

		assertEquals(0, run.getOutputRowList().get(7).getN());
		assertNull(run.getOutputRowList().get(7).getMaxWin());
		assertNull(run.getOutputRowList().get(7).getMinWin());
		assertEquals(0, run.getOutputRowList().get(7).getToPay());

	}

	@Test
	void test4(){
		InputObj r = new InputObj();
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.45, 2.01) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.6) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.1) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(3.5, 1.3, 1.7) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.65) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.01, 1.35) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(3.0, 2.5, 6.0) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.55, 1.58) ));

		r.getStakes().add(null);
		r.getStakes().add(null);
		r.getStakes().add(2);
		r.getStakes().add(null);
		r.getStakes().add(null);
		r.getStakes().add(4);
		r.getStakes().add(3);
		r.getStakes().add(null);

		OutputObj run = engine.run(r);
		assertEquals(494210.599584801, run.getPossibleWin(), 0.0001);
		assertEquals(19502.329336, run.getBonusAdded(), 0.0001);

		assertEquals(8, run.getOutputRowList().size());

		assertEquals("8-fold", run.getOutputRowList().get(0).getType());
		assertEquals(72, run.getOutputRowList().get(0).getN());
		assertEquals(1133.24692, run.getOutputRowList().get(0).getMaxWin(), 0.0001);
		assertEquals(47.4342672, run.getOutputRowList().get(0).getMinWin(), 0.0001);
		assertEquals(72, run.getOutputRowList().get(0).getToPay());

		assertEquals(372, run.getOutputRowList().get(1).getN());
		assertEquals(3090.67341, run.getOutputRowList().get(1).getMaxWin(), 0.0001);
		assertEquals(54.73184, run.getOutputRowList().get(1).getMinWin(), 0.0001);
		assertEquals(1116, run.getOutputRowList().get(1).getToPay());

		assertEquals(818, run.getOutputRowList().get(2).getN());
		assertEquals(2430.01349, run.getOutputRowList().get(2).getMaxWin(), 0.0001);
		assertEquals(29.1903183, run.getOutputRowList().get(2).getMinWin(), 0.0001);
		assertEquals(3272, run.getOutputRowList().get(2).getToPay());

		assertEquals(999, run.getOutputRowList().get(3).getN());
		assertEquals(293.844915, run.getOutputRowList().get(3).getMaxWin(), 0.0001);
		assertEquals(4.4227755, run.getOutputRowList().get(3).getMinWin(), 0.0001);
		assertEquals(999, run.getOutputRowList().get(3).getToPay());

		assertEquals(741, run.getOutputRowList().get(4).getN());
		assertEquals(139.23, run.getOutputRowList().get(4).getMaxWin(), 0.0001);
		assertEquals(2.799225, run.getOutputRowList().get(4).getMinWin(), 0.0001);
		assertEquals(741, run.getOutputRowList().get(4).getToPay());

		assertEquals(342, run.getOutputRowList().get(5).getN());
		assertEquals(109.2, run.getOutputRowList().get(5).getMaxWin(), 0.0001);
		assertEquals(3.861, run.getOutputRowList().get(5).getMinWin(), 0.0001);
		assertEquals(684, run.getOutputRowList().get(5).getToPay());

		assertEquals(96, run.getOutputRowList().get(6).getN());
		assertEquals(21, run.getOutputRowList().get(6).getMaxWin(), 0.0001);
		assertEquals(1.43, run.getOutputRowList().get(6).getMinWin(), 0.0001);
		assertEquals(96, run.getOutputRowList().get(6).getToPay());

		assertEquals(15, run.getOutputRowList().get(7).getN());
		assertEquals(6, run.getOutputRowList().get(7).getMaxWin(), 0.0001);
		assertEquals(1.1, run.getOutputRowList().get(7).getMinWin(), 0.0001);
		assertEquals(15, run.getOutputRowList().get(7).getToPay());

	}

	@Test
	void test5(){
		InputObj r = new InputObj();
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.45, 2.01) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.6) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.1) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(3.5, 1.3, 1.7) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.65) ));

		r.getStakes().add(null);
		r.getStakes().add(2);
		r.getStakes().add(4);
		r.getStakes().add(3);
		r.getStakes().add(null);

		OutputObj run = engine.run(r);
		assertEquals(1894.12218, run.getPossibleWin(), 0.0001);
		assertEquals(0, run.getBonusAdded(), 0.0001);

		assertEquals(5, run.getOutputRowList().size());

		assertEquals(6, run.getOutputRowList().get(0).getN());
		assertEquals(33.198165, run.getOutputRowList().get(0).getMaxWin(), 0.0001);
		assertEquals(8.895315, run.getOutputRowList().get(0).getMinWin(), 0.0001);
		assertEquals(6, run.getOutputRowList().get(0).getToPay());

		assertEquals(23, run.getOutputRowList().get(1).getN());
		assertEquals(90.54045, run.getOutputRowList().get(1).getMaxWin(), 0.0001);
		assertEquals(10.263825, run.getOutputRowList().get(1).getMinWin(), 0.0001);
		assertEquals(69, run.getOutputRowList().get(1).getToPay());

		assertEquals(34, run.getOutputRowList().get(2).getN());
		assertEquals(73.164, run.getOutputRowList().get(2).getMaxWin(), 0.0001);
		assertEquals(8.294, run.getOutputRowList().get(2).getMinWin(), 0.0001);
		assertEquals(136, run.getOutputRowList().get(2).getToPay());

		assertEquals(24, run.getOutputRowList().get(3).getN());
		assertEquals(18.2, run.getOutputRowList().get(3).getMaxWin(), 0.0001);
		assertEquals(2.86, run.getOutputRowList().get(3).getMinWin(), 0.0001);
		assertEquals(48, run.getOutputRowList().get(3).getToPay());

		assertEquals(8, run.getOutputRowList().get(4).getN());
		assertEquals(3.5, run.getOutputRowList().get(4).getMaxWin(), 0.0001);
		assertEquals(1.1, run.getOutputRowList().get(4).getMinWin(), 0.0001);
		assertEquals(8, run.getOutputRowList().get(4).getToPay());


	}

	@Test
	void test6(){
		InputObj r = new InputObj();
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.45, 2.01) ));
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(2.6) ));
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(1.1) ));
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(3.5, 1.3, 1.7) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.65) ));

		r.getStakes().add(null);
		r.getStakes().add(2);
		r.getStakes().add(4);
		r.getStakes().add(3);
		r.getStakes().add(null);

		OutputObj run = engine.run(r);
		assertEquals(465.47501, run.getPossibleWin(), 0.0001);
		assertEquals(0, run.getBonusAdded(), 0.0001);

		assertEquals(5, run.getOutputRowList().size());

		assertEquals(6, run.getOutputRowList().get(0).getN());
		assertEquals(33.198165, run.getOutputRowList().get(0).getMaxWin(), 0.0001);
		assertEquals(8.895315, run.getOutputRowList().get(0).getMinWin(), 0.0001);
		assertEquals(6, run.getOutputRowList().get(0).getToPay());

		assertEquals(9, run.getOutputRowList().get(1).getN());
		assertEquals(60.3603, run.getOutputRowList().get(1).getMaxWin(), 0.0001);
		assertEquals(16.1733, run.getOutputRowList().get(1).getMinWin(), 0.0001);
		assertEquals(27, run.getOutputRowList().get(1).getToPay());

		assertEquals(3, run.getOutputRowList().get(2).getN());
		assertEquals(40.04, run.getOutputRowList().get(2).getMaxWin(), 0.0001);
		assertEquals(14.872, run.getOutputRowList().get(2).getMinWin(), 0.0001);
		assertEquals(12, run.getOutputRowList().get(2).getToPay());

		assertEquals(0, run.getOutputRowList().get(3).getN());
		assertNull(run.getOutputRowList().get(3).getMaxWin());
		assertNull(run.getOutputRowList().get(3).getMinWin());
		assertEquals(0, run.getOutputRowList().get(3).getToPay());

		assertEquals(0, run.getOutputRowList().get(4).getN());
		assertNull(run.getOutputRowList().get(4).getMaxWin());
		assertNull(run.getOutputRowList().get(4).getMinWin());
		assertEquals(0, run.getOutputRowList().get(4).getToPay());


	}

	@Test
	void test7(){
		InputObj r = new InputObj();
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(2.6) ));
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(1.1) ));
		r.getOddsRows().add( new InputRow(true, Lists.newArrayList(3.5, 1.3, 1.7) ));

		r.getStakes().add(null);
		r.getStakes().add(null);
		r.getStakes().add(null);

		OutputObj run = engine.run(r);
		assertEquals(18.59, run.getPossibleWin(), 0.0001);
		assertEquals(0, run.getBonusAdded(), 0.0001);

		assertEquals(3, run.getOutputRowList().size());

		assertEquals(3, run.getOutputRowList().get(0).getN());
		assertEquals(10.01, run.getOutputRowList().get(0).getMaxWin(), 0.0001);
		assertEquals(3.718, run.getOutputRowList().get(0).getMinWin(), 0.0001);
		assertEquals(3, run.getOutputRowList().get(0).getToPay());

		assertEquals(0, run.getOutputRowList().get(1).getN());
		assertNull(run.getOutputRowList().get(1).getMaxWin());
		assertNull(run.getOutputRowList().get(1).getMinWin());
		assertEquals(0, run.getOutputRowList().get(1).getToPay());

		assertEquals(0, run.getOutputRowList().get(2).getN());
		assertNull(run.getOutputRowList().get(2).getMaxWin());
		assertNull(run.getOutputRowList().get(2).getMinWin());
		assertEquals(0, run.getOutputRowList().get(2).getToPay());


	}

	@Test
	void test8(){
		InputObj r = new InputObj();
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(2.6) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(1.1) ));
		r.getOddsRows().add( new InputRow(false, Lists.newArrayList(3.5, 1.3, 1.7) ));

		r.getStakes().add(null);
		r.getStakes().add(null);
		r.getStakes().add(null);

		OutputObj run = engine.run(r);
		assertEquals(55.70, run.getPossibleWin(), 0.0001);
		assertEquals(0, run.getBonusAdded(), 0.0001);

		assertEquals(3, run.getOutputRowList().size());

		assertEquals(3, run.getOutputRowList().get(0).getN());
		assertEquals(10.01, run.getOutputRowList().get(0).getMaxWin(), 0.0001);
		assertEquals(3.718, run.getOutputRowList().get(0).getMinWin(), 0.0001);
		assertEquals(3, run.getOutputRowList().get(0).getToPay());

		assertEquals(7, run.getOutputRowList().get(1).getN());
		assertEquals(9.1, run.getOutputRowList().get(1).getMaxWin(), 0.0001);
		assertEquals(1.43, run.getOutputRowList().get(1).getMinWin(), 0.0001);
		assertEquals(7, run.getOutputRowList().get(1).getToPay());

		assertEquals(5, run.getOutputRowList().get(2).getN());
		assertEquals(3.5, run.getOutputRowList().get(2).getMaxWin(), 0.0001);
		assertEquals(1.1, run.getOutputRowList().get(2).getMinWin(), 0.0001);
		assertEquals(5, run.getOutputRowList().get(2).getToPay());

	}
}

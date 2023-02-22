package com.gianp.sistemiintegrali;

import com.gianp.sistemiintegrali.engine.Engine;
import com.gianp.sistemiintegrali.model.*;
import com.gianp.sistemiintegrali.model.dto.BetCouponDto;
import com.gianp.sistemiintegrali.model.dto.CouponTrackerDto;
import com.gianp.sistemiintegrali.model.dto.ScheduleType;
import com.gianp.sistemiintegrali.model.dto.SetUnset;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SistemiIntegraliApplicationTests2 {

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
		CouponTrackerDto tracker = new CouponTrackerDto();

		BetCouponDto bet1 = new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				66L,
				new BigDecimal(5.7),
				"spread",
				77L,
				"mark id type",
				"mark name",
				11L,
				"amdi",
				"evtname",
				LocalDateTime.now(),
				5436563L,
				32L,
				"sport name",
				99L,
				"ctname",
				99L,
				"trname",
				false,
				"fds",
				"livecurrenttime",
				10L
		);

		tracker = engine.eval(tracker, bet1, SetUnset.SET);
		System.out.println(tracker);


		BetCouponDto bet2 = new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				66L,
				new BigDecimal(1.4),
				"spread",
				77L,
				"mark id type",
				"mark name",
				12L,
				"amdi",
				"evtname",
				LocalDateTime.now(),
				5436563L,
				32L,
				"sport name",
				99L,
				"ctname",
				99L,
				"trname",
				false,
				"fds",
				"livecurrenttime",
				10L
		);

		tracker = engine.eval(tracker, bet2, SetUnset.SET);

		System.out.println(tracker);
	}
}

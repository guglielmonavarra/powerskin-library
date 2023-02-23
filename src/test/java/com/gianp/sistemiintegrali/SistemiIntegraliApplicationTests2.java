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

		long evtId1 = 11L;
		long evtId2 = 112L;

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
				evtId1,
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

		tracker = engine.betCouponInteraction(tracker, bet1, SetUnset.SET);
//		System.out.println(tracker);


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

		tracker = engine.betCouponInteraction(tracker, bet2, SetUnset.SET);
		tracker = engine.setActiveList(tracker, Lists.newArrayList(true, true));
		tracker = engine.spreadStake(tracker);
		tracker = engine.eval(tracker);

		//System.out.println(tracker);
		assertEquals(2, tracker.getNumEvents());
		assertEquals(2, tracker.getSystem().size());
		assertEquals(1, tracker.getMultiplier());
		
		assertEquals("Double", tracker.getSystem().get(0).getType());
		assertEquals(1, tracker.getSystem().get(0).getCol());
		assertEquals(7.98, tracker.getSystem().get(0).getMaxWin().doubleValue(), 0.01);
		assertEquals(7.98, tracker.getSystem().get(0).getMinWin().doubleValue(), 0.01);
		assertEquals(1, tracker.getSystem().get(0).getToPay().doubleValue(), 0.01);

		assertEquals("Single", tracker.getSystem().get(1).getType());
		assertEquals(2, tracker.getSystem().get(1).getCol());
		assertEquals(5.70, tracker.getSystem().get(1).getMaxWin().doubleValue(), 0.01);
		assertEquals(1.40, tracker.getSystem().get(1).getMinWin().doubleValue(), 0.01);
		assertEquals(2, tracker.getSystem().get(1).getToPay().doubleValue(), 0.01);

		BetCouponDto bet3 = new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				666L,
				new BigDecimal(3.6),
				"spread",
				77L,
				"mark id type",
				"mark name",
				13L,
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

		//mi aspetto che nulla sia cambiato se aggiungo un bet, e poi lo rimuovo
		tracker = engine.betCouponInteraction(tracker, bet3, SetUnset.SET);
		tracker = engine.setActiveList(tracker, Lists.newArrayList(true, true, true));
		tracker = engine.spreadStake(tracker);
		tracker = engine.eval(tracker);

		tracker = engine.betCouponInteraction(tracker, bet3, SetUnset.UNSET);
		tracker = engine.setActiveList(tracker, Lists.newArrayList(true, true));
		tracker = engine.spreadStake(tracker);
		tracker = engine.eval(tracker);

		assertEquals(2, tracker.getNumEvents());
		assertEquals(2, tracker.getSystem().size());
		assertEquals(1, tracker.getMultiplier());

		assertEquals("Double", tracker.getSystem().get(0).getType());
		assertEquals(1, tracker.getSystem().get(0).getCol());
		assertEquals(7.98, tracker.getSystem().get(0).getMaxWin().doubleValue(), 0.01);
		assertEquals(7.98, tracker.getSystem().get(0).getMinWin().doubleValue(), 0.01);
		assertEquals(1, tracker.getSystem().get(0).getToPay().doubleValue(), 0.01);

		assertEquals("Single", tracker.getSystem().get(1).getType());
		assertEquals(2, tracker.getSystem().get(1).getCol());
		assertEquals(5.70, tracker.getSystem().get(1).getMaxWin().doubleValue(), 0.01);
		assertEquals(1.40, tracker.getSystem().get(1).getMinWin().doubleValue(), 0.01);
		assertEquals(2, tracker.getSystem().get(1).getToPay().doubleValue(), 0.01);



		tracker = engine.betCouponInteraction(tracker, bet2, SetUnset.UNSET);
		BetCouponDto bet12 = new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				661L,
				new BigDecimal(1.67),
				"spread",
				77L,
				"mark id type",
				"mark name",
				evtId1,
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

		tracker = engine.betCouponInteraction(tracker, bet12, SetUnset.SET);
		tracker = engine.fixedInteraction(tracker, evtId1, SetUnset.SET);

		BetCouponDto bet21 = new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				671L,
				new BigDecimal(5),
				"spread",
				77L,
				"mark id type",
				"mark name",
				evtId2,
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

		tracker = engine.betCouponInteraction(tracker, bet21, SetUnset.SET);
		tracker = engine.spreadStake(tracker);
		tracker = engine.eval(tracker);
		System.out.println(tracker);

		assertEquals(3, tracker.getNumEvents());
		assertEquals(2, tracker.getMultiplier());

		assertEquals(evtId1, tracker.getEvents().get(0).getId());
		assertEquals(2, tracker.getEvents().get(0).getComb());
		assertEquals(evtId1, tracker.getEvents().get(1).getId());
		assertEquals(2, tracker.getEvents().get(1).getComb());
		assertEquals(evtId2, tracker.getEvents().get(2).getId());
		assertEquals(1, tracker.getEvents().get(2).getComb());

		assertEquals(2, tracker.getSystem().size());

		assertEquals("Double", tracker.getSystem().get(0).getType());
		assertEquals(2, tracker.getSystem().get(0).getCol());
		assertEquals(21.375, tracker.getSystem().get(0).getMaxWin().doubleValue(), 0.001);
		assertEquals(6.2625, tracker.getSystem().get(0).getMinWin().doubleValue(), 0.001);
		assertEquals(1.5, tracker.getSystem().get(0).getToPay().doubleValue(), 0.01);

		assertEquals("Single", tracker.getSystem().get(1).getType());
		assertEquals(2, tracker.getSystem().get(1).getCol());
		assertEquals(4.275, tracker.getSystem().get(1).getMaxWin().doubleValue(), 0.001);
		assertEquals(1.2525, tracker.getSystem().get(1).getMinWin().doubleValue(), 0.001);
		assertEquals(1.5, tracker.getSystem().get(1).getToPay().doubleValue(), 0.01);

	}

	@Test
	//test senza impostare active. comportamento atteso di default: tutto lo stake sulla k-pla piu grande
	void test2(){
		CouponTrackerDto tracker = new CouponTrackerDto();

		long evtId1 = 11L;
		long evtId2 = 112L;

		BetCouponDto bet11 = new BetCouponDto(
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
				evtId1,
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

		tracker = engine.betCouponInteraction(tracker, bet11, SetUnset.SET);

		BetCouponDto bet12 = new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				661L,
				new BigDecimal(1.67),
				"spread",
				77L,
				"mark id type",
				"mark name",
				evtId1,
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

		tracker = engine.betCouponInteraction(tracker, bet12, SetUnset.SET);
		tracker = engine.fixedInteraction(tracker, evtId1, SetUnset.SET);

		BetCouponDto bet21 = new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				671L,
				new BigDecimal(5),
				"spread",
				77L,
				"mark id type",
				"mark name",
				evtId2,
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

		tracker = engine.betCouponInteraction(tracker, bet21, SetUnset.SET);
		tracker = engine.spreadStake(tracker);
		tracker = engine.eval(tracker);
		System.out.println(tracker);

		assertEquals(3, tracker.getNumEvents());
		assertEquals(2, tracker.getMultiplier());

		assertEquals(evtId1, tracker.getEvents().get(0).getId());
		assertEquals(2, tracker.getEvents().get(0).getComb());
		assertEquals(evtId1, tracker.getEvents().get(1).getId());
		assertEquals(2, tracker.getEvents().get(1).getComb());
		assertEquals(evtId2, tracker.getEvents().get(2).getId());
		assertEquals(1, tracker.getEvents().get(2).getComb());

		assertEquals(2, tracker.getSystem().size());

		assertEquals("Double", tracker.getSystem().get(0).getType());
		assertEquals(2, tracker.getSystem().get(0).getCol());
		assertEquals(42.75, tracker.getSystem().get(0).getMaxWin().doubleValue(), 0.001);
		assertEquals(12.525, tracker.getSystem().get(0).getMinWin().doubleValue(), 0.001);
		assertEquals(3, tracker.getSystem().get(0).getToPay().doubleValue(), 0.01); //pay 6 perchè stake 3

		assertEquals("Single", tracker.getSystem().get(1).getType());
		assertEquals(2, tracker.getSystem().get(1).getCol());
		assertEquals(0, tracker.getSystem().get(1).getMaxWin().doubleValue(), 0.001); //tutto 0, stake 0
		assertEquals(0, tracker.getSystem().get(1).getMinWin().doubleValue(), 0.001);
		assertEquals(0, tracker.getSystem().get(1).getToPay().doubleValue(), 0.01);

		//l'utente ora imposta stake a piacimento. 4 sul double, 0.75 sul single
		tracker = engine.setStakeFromUser(tracker, Lists.newArrayList(BigDecimal.valueOf(4), BigDecimal.valueOf(0.75)));
		tracker = engine.eval(tracker);

		assertEquals(3, tracker.getNumEvents());
		assertEquals(2, tracker.getMultiplier());

		assertEquals(evtId1, tracker.getEvents().get(0).getId());
		assertEquals(2, tracker.getEvents().get(0).getComb());
		assertEquals(evtId1, tracker.getEvents().get(1).getId());
		assertEquals(2, tracker.getEvents().get(1).getComb());
		assertEquals(evtId2, tracker.getEvents().get(2).getId());
		assertEquals(1, tracker.getEvents().get(2).getComb());

		assertEquals(2, tracker.getSystem().size());

		assertEquals("Double", tracker.getSystem().get(0).getType());
		assertEquals(2, tracker.getSystem().get(0).getCol());
		assertEquals(114, tracker.getSystem().get(0).getMaxWin().doubleValue(), 0.001);
		assertEquals(33.4, tracker.getSystem().get(0).getMinWin().doubleValue(), 0.001);
		assertEquals(8, tracker.getSystem().get(0).getToPay().doubleValue(), 0.01);

		assertEquals("Single", tracker.getSystem().get(1).getType());
		assertEquals(2, tracker.getSystem().get(1).getCol());
		assertEquals(4.275, tracker.getSystem().get(1).getMaxWin().doubleValue(), 0.001);
		assertEquals(1.2525, tracker.getSystem().get(1).getMinWin().doubleValue(), 0.001);
		assertEquals(1.5, tracker.getSystem().get(1).getToPay().doubleValue(), 0.01);

	}
}

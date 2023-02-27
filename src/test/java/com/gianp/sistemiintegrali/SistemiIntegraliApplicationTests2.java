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
		//System.out.println(myTree.getDag());

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

		assertEquals(1, tracker.getSystem().size());

		assertEquals("Double", tracker.getSystem().get(0).getType());
		assertEquals(2, tracker.getSystem().get(0).getCol());
		assertEquals(42.75, tracker.getSystem().get(0).getMaxWin().doubleValue(), 0.01);
		assertEquals(12.52, tracker.getSystem().get(0).getMinWin().doubleValue(), 0.01);
		assertEquals(3, tracker.getSystem().get(0).getToPay().doubleValue(), 0.01);

		//la single non esiste, non è attiva
//		assertEquals("Single", tracker.getSystem().get(1).getType());
//		assertEquals(2, tracker.getSystem().get(1).getCol());
//		assertEquals(0, tracker.getSystem().get(1).getMaxWin().doubleValue(), 0.01);
//		assertEquals(0, tracker.getSystem().get(1).getMinWin().doubleValue(), 0.01);
//		assertEquals(0, tracker.getSystem().get(1).getToPay().doubleValue(), 0.01);

		//totali
		assertEquals(12.52, tracker.getMinWin().doubleValue(), 0.01);
		assertEquals(42.75, tracker.getMaxWin().doubleValue(), 0.01);
		assertEquals(0, tracker.getMinBonus().doubleValue(), 0.01);
		assertEquals(0, tracker.getMaxBonus().doubleValue(), 0.01);
		assertEquals(8.35, tracker.getMinOdds().doubleValue(), 0.01);
		assertEquals(28.50, tracker.getMaxOdds().doubleValue(), 0.01);


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
		assertEquals(114, tracker.getSystem().get(0).getMaxWin().doubleValue(), 0.01);
		assertEquals(33.4, tracker.getSystem().get(0).getMinWin().doubleValue(), 0.01);
		assertEquals(8, tracker.getSystem().get(0).getToPay().doubleValue(), 0.01);

		//ora ha scommesso anche sul single, la riga single è ora presente
		assertEquals("Single", tracker.getSystem().get(1).getType());
		assertEquals(2, tracker.getSystem().get(1).getCol());
		assertEquals(4.27, tracker.getSystem().get(1).getMaxWin().doubleValue(), 0.01);
		assertEquals(1.25, tracker.getSystem().get(1).getMinWin().doubleValue(), 0.01);
		assertEquals(1.5, tracker.getSystem().get(1).getToPay().doubleValue(), 0.01);

		//totali
		assertEquals(1.25, tracker.getMinWin().doubleValue(), 0.01);
		assertEquals(118.27, tracker.getMaxWin().doubleValue(), 0.01);
		assertEquals(0, tracker.getMinBonus().doubleValue(), 0.01);
		assertEquals(0, tracker.getMaxBonus().doubleValue(), 0.01);
		assertEquals(8.35, tracker.getMinOdds().doubleValue(), 0.01);
		assertEquals(28.50, tracker.getMaxOdds().doubleValue(), 0.01);



		//mi aspetto che nulla cambia se aggiungo un bet, e poi lo rimuovo
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

		tracker = engine.betCouponInteraction(tracker, bet3, SetUnset.SET);
		tracker = engine.eval(tracker);

		tracker = engine.betCouponInteraction(tracker, bet3, SetUnset.UNSET);
		tracker = engine.eval(tracker);


		//ripeto gli stessi test di sopra, mi aspetto che nulla sia cambiato
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
		assertEquals(114, tracker.getSystem().get(0).getMaxWin().doubleValue(), 0.01);
		assertEquals(33.4, tracker.getSystem().get(0).getMinWin().doubleValue(), 0.01);
		assertEquals(8, tracker.getSystem().get(0).getToPay().doubleValue(), 0.01);

		//ora ha scommesso anche sul single, la riga single è ora presente
		assertEquals("Single", tracker.getSystem().get(1).getType());
		assertEquals(2, tracker.getSystem().get(1).getCol());
		assertEquals(4.27, tracker.getSystem().get(1).getMaxWin().doubleValue(), 0.01);
		assertEquals(1.25, tracker.getSystem().get(1).getMinWin().doubleValue(), 0.01);
		assertEquals(1.5, tracker.getSystem().get(1).getToPay().doubleValue(), 0.01);

		//totali
		assertEquals(1.25, tracker.getMinWin().doubleValue(), 0.01);
		assertEquals(118.27, tracker.getMaxWin().doubleValue(), 0.01);
		assertEquals(0, tracker.getMinBonus().doubleValue(), 0.01);
		assertEquals(0, tracker.getMaxBonus().doubleValue(), 0.01);
		assertEquals(8.35, tracker.getMinOdds().doubleValue(), 0.01);
		assertEquals(28.50, tracker.getMaxOdds().doubleValue(), 0.01);
	}

	@Test
	void test3(){
		CouponTrackerDto tracker = new CouponTrackerDto();
		
		tracker = engine.betCouponInteraction(tracker, new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				11L,
				new BigDecimal(1.19),
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
		), SetUnset.SET);

		tracker = engine.betCouponInteraction(tracker, new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				22L,
				new BigDecimal(1.16),
				"spread",
				77L,
				"mark id type",
				"mark name",
				22L,
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
		), SetUnset.SET);

		tracker = engine.betCouponInteraction(tracker, new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				33L,
				new BigDecimal(1.24),
				"spread",
				77L,
				"mark id type",
				"mark name",
				33L,
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
		), SetUnset.SET);

		tracker = engine.betCouponInteraction(tracker, new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				44L,
				44L,
				new BigDecimal(1.2),
				"spread",
				77L,
				"mark id type",
				"mark name",
				44L,
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
		), SetUnset.SET);

		tracker = engine.betCouponInteraction(tracker, new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				55L,
				new BigDecimal(1.15),
				"spread",
				77L,
				"mark id type",
				"mark name",
				55L,
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
		), SetUnset.SET);

		tracker = engine.betCouponInteraction(tracker, new BetCouponDto(
				1,
				"sdf",
				ScheduleType.PREMATCH,
				"sel name",
				55L,
				66L,
				new BigDecimal(1.18),
				"spread",
				77L,
				"mark id type",
				"mark name",
				66L,
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
		), SetUnset.SET);

		tracker = engine.setDefaultStake(tracker, BigDecimal.valueOf(22.0));
		tracker = engine.setActiveList(tracker, Lists.newArrayList(true, true, true)); //solo i primi 3 sono active, i restanti no
		tracker = engine.spreadStake(tracker);
		tracker = engine.eval(tracker);
		System.out.println(tracker);

		assertEquals(6, tracker.getNumEvents());
		assertEquals(1, tracker.getMultiplier());

		assertEquals(3, tracker.getSystem().size());

		assertEquals("6-fold", tracker.getSystem().get(0).getType());
		assertEquals(1, tracker.getSystem().get(0).getCol());
		assertEquals(2.78, tracker.getSystem().get(0).getMaxWin().doubleValue(), 0.01);
		assertEquals(2.78, tracker.getSystem().get(0).getMinWin().doubleValue(), 0.01);
		assertEquals(1, tracker.getSystem().get(0).getToPay().doubleValue(), 0.01);

		assertEquals("5-fold", tracker.getSystem().get(1).getType());
		assertEquals(6, tracker.getSystem().get(1).getCol());
		assertEquals(14.10, tracker.getSystem().get(1).getMaxWin().doubleValue(), 0.01);
		assertEquals(2.24, tracker.getSystem().get(1).getMinWin().doubleValue(), 0.01);
		assertEquals(6, tracker.getSystem().get(1).getToPay().doubleValue(), 0.01);

		assertEquals("Quadruple", tracker.getSystem().get(2).getType());
		assertEquals(15, tracker.getSystem().get(2).getCol());
		assertEquals(29.72, tracker.getSystem().get(2).getMaxWin().doubleValue(), 0.01);
		assertEquals(1.87, tracker.getSystem().get(2).getMinWin().doubleValue(), 0.01);
		assertEquals(15, tracker.getSystem().get(2).getToPay().doubleValue(), 0.01);

		

		//totali
		assertEquals(1.87, tracker.getMinWin().doubleValue(), 0.01);
		assertEquals(46.61, tracker.getMaxWin().doubleValue(), 0.01);
		assertEquals(0, tracker.getMinBonus().doubleValue(), 0.01);
		assertEquals(0, tracker.getMaxBonus().doubleValue(), 0.01);
		assertEquals(2.78, tracker.getMinOdds().doubleValue(), 0.01);
		assertEquals(2.78, tracker.getMaxOdds().doubleValue(), 0.01);


		
	}
	
	
}

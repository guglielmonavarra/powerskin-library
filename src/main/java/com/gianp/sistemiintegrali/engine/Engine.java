package com.gianp.sistemiintegrali.engine;


import com.gianp.sistemiintegrali.model.*;
import com.gianp.sistemiintegrali.model.dto.*;
import com.google.common.collect.Lists;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Engine {


    public static List<Boolean> active = Lists.newArrayList(true, true, false); //todo: prendere gli active
    private double defaultStake = 3.0;

//    public OutputObj run(InputObj in) {
//        MySlip mySlip = MySlip.buildFromRange(in.getOddsRows());
//        mySlip.getCompleteDag().setBonusTable(BonusTable.getTestInstance());
//
//        return mySlip.writeNple(in.getOddsRows());
//    }

    public CouponTrackerDto eval(CouponTrackerDto tracker, BetCouponDto betCoupon, SetUnset method){
        switch (method) {
            case SET: {
                CouponEventDto ev = CouponEventDto.from(betCoupon);
                if (!tracker.getEvents().contains(ev)) tracker.getEvents().add(ev);
                break;
            }
            case UNSET: {
                CouponEventDto ev = CouponEventDto.from(betCoupon);
                if (tracker.getEvents().contains(ev)) tracker.getEvents().remove(ev);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }

        return run(tracker);
    }

    public CouponTrackerDto eval(CouponTrackerDto tracker, Long eventIdFixed, SetUnset method){
        tracker.getFixedMapByEvtId().put(eventIdFixed, SetUnset.SET.equals(method));
        return run(tracker);
    }

    private CouponTrackerDto run(CouponTrackerDto tracker) {
        List<InputRow> inputRowListLib = createOddsRowsFromTracker(tracker);

        MySlip mySlip = MySlip.buildFromRange(inputRowListLib);
        mySlip.getCompleteDag().setBonusTable(BonusTable.getTestInstance());

        List<Double> stakes = spreadStakes(inputRowListLib, mySlip, defaultStake);
        mySlip.setStakes(stakes);

        OutputObj outputObj = mySlip.writeNple(inputRowListLib);
        fillTrackerFields(tracker, inputRowListLib, outputObj);

        return tracker;
    }


//    private CouponTrackerDto evalUnset(CouponTrackerDto tracker, BetCouponDto betCoupon) {
//        CouponEventDto ev = CouponEventDto.from(betCoupon);
//        if (tracker.getEvents().contains(ev)) tracker.getEvents().remove(ev);
//        return tracker;
//    }
//
//    private CouponTrackerDto evalSet(CouponTrackerDto tracker, BetCouponDto betCoupon) {
//        CouponEventDto ev = CouponEventDto.from(betCoupon);
//        if (!tracker.getEvents().contains(ev)) tracker.getEvents().add(ev);
//
//        List<InputRow> inputRowListLib = createOddsRowsFromTracker(tracker.getEvents());
//
//        MySlip mySlip = MySlip.buildFromRange(inputRowListLib);
//        mySlip.getCompleteDag().setBonusTable(BonusTable.getTestInstance());
//
//        List<Double> stakes = spreadStakes(inputRowListLib, mySlip, defaultStake);
//        mySlip.setStakes(stakes);
//
//        OutputObj outputObj = mySlip.writeNple(inputRowListLib);
//        fillTrackerFields(tracker, inputRowListLib, outputObj);
//
//        return tracker;
//    }

    private void fillTrackerFields(CouponTrackerDto tracker, List<InputRow> inputRowListLib, OutputObj outputObj) {
        tracker.setNumEvents(tracker.getEvents().size());
        tracker.setTotalSign(inputRowListLib.size());
        tracker.setSs(tracker.getNumEvents());
        tracker.setMultiplier(outputObj.getOutputRowList().get(0).getN());
        tracker.setSystem(outputObj.getOutputRowList().stream().map(this::couponSystemDtoFromOutputRow).collect(Collectors.toList()));

    }

    private List<Double> spreadStakes(List<InputRow> inputRowListLib, MySlip mySlip, double defaultStake) {
        //spread the stake
        List<Double> stakes = Lists.newArrayList();
        List<Integer> combPerRow = Lists.newArrayList();
        int nCombz = 0;
        for (int i = 0; i < mySlip.getNevent(); i++){
            combPerRow.add(mySlip.getKpla(mySlip.getNevent()-i, inputRowListLib) );
            if (active.get(i)) nCombz += combPerRow.get(i);
        }
        for (int i = 0; i < mySlip.getNevent(); i++){
            stakes.add(active.get(i) ? defaultStake / nCombz : 0);
        }
        return stakes;
    }

    private List<InputRow> createOddsRowsFromTracker(CouponTrackerDto tracker){
        List<InputRow> result = Lists.newArrayList();
        List<CouponEventDto> events = tracker.getEvents();

        Map<Long, List<CouponEventDto>> eventGroupedByEventID = events
                .stream()
                .collect(Collectors.groupingBy(CouponEventDto::getId))
                ;
        eventGroupedByEventID.forEach((id, couponEventDtos) -> result.add(
                new InputRow(id, tracker.getFixedMapByEvtId().getOrDefault(id, false),
                        couponEventDtos.stream().map(CouponEventDto::getOd).map(BigDecimal::doubleValue).collect(Collectors.toList()))
        ));

        return result;
    }

    private CouponSystemDto couponSystemDtoFromOutputRow(OutputRow or){
        return new CouponSystemDto(
                or.getType(),
                or.getN(),
                new BigDecimal(or.getMaxWin()),
                new BigDecimal(or.getMinWin()),
                new BigDecimal(or.getToPay())
        );
    }

}

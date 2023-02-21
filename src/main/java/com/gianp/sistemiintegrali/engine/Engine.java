package com.gianp.sistemiintegrali.engine;


import com.gianp.sistemiintegrali.model.*;
import com.gianp.sistemiintegrali.model.dto.BetCouponDto;
import com.gianp.sistemiintegrali.model.dto.CouponEventDto;
import com.gianp.sistemiintegrali.model.dto.CouponTrackerDto;
import com.gianp.sistemiintegrali.model.dto.SetUnset;
import com.gianp.sistemiintegrali.util.Utils;
import com.google.common.collect.Lists;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Engine {


    public static List<Boolean> active = Lists.newArrayList(true, true, false); //todo: prendere gli active
    private double defaultStake = 3.0;

    public OutputObj run(InputObj in) {
        MySlip mySlip = MySlip.buildFromRange(in.getOddsRows());
        mySlip.getCompleteDag().setBonusTable(BonusTable.getTestInstance());

        return mySlip.writeNple(in.getOddsRows());
    }

    public CouponTrackerDto eval(CouponTrackerDto tracker, BetCouponDto betCoupon, SetUnset method){
        switch (method) {
            case SET:   return evalSet(tracker, betCoupon);
            case UNSET: return evalUnset(tracker, betCoupon);
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }
    }

    private CouponTrackerDto evalUnset(CouponTrackerDto tracker, BetCouponDto betCoupon) {
        CouponEventDto ev = CouponEventDto.from(betCoupon);
        if (tracker.getEvents().contains(ev)) tracker.getEvents().remove(ev);
        return tracker;
    }

    private CouponTrackerDto evalSet(CouponTrackerDto tracker, BetCouponDto betCoupon) {
        CouponEventDto ev = CouponEventDto.from(betCoupon);
        if (!tracker.getEvents().contains(ev)) tracker.getEvents().add(ev);
        List<InputRow> inputRowListLib = createOddsRowsFromTracker(tracker.getEvents());
        MySlip mySlip = MySlip.buildFromRange(inputRowListLib);

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
        //end spread
        mySlip.setStakes(stakes);
        mySlip.getCompleteDag().setBonusTable(BonusTable.getTestInstance());
        OutputObj outputObj = mySlip.writeNple(inputRowListLib);

        //todo: from outputObj to Tracker

        return tracker;
    }

    public CouponTrackerDto eval(CouponTrackerDto tracker, Long eventIdFixed, SetUnset method){
        throw new NotImplementedException();
    }

    private List<InputRow> createOddsRowsFromTracker(List<CouponEventDto> events){
        List<InputRow> result = Lists.newArrayList();

        Map<Long, List<CouponEventDto>> eventGroupedByEventID = events
                .stream()
                .collect(Collectors.groupingBy(CouponEventDto::getId))
                ;
        eventGroupedByEventID.forEach((id, couponEventDtos) -> result.add(
                new InputRow(false,
                        couponEventDtos.stream().map(CouponEventDto::getOd).map(BigDecimal::doubleValue).collect(Collectors.toList()))
        ));

        return result;
    }

}

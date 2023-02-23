package com.gianp.sistemiintegrali.engine;


import com.gianp.sistemiintegrali.model.*;
import com.gianp.sistemiintegrali.model.dto.*;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Engine {

    //raccoglie solo informazioni, non fa ne eval, ne spread stake
    public CouponTrackerDto betCouponInteraction(CouponTrackerDto tracker, BetCouponDto betCoupon, SetUnset method){
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

        return tracker;
    }

    //raccoglie solo informazioni, non fa ne eval, ne spread stake
    public CouponTrackerDto fixedInteraction(CouponTrackerDto tracker, Long eventIdFixed, SetUnset method){
        tracker.getFixedMapByEvtId().put(eventIdFixed, SetUnset.SET.equals(method));
        return tracker;
    }

    //automaticamente fa lo spread stake
    public CouponTrackerDto setDefaultStake(CouponTrackerDto tracker, BigDecimal defaultStake){
        tracker.setDefaultStake(defaultStake);
        return spreadStake(tracker);
    }

    //automaticamente fa lo spread stake
    public CouponTrackerDto setActiveList(CouponTrackerDto tracker, List<Boolean> activeList){
        tracker.setActive(activeList);
        return spreadStake(tracker);
    }

    //solo spread stake
    public CouponTrackerDto spreadStake(CouponTrackerDto tracker){
        List<InputRow> inputRowListLib = createOddsRowsFromTracker(tracker);

        MySlip mySlip = MySlip.buildFromRange(inputRowListLib);
        mySlip.getCompleteDag().setBonusTable(BonusTable.getTestInstance());

        List<Double> stakes = spreadStakesPrivate(inputRowListLib, mySlip, tracker.getDefaultStake(), tracker.getActive());
        //mySlip.setStakes(stakes);

        tracker.setStakes(stakes.stream().map(BigDecimal::valueOf).collect(Collectors.toList()));
        return tracker;
    }

    //per cambio stake manuale da parte dell'utente
    public CouponTrackerDto setStakeFromUser(CouponTrackerDto tracker, List<BigDecimal> stakes){
        tracker.setStakes(stakes);
        return tracker;
    }

    //non fa spread stake, solo eval
    public CouponTrackerDto eval(CouponTrackerDto tracker) {
        List<InputRow> inputRowListLib = createOddsRowsFromTracker(tracker);

        MySlip mySlip = MySlip.buildFromRange(inputRowListLib);
        mySlip.getCompleteDag().setBonusTable(BonusTable.getTestInstance());

        mySlip.setStakes(tracker.getStakes().stream().map(BigDecimal::doubleValue).collect(Collectors.toList()));

        OutputObj outputObj = mySlip.writeNple(inputRowListLib);
        fillTrackerFields(tracker, inputRowListLib, outputObj);

        return tracker;
    }


    private void fillTrackerFields(CouponTrackerDto tracker, List<InputRow> inputRowListLib, OutputObj outputObj) {
        tracker.setNumEvents(tracker.getEvents().size());
        tracker.setTotalSign(inputRowListLib.size());
        tracker.setSs(tracker.getNumEvents());
        tracker.setMultiplier(outputObj.getOutputRowList().get(0).getN());
        tracker.setSystem(outputObj.getOutputRowList().stream().map(this::couponSystemDtoFromOutputRow).collect(Collectors.toList()));
    }

    private List<Double> spreadStakesPrivate(List<InputRow> inputRowListLib, MySlip mySlip, BigDecimal defaultStake, List<Boolean> active) {
        //spread the stake
        List<Double> stakes = Lists.newArrayList();
        List<Integer> combPerRow = Lists.newArrayList();
        int nCombz = 0;
        for (int i = 0; i < mySlip.getNevent(); i++){
            combPerRow.add(mySlip.getKpla(mySlip.getNevent()-i, inputRowListLib) );
            if (getActive(active, i)) nCombz += combPerRow.get(i);
        }
        for (int i = 0; i < mySlip.getNevent(); i++){
            stakes.add(getActive(active, i) ? defaultStake.doubleValue() / nCombz : 0);
        }
        return stakes;
    }
    
    private boolean getActive(List<Boolean> activeList, int index){
        try {
            return activeList.get(index);
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    private List<InputRow> createOddsRowsFromTracker(CouponTrackerDto tracker){
        List<InputRow> result = Lists.newArrayList();
        List<CouponEventDto> events = tracker.getEvents();

        Map<Long, List<CouponEventDto>> eventGroupedByEventID = events
                .stream()
                .collect(Collectors.groupingBy(CouponEventDto::getId))
                ;
        eventGroupedByEventID.forEach((id, couponEventDtos) -> {
            result.add(
                    new InputRow(id,
                            tracker.getFixedMapByEvtId().getOrDefault(id, false),
                            couponEventDtos.stream().map(CouponEventDto::getOd).map(BigDecimal::doubleValue).collect(Collectors.toList()))
                );

            //fill comb number in couponEventDto
            couponEventDtos.forEach(c -> c.setComb(couponEventDtos.size()));
            }
        );

        return result;
    }

    private CouponSystemDto couponSystemDtoFromOutputRow(OutputRow or){
        return new CouponSystemDto(
                or.getType(),
                or.getN(),
                BigDecimal.valueOf(or.getMaxWin()),
                BigDecimal.valueOf(or.getMinWin()),
                BigDecimal.valueOf(or.getToPay())
        );
    }

}

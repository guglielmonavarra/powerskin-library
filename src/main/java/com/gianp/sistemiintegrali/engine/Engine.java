package com.gianp.sistemiintegrali.engine;


import com.gianp.sistemiintegrali.model.*;
import com.gianp.sistemiintegrali.model.dto.BetCouponDto;
import com.gianp.sistemiintegrali.model.dto.CouponTrackerDto;
import com.gianp.sistemiintegrali.model.dto.SetUnset;
import com.gianp.sistemiintegrali.util.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class Engine {
    public OutputObj run(InputObj in) {
        MySlip mySlip = MySlip.buildFromRange(in.getOddsRows());
        mySlip.getCompleteDag().setBonusTable(BonusTable.getTestInstance());
        mySlip.setStakes(Utils.fillStakes(in.getStakes(), in.getDefaultStake()));
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

    }

    private CouponTrackerDto evalSet(CouponTrackerDto tracker, BetCouponDto betCoupon) {

    }

    public CouponTrackerDto eval(CouponTrackerDto tracker, Long eventIdFixed, SetUnset method){
        throw new NotImplementedException();
    }

}

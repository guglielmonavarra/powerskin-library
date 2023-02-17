package com.gianp.sistemiintegrali.engine;


import com.gianp.sistemiintegrali.model.*;
import com.gianp.sistemiintegrali.util.Utils;


public class Engine {
    public OutputObj run(InputObj in) {
        MySlip mySlip = MySlip.buildFromRange(in.getOddsRows());
        mySlip.getCompleteDag().setBonusTable(BonusTable.getTestInstance());
        mySlip.setStakes(Utils.fillStakes(in.getStakes(), in.getDefaultStake()));
        return mySlip.writeNple(in.getOddsRows());
    }

}

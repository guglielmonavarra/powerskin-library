/**
 * This file is part of PowerSkin.
 * PowerSkin is not a free software. UNAUTHORIZED copying 
 * of software is illegal. Copyright law protects software authors
 * and publishers, just as patent law protects inventors.
 * You can purchase your copy contacting the author  (Navarra Guglielmo)
 * at <navarra.guglielmo@gmail.com>.
 * 
 */

package com.gianp.sistemiintegrali.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CouponEventDto {

    
    //@ApiModelProperty(value = "Match Id.")
    private Long id; // evtId

    //@ApiModelProperty(value = "Qbet number.")
    private Long alias;  // alias
    
    //@ApiModelProperty(value = "Event name.")
    private String event;    // evtName

    //@ApiModelProperty(value = "Type of event.")
    private ScheduleType type; //type
    
    //@ApiModelProperty(value = "Combination number.")
    private Integer comb; //numero delle selection su singola riga

    //@ApiModelProperty(value = "Sport/Category.")
    private String more; //spName / ctName

    //@ApiModelProperty(value = "Market Id.")
    private Long mid; //markId
    
    //@ApiModelProperty(value = "Market Name.")
    private String mn; //markName

    //@ApiModelProperty(value = "Spread.")
    private String spr; //spread

    //@ApiModelProperty(value = "Event date.")
    private LocalDateTime date; //evnDate

    //@ApiModelProperty(value = "Odds value.")
    private BigDecimal od; //oddsValue

    //@ApiModelProperty(value = "Odds Id.")
    private Long oddsId;

    public static CouponEventDto from(BetCouponDto bc){
        return new CouponEventDto(
               bc.getEvtId(),
               bc.getAlias(),
               bc.getEvtName(),
               bc.getType(),
               null,
               bc.getSpName() + " / " + bc.getCtName(),
               bc.getMarkId(),
               bc.getMarkName(),
               bc.getSpread(),
               bc.getEvnDate(),
                bc.getOddsValue(),
                bc.getOddsId()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouponEventDto that = (CouponEventDto) o;
        return id.equals(that.id) && oddsId.equals(that.oddsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oddsId);
    }
}

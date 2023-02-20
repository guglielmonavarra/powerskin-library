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
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponTrackerDto {

    //@ApiModelProperty(value = "Number of events.")
    private Integer numEvents; //events.size()
    
    //@ApiModelProperty(value = "List of events.")
    private List<CouponEventDto> events;
    
    //@ApiModelProperty(value = "Coupon multiplier.")
    private Integer multiplier; //
    
    //@ApiModelProperty(value = "Coupon bonus percentage.")
    private BigDecimal bonusPerc;// da calcolo
    
    //@ApiModelProperty(value = "Coupon multiple bonus.")
    private BigDecimal multipleBonus; //da calcolo
    
    //@ApiModelProperty(value = "Coupon win pot.")
    private BigDecimal multipleWinPot; //da calcolo
    
    //@ApiModelProperty(value = "Coupon total Sign.")
    private Integer totalSign;    //numero di eventId (row)
    
    //@ApiModelProperty(value = "Selection numbers.")
    private Integer ss;  //  come numEvents
    
    //@ApiModelProperty(value = "List of system column.")
    private List<CouponSystemDto> system;


    /*
    *
    * */
    
}

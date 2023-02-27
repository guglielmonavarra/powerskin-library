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
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CouponTrackerDto {

    //@ApiModelProperty(value = "Number of events.")
    private Integer numEvents; //events.size()

    //@ApiModelProperty(value = "List of events.")
    private List<CouponEventDto> events = Lists.newArrayList();
    
    //@ApiModelProperty(value = "Coupon multiplier.")
    private Integer multiplier; // 1 se non integrale. se integrale: valore della prima "n" da lib
    
    //@ApiModelProperty(value = "Coupon min bonus")
    private BigDecimal minBonus;// da calcolo

    //@ApiModelProperty(value = "Coupon max bonus")
    private BigDecimal maxBonus;// da calcolo
    
    //@ApiModelProperty(value = "Coupon min win.")
    private BigDecimal minWin; //da calcolo

    //@ApiModelProperty(value = "Coupon max win.")
    private BigDecimal maxWin; //da calcolo
    
    //@ApiModelProperty(value = "Coupon min odds.")
    private BigDecimal minOdds; //da calcolo

    //@ApiModelProperty(value = "Coupon max odds.")
    private BigDecimal maxOdds; //da calcolo
    
    //@ApiModelProperty(value = "Coupon total Sign.")
    private Integer totalSign;    //numero di eventId (row)
    
    //@ApiModelProperty(value = "Selection numbers.")
    private Integer ss;  //  come numEvents
    
    //@ApiModelProperty(value = "List of system column.")
    private List<CouponSystemDto> system;

    private Map<Long, Boolean> fixedMapByEvtId = Maps.newHashMap();

    public  List<Boolean> active = Lists.newArrayList(true);
    private BigDecimal defaultStake = BigDecimal.valueOf(3.0);

    private List<BigDecimal> stakes = Lists.newArrayList();


    
}

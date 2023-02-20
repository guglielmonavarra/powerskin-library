/**
 * This file is part of PowerSkin.
 * PowerSkin is not a free software. UNAUTHORIZED copying 
 * of software is illegal. Copyright law protects software authors
 * and publishers, just as patent law protects inventors.
 * You can purchase your copy contacting the author  (Navarra Guglielmo)
 * at <navarra.guglielmo@gmail.com>.
 * 
 */

package com.mondo.gaming.dto;


import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@ApiModel(description = "Coupon tracker model.")
public class CouponTrackerDto {

    @ApiModelProperty(value = "Number of events.")
    private Integer numEvents;
    
    @ApiModelProperty(value = "List of events.")
    private List<CouponEventDto> events;
    
    @ApiModelProperty(value = "Coupon multiplier.")
    private Integer multiplier;
    
    @ApiModelProperty(value = "Coupon bonus percentage.")
    private BigDecimal bonusPerc;
    
    @ApiModelProperty(value = "Coupon multiple bonus.")
    private BigDecimal multipleBonus;
    
    @ApiModelProperty(value = "Coupon win pot.")
    private BigDecimal multipleWinPot;
    
    @ApiModelProperty(value = "Coupon total Sign.")
    private Integer totalSign;    
    
    @ApiModelProperty(value = "Selection numbers.")
    private Integer ss; 
    
    @ApiModelProperty(value = "List of system column.")
    private List<CouponSystemDto> system;
    
    
}

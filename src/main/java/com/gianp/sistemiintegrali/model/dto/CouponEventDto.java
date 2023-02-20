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
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mondo.gaming.models.ScheduleType;

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
@ApiModel(description = "Coupon event model.")
public class CouponEventDto {

    
    @ApiModelProperty(value = "Match Id.")
    private Long id;

    @ApiModelProperty(value = "Qbet number.")
    private Long alias;
    
    @ApiModelProperty(value = "Event name.")
    private String event;    

    @ApiModelProperty(value = "Type of event.")
    private ScheduleType type;
    
    @ApiModelProperty(value = "Combination number.")
    private Integer comb;

    @ApiModelProperty(value = "Sport/Category.")
    private String more;

    @ApiModelProperty(value = "Market Id.")
    private Long mid;
    
    @ApiModelProperty(value = "Market Name.")
    private String mn;

    @ApiModelProperty(value = "Spread.")
    private String spr;

    @ApiModelProperty(value = "Event date.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;  

    @ApiModelProperty(value = "Odds value.")
    private BigDecimal od;
    
    
   
    
}

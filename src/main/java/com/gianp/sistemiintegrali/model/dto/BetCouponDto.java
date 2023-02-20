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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mondo.gaming.models.comb.BetCoupon;
import com.mondo.gaming.utils.ModelBuilder;
import com.mondo.gaming.utils.Validatable;

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
@ApiModel(description = "Bet coupon model.")
public class BetCouponDto implements Validatable, ModelBuilder<BetCoupon, BetCouponDto> {

    @ApiModelProperty(value = "Coupon ticket Id.")
    private Integer ticketId;

    @ApiModelProperty(value = "Coupon session Id.")
    private String sessionId;    
    
    @ApiModelProperty(value = "If bet it's for live side.")
    private Boolean isLive;    
   
    @ApiModelProperty(value = "Selection name.")
    private String selName;  
    
    @ApiModelProperty(value = "Selection Id.")
    private Long selId;      
    
    @ApiModelProperty(value = "Odds Id.")
    private Long oddsId;   
    
    @ApiModelProperty(value = "Odds Value.")
    private BigDecimal oddsValue;

    @ApiModelProperty(value = "SBV specifier.")
    private String spread;    
    
    @ApiModelProperty(value = "Market Id.")
    private Long markId;
    
    @ApiModelProperty(value = "Market Type Id.")
    private String markTypId;    
    
    @ApiModelProperty(value = "Market name.")
    private String markName;     
    
    @ApiModelProperty(value = "Match Id.")
    private Long evtId;   

    @ApiModelProperty(value = "Match ADM Id.")
    private String admId;    
    
    @ApiModelProperty(value = "Event name.")
    private String evtName;

    @ApiModelProperty(value = "Event date.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime evnDate;    
    
    @ApiModelProperty(value = "Event date timestamp.")
    private Long evnDateTs;
    
    @ApiModelProperty(value = "Sport Id.")
    private Long spId;      
    
    @ApiModelProperty(value = "Sport name.")
    private String spName;     
    
    @ApiModelProperty(value = "Category Id.")
    private Long ctId;      
    
    @ApiModelProperty(value = "Category name.")
    private String ctName;

    @ApiModelProperty(value = "Tournament Id.")
    private Long trId;      

    @ApiModelProperty(value = "Tournament name.")
    private String trName;  
    
    @ApiModelProperty(value = "If bet it's for virtual.")
    private Boolean vrt;
    
    @ApiModelProperty(value = "Score is Live event .")
    private String currentScore;
    
    @ApiModelProperty(value = "Live current time.")
    private String liveCurrentTime;  
    
    
    @Override
    public Optional<Map<String, Object[]>> applyValidations() {     
        Map<String, Object[]> validations = new HashMap<>();
        
        return Optional.of(validations).filter(map -> !map.isEmpty()); 
    }
    
    

    @Override
    public BetCoupon toModel() {

        return null;
    }  
    


}



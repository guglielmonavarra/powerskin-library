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
@ApiModel(description = "Coupon system model.")
public class CouponSystemDto {

    @ApiModelProperty(value = "Kind of column.")
    private Integer kind;

    @ApiModelProperty(value = "Column index.")
    private Integer col;

    @ApiModelProperty(value = "Odds min value.")
    private BigDecimal oddMin;

    @ApiModelProperty(value = "Odds max value.")
    private BigDecimal oddMax;

    @ApiModelProperty(value = "Bonus min.")
    private BigDecimal bonusMin;

    @ApiModelProperty(value = "Bonus Max.")
    private BigDecimal bonusMax;

    @ApiModelProperty(value = "Odds Max column.")
    private BigDecimal oddMaxCol;
    
}

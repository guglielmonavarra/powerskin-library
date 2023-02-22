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


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CouponSystemDto {

    private String type; // campo type da lib

    //@ApiModelProperty(value = "Column index.")
    private Integer col; // campo n da lib

    //@ApiModelProperty(value = "Max Win.")
    private BigDecimal maxWin;

    //@ApiModelProperty(value = "Min Win.")
    private BigDecimal minWin;


    //@ApiModelProperty(value = "To Pay.")
    private BigDecimal toPay;


    
}

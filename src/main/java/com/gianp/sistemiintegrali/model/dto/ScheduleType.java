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


import java.util.Arrays;


public enum ScheduleType {

    LIVE(202, "LIVE","L"),
    PREMATCH(203, "PREMATCH","P"),
    MIXED(253, "MIXED","M");
    
    private int code;
    private String description;
    private String prefix;
    
    
    ScheduleType(int code, String description, String prefix) {
        this.code = code;
        this.description = description;
        this.prefix = prefix;
    }
   
    
    public int getCode() {
        return code;
    }
   
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getName() {
        return prefix;
    }
   
    public void setName(String prefix) {
        this.prefix = prefix;
    }
   
     
    public String getDescription() {
        return description;
    }
   
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public static ScheduleType from(String value) {
        return Arrays.stream(values()).filter(v -> (v.getName().equalsIgnoreCase(value))).findFirst().orElse(PREMATCH);
    }
    

    public static ScheduleType getByCode(String code) {
        for(ScheduleType d : ScheduleType.values()) {
            if(d.getCode() == Integer.valueOf(code)) {
                return d;
            }
        }
        return null;
    }     
    
    
   
}

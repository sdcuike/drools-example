package com.beaver.drools.common.model;

import lombok.AllArgsConstructor;

/**
 * Created by beaver on 2017/3/27.
 */
@AllArgsConstructor
public enum RuleResult {
    
    REJECT(1, "拒绝"),
    
    WARNING(2, "警告"),
    
    CALCULATE(3, "用于计算某个值"),
    
    OK(4, "OK");
    
    private int code;
    private String description;
    
    public int getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return "RuleResult{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}

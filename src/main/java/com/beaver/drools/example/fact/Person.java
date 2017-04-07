package com.beaver.drools.example.fact;

import com.beaver.drools.common.model.RuleResultDetail;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by beaver on 2017/3/24.
 */

@Getter
@Setter
@EqualsAndHashCode
public class Person {
    private String name;
    private int age;
    private List<String> address;
    private Map<String, String> exGirlfriend;
    private String message;
    /**
     * 规则结果列表
     */
    RuleResultDetail ruleResultDetail = new RuleResultDetail();
    
    private Map<String,Boolean> keyToFlags = new HashMap<>();
    
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", exGirlfriend=" + exGirlfriend +
                ", message='" + message + '\'' +
                ", ruleResultDetail=" + ruleResultDetail +
                '}';
    }
}

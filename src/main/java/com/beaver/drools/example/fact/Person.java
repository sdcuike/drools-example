package com.beaver.drools.example.fact;

import com.beaver.drools.common.model.RuleResultDetail;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
    private List<String> address = new ArrayList<>();
    private Map<String, String> exGirlfriend = new HashMap<>();
    private String message;
    /**
     * 规则结果列表
     */
    RuleResultDetail ruleResultDetail = new RuleResultDetail();
    
    private Map<String, Boolean> keyToFlags = new HashMap<>();
    
    public void putExGirlfriend(String key, String value) {
        exGirlfriend.put(key, value);
    }
    
    public void addAddress(String address) {
        this.address.add(address);
    }
    
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

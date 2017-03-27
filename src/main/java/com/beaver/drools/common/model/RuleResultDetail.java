package com.beaver.drools.common.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by beaver on 2017/3/27.
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class RuleResultDetail {
    private Map<RuleResult, List<String>> ruleResultDetailInfo = new HashMap<>();
    
    public void add(RuleResult ruleResult, String info) {
        List<String> infos = ruleResultDetailInfo.get(ruleResult);
        if (infos == null) {
            infos = new ArrayList<>();
            ruleResultDetailInfo.put(ruleResult, infos);
        }
        infos.add(info);
    }
    
    @Override
    public String toString() {
        return "RuleResultDetail{" +
                "ruleResultDetailInfo=" + ruleResultDetailInfo +
                '}';
    }
}

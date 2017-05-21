package com.beaver.drools.exampl.travel.rule;

import com.beaver.drools.common.model.RuleResult;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by beaver on 2017/5/20.
 */
@Data
public class MatchInvoiceTravelRuleRequestDto {
    //规则ID
    private String ruleOID;
    
    //出差申请ID
    private String invoiceOID;
    
    //出差信息-人员级别、出差地点、出差费用等等
    private Map<String, String> data = new HashMap<>();
    
    //规则执行结果
    private RuleResult result = RuleResult.OK;
    
    //备注-规则明细
    private String remarkMessage;
    
}

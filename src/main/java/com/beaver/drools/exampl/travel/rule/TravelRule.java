package com.beaver.drools.exampl.travel.rule;

import org.javafunk.excelparser.annotations.ExcelField;
import org.javafunk.excelparser.annotations.ExcelObject;
import org.javafunk.excelparser.annotations.ParseType;

/**
 * Created by beaver on 2017/5/20.
 */
@ExcelObject(parseType = ParseType.ROW, start = 2, end = 61)
public class TravelRule {
    
    //公司名称
    @ExcelField(position = 1)
    private String companyName;
    
    //费用类型
    @ExcelField(position = 2)
    private String expenseType;
    
    //规则应用场景
    @ExcelField(position = 3)
    private String applyPoint;
    
    //规则结果
    @ExcelField(position = 4)
    private String ruleAction;
    
    //规则内容
    @ExcelField(position = 7)
    private String rule;
    
    //提示信息
    @ExcelField(position = 9)
    private String alertMessage;
    
    //规则ID
    @ExcelField(position = 11)
    private String ruleId;
    
    //解析出的drools规则内容
    private String droolsRule;
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getExpenseType() {
        return expenseType;
    }
    
    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }
    
    public String getRule() {
        return rule;
    }
    
    public void setRule(String rule) {
        this.rule = rule;
    }
    
    public String getApplyPoint() {
        return applyPoint;
    }
    
    public void setApplyPoint(String applyPoint) {
        this.applyPoint = applyPoint;
    }
    
    public String getRuleAction() {
        return ruleAction;
    }
    
    public void setRuleAction(String ruleAction) {
        this.ruleAction = ruleAction;
    }
    
    public String getAlertMessage() {
        return alertMessage;
    }
    
    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }
    
    public String getRuleId() {
        return ruleId;
    }
    
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }
    
    public String getDroolsRule() {
        return droolsRule;
    }
    
    public void setDroolsRule(String droolsRule) {
        this.droolsRule = droolsRule;
    }
    
    @Override
    public String toString() {
        return "TravelRule{" +
                "companyName='" + companyName + '\'' +
                ", expenseType='" + expenseType + '\'' +
                ", applyPoint='" + applyPoint + '\'' +
                ", ruleAction='" + ruleAction + '\'' +
                ", rule='" + rule + '\'' +
                ", alertMessage='" + alertMessage + '\'' +
                ", ruleId='" + ruleId + '\'' +
                ", droolsRule='" + droolsRule + '\'' +
                '}';
    }
}

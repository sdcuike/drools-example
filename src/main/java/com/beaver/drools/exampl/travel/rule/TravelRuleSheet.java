package com.beaver.drools.exampl.travel.rule;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;

import java.util.List;

/**
 * Created by beaver on 2017/5/23.
 */
@XlsSheet(name = "工作表1")
public class TravelRuleSheet {
    @XlsHorizontalRecords(headerRow = 0, headerColumn = 0)
    List<TravelRuleInfo> travelRules;
    
    public List<TravelRuleInfo> getTravelRules() {
        return travelRules;
    }
    
    public void setTravelRules(List<TravelRuleInfo> travelRules) {
        this.travelRules = travelRules;
    }
    
    @Override
    public String toString() {
        return "UserSheet{" +
                "travelRules=" + travelRules +
                '}';
    }
    
    public static class TravelRuleInfo implements TravelRuleInterface {
        
        //公司名称
        @XlsColumn(columnName = "来源")
        private String companyName;
        
        //费用类型
        @XlsColumn(columnName = "费用类型")
        private String expenseType;
        
        //规则应用场景
        @XlsColumn(columnName = "APPLY_POINT")
        private String applyPoint;
        
        //规则结果
        @XlsColumn(columnName = "ACTION")
        private String ruleAction;
        
        //规则内容
        @XlsColumn(columnName = "RULE")
        private String rule;
        
        //提示信息
        @XlsColumn(columnName = "Warning Message")
        private String alertMessage;
        
        //规则ID
        @XlsColumn(columnName = "id")
        private String ruleId;
        
        private String droolsRule;
        
        @Override
        public String getCompanyName() {
            return companyName;
        }
        
        @Override
        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
        
        @Override
        public String getExpenseType() {
            return expenseType;
        }
        
        @Override
        public void setExpenseType(String expenseType) {
            this.expenseType = expenseType;
        }
        
        @Override
        public String getApplyPoint() {
            return applyPoint;
        }
        
        @Override
        public void setApplyPoint(String applyPoint) {
            this.applyPoint = applyPoint;
        }
        
        @Override
        public String getRuleAction() {
            return ruleAction;
        }
        
        @Override
        public void setRuleAction(String ruleAction) {
            this.ruleAction = ruleAction;
        }
        
        @Override
        public String getRule() {
            return rule;
        }
        
        @Override
        public void setRule(String rule) {
            this.rule = rule;
        }
        
        @Override
        public String getAlertMessage() {
            return alertMessage;
        }
        
        @Override
        public void setAlertMessage(String alertMessage) {
            this.alertMessage = alertMessage;
        }
        
        @Override
        public String getRuleId() {
            return ruleId;
        }
        
        @Override
        public void setRuleId(String ruleId) {
            this.ruleId = ruleId;
        }
        
        @Override
        public String getDroolsRule() {
            return droolsRule;
        }
        
        @Override
        public void setDroolsRule(String droolsRule) {
            this.droolsRule = droolsRule;
        }
        
        @Override
        public String toString() {
            return "TravelRuleInfo{" +
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
}

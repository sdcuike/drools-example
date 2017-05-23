package com.beaver.drools.exampl.travel.rule;

import com.gh.mygreen.xlsmapper.XlsMapper;
import com.gh.mygreen.xlsmapper.XlsMapperException;
import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by beaver on 2017/5/22.
 */
public class XlsmapperExcel {
    
    static String fileName = "tengxun-travle-rule.xlsx";
    
    public static void main(String[] args) throws IOException, XlsMapperException {
        String filePath = FilloExcel.class.getClassLoader().getResource(fileName).getFile();
        
        XlsMapper xlsMapper = new XlsMapper();
        UserSheet sheet = xlsMapper.load(new FileInputStream(filePath), UserSheet.class);
        
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        System.out.println(gson.toJson(sheet));
    }
    
    @XlsSheet(name = "s1")
    public static class UserSheet {
        @XlsHorizontalRecords(headerRow = 0,headerColumn = 0)
        List<TravelRule> travelRules;
        
        public List<TravelRule> getTravelRules() {
            return travelRules;
        }
        
        public void setTravelRules(List<TravelRule> travelRules) {
            this.travelRules = travelRules;
        }
    
        @Override
        public String toString() {
            return "UserSheet{" +
                    "travelRules=" + travelRules +
                    '}';
        }
    }
    
    public static class TravelRule {
        
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
}

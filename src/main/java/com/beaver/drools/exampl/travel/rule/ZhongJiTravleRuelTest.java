package com.beaver.drools.exampl.travel.rule;

import com.beaver.drools.exampl.travel.rule.TravelRuleSheet.TravelRuleInfo;
import com.gh.mygreen.xlsmapper.XlsMapper;
import com.gh.mygreen.xlsmapper.XlsMapperException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by beaver on 2017/5/23.
 */
public class ZhongJiTravleRuelTest {
    
    
    static String packageName = "com.beaver.drools.exampl.travel.rule";
    static List<String> importPackages = Arrays.asList(
            "java.math.*"
            , "com.beaver.drools.common.model.*");
    
    
    static String sheetName = "工作表1";
    static String fileName = "zhongji-rule20170523.xlsx";
    
    static String objectType = "MatchInvoiceTravelRuleRequestDto";
    static String objectVar = "$invoice";
    
    static String resultPropertyName = "result";
    static String alertMessagePropertyName = "remarkMessage";
    
    //规则内容属性别名，如{EXP_LEVEL}IN[一般员工]，EXP_LEVEL 可以起别名
    static Map<String, String> rulePropertyNameAlias = new HashMap<>();
    
    static {
        rulePropertyNameAlias.put("EXP_LEVEL", "EXPENSE_LEVEL");
        rulePropertyNameAlias.put("ER_LOCATION", "city");
        rulePropertyNameAlias.put("ER_LOCATION_COUNTRY", "country");
        rulePropertyNameAlias.put("ER_HOTEL_FEE", "INVOICE_AMOUNT");
        rulePropertyNameAlias.put("ER_DAYS", "days");
        rulePropertyNameAlias.put("ER_EXP_FEE", "INVOICE_AMOUNT");
        rulePropertyNameAlias.put("ER_FIGHT_LEVEL", "FLIGHT_LEVEL");
        rulePropertyNameAlias.put("ER_TRAIN_LEVEL", "TRAIN_LEVEL");
        rulePropertyNameAlias.put("ER_SHIP_LEVEL", "SHIP_LEVEL");
        rulePropertyNameAlias.put("ER_REASON", "EXCEED_REASON");
        rulePropertyNameAlias.put("ER_COACH_LEVEL", "COACH_LEVEL");
        
    }
    
    public static void main(String[] args) throws IOException, XlsMapperException {
        String filePath = ZhongJiTravleRuelTest.class.getClassLoader().getResource(fileName).getFile();
        
        XlsMapper xlsMapper = new XlsMapper();
        TravelRuleSheet sheet = xlsMapper.load(new FileInputStream(filePath), TravelRuleSheet.class);
        
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        List<TravelRuleInfo> entityList = sheet.getTravelRules();
        //
        entityList = entityList.stream()
                               .filter(t -> t.getCompanyName() != null && !t.getCompanyName().isEmpty())
                               .collect(Collectors.toList());
        
        System.out.println(gson.toJson(entityList));
        
        
        Set<String> set = entityList.stream().map(TravelRuleInterface::getRuleId).collect(Collectors.toSet());
        if (set.size() != entityList.size()) {
            throw new IllegalArgumentException("id 有重复");
        }
        
        
        entityList.stream()
                  .map(ZhongJiTravleRuelTest::travelRuleGenerate)
                  .forEach(t -> {
                      System.out.println(t);
                  });
    }
    
    
    public static TravelRuleInterface travelRuleGenerate(TravelRuleInterface travelRule) {
        TravelRuleGenerate travelRuleGenerate = new TravelRuleGenerate();
        travelRuleGenerate.setPackageName(packageName);
        travelRuleGenerate.setImportPackages(importPackages);
        travelRuleGenerate.setRuleName(travelRule.getRule());
        travelRuleGenerate.setObjectType(objectType);
        travelRuleGenerate.setObjectVar(objectVar);
        travelRuleGenerate.setTravelRule(travelRule);
        travelRuleGenerate.setResultPropertyName(resultPropertyName);
        travelRuleGenerate.setAlertMessagePropertyName(alertMessagePropertyName);
        travelRuleGenerate.setRulePropertyNameAlias(rulePropertyNameAlias);
        travelRuleGenerate.setRulePropertyNameAliasIsRegularExpression(false);
        String generate = travelRuleGenerate.Generate();
        travelRule.setDroolsRule(generate);
        return travelRule;
    }
}

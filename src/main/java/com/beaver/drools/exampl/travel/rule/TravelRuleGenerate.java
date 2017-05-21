package com.beaver.drools.exampl.travel.rule;

import com.beaver.drools.common.model.RuleResult;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by beaver on 2017/5/20.<br>
 * 简单规则组成,并不不包含drools复杂的语法规则
 */
public class TravelRuleGenerate {
    //规则包名
    private String packageName;
    //规则需要外部类的包
    private List<String> importPackages = new ArrayList<>();
    //规则名字
    private String ruleName;
    
    //规则匹配的类型，如 $o: OrderItem( $p : price, $code : custCode )中的OrderItem
    private String objectType;
    
    //规则匹配类型的变量名，如 $o: OrderItem( $p : price, $code : custCode )中的OrderItem中$o
    private String objectVar;
    
    //规则信息
    private TravelRule travelRule;
    
    //规则执行动作结果赋值属性名，如 $invoice.setResult(RuleResult.REJECT); 中的resultr;
    private String resultPropertyName;
    //规则执行动作结果的提示信息赋值属性名，如   $invoice.setRemarkMessage("您的房费高于公司标准 ");中的remarkMessage.
    private String alertMessagePropertyName;
    
    //规则匹配属性别名，如data["EXPENSE_LEVEL"] not in ("总裁"),
    // 中的EXPENSE_LEVEL在travelRule中droolsRule中的名不一样，类似mybatis映射一下
    //不过key为正则表达式
    private Map<String, String> rulePropertyNameAlias = new HashMap<>();
    
    public String getPackageName() {
        return packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public List<String> getImportPackages() {
        return importPackages;
    }
    
    public void setImportPackages(List<String> importPackages) {
        this.importPackages = importPackages;
    }
    
    public String getRuleName() {
        return ruleName;
    }
    
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
    
    public TravelRule getTravelRule() {
        return travelRule;
    }
    
    public void setTravelRule(TravelRule travelRule) {
        this.travelRule = travelRule;
    }
    
    public String getObjectType() {
        return objectType;
    }
    
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    
    public String getObjectVar() {
        return objectVar;
    }
    
    public void setObjectVar(String objectVar) {
        this.objectVar = objectVar;
    }
    
    public String getResultPropertyName() {
        return resultPropertyName;
    }
    
    public void setResultPropertyName(String resultPropertyName) {
        this.resultPropertyName = resultPropertyName;
    }
    
    public String getAlertMessagePropertyName() {
        return alertMessagePropertyName;
    }
    
    public void setAlertMessagePropertyName(String alertMessagePropertyName) {
        this.alertMessagePropertyName = alertMessagePropertyName;
    }
    
    public Map<String, String> getRulePropertyNameAlias() {
        return rulePropertyNameAlias;
    }
    
    public void setRulePropertyNameAlias(Map<String, String> rulePropertyNameAlias) {
        this.rulePropertyNameAlias = rulePropertyNameAlias;
    }
    
    public String Generate() {
        StringBuilder sb = new StringBuilder();
        
        //规则头部
        sb.append(packageName).append("\n").append("\n");
        
        importPackages.stream().forEach(t -> sb.append(t).append("\n"));
        
        sb.append("\n\n");
        
        //规则名称
        sb.append("rule  \" ").append(travelRule.getCompanyName())
          .append("  ")
          .append(travelRule.getExpenseType())
          .append("  ")
          .append(ruleName).append(" \"").append("\n\n");
        
        //规则条件
        sb.append("when").append("\n");
        sb.append("  ").append(objectVar).append(" : ").append(objectType).append("(").append("\n");
        //规则条件内容
        List<String> rules = generateWhenContent();
        rules.stream().forEach(t -> {
            sb.append("  ").append(t).append(",\n");
        });
        
        int lastIndexOf = sb.lastIndexOf(",");
        if (lastIndexOf > 0) {
            sb.deleteCharAt(lastIndexOf);
        }
        
        sb.append(" ) \n");
        
        
        //规则执行动作
        sb.append("then \n");
        
        sb.append("  ").append(objectVar).append(".set").append(StringUtils.capitalize(resultPropertyName)).append("(");
        RuleResult ruleResult = RuleResult.valueOf(travelRule.getRuleAction());
        sb.append(RuleResult.class.getSimpleName()).append(".").append(ruleResult.name()).append("); \n");
        
        sb.append("  ").append(objectVar).append(".set").append(StringUtils.capitalize(alertMessagePropertyName)).append("(\"");
        sb.append(travelRule.getAlertMessage()).append(" \");");
        
        sb.append("\n").append("end");
        return sb.toString();
    }
    
    //{EXP_LEVEL}IN[一般员工] AND {ER_LOCATION}NOT IN[北京/上海/深圳] AND {ER_EXP_FEE}>{ER_DAYS}*300
    private List<String> generateWhenContent() {
        String rule = travelRule.getRule();
        List<String> ruelList = Splitter.on(Pattern.compile("AND")).trimResults().splitToList(rule);
        
        List<String> rules = ruelList.stream().map(this::generateARule).collect(Collectors.toList());
        return rules;
    }
    
    
    private String generateARule(final String rule) {
        if (StringUtils.deleteWhitespace(rule).contains("NOTIN")) {
            Pair<String, String> pair = getRulePropertyNameAliasAndValuesForIn(rule, "NOT");
            return pair.getKey() + " not in " + pair.getValue();
        }
        
        if (rule.contains("IN")) {
            Pair<String, String> pair = getRulePropertyNameAliasAndValuesForIn(rule, "IN");
            return pair.getKey() + " in " + pair.getValue();
        }
        
        if (rule.contains(">")) {
            return getRulePropertyNameAliasAndValuesForCompare(rule, ">");
        }
        
        throw new UnsupportedOperationException();
    }
    
    //{EXP_LEVEL}IN[一般员工] , {ER_LOCATION}NOT IN[北京/上海/深圳]  to
    //data["EXPENSE_LEVEL"] in ("一般员工"),	data["city"] not in ("上海","北京","深圳","广州"),
    private Pair<String, String> getRulePropertyNameAliasAndValuesForIn(final String rule, final String separators) {
        String name = StringUtils.splitByWholeSeparator(rule, separators)[0];
        name = getRulePropertyNameAlias(name).getValue();
        
        name = "data[\"" + name + "\"]";
        
        String value = StringUtils.substring(rule
                , StringUtils.lastIndexOf(rule, "[") + 1
                , StringUtils.lastIndexOf(rule, "]"));
        
        List<String> values = Splitter.on("/").trimResults().splitToList(value);
        value = values.stream().map(t -> "\"" + t + "\"").collect(Collectors.joining(","));
        value = "(" + value + ")";
        return Pair.of(name, value);
    }
    
    //{ER_EXP_FEE}>{ER_DAYS}*300     to
    // BigDecimal.valueOf(Double.valueOf(data.["INVOICE_AMOUNT"])) > 300 * Double.valueOf(data["days"])
    private String getRulePropertyNameAliasAndValuesForCompare(final String rule, final String separator) {
        if (!Arrays.asList(">", "<", "=").contains(separator.trim())) {
            throw new UnsupportedOperationException();
        }
        
        List<String> list = Splitter.on(separator.trim()).trimResults().splitToList(rule);
        String express1 = list.get(0);
        String express2 = list.get(1);
        String template1 = "BigDecimal.valueOf(Double.valueOf(data[\"%s\"]))";
        String tempalte2 = "Double.valueOf(data[\"%s\"])";
        
        Pair<String, String> rulePropertyNameAlias1 = getRulePropertyNameAlias(express1);
        express1 = String.format(template1, rulePropertyNameAlias1.getValue());
        
        Pair<String, String> rulePropertyNameAlias2 = getRulePropertyNameAlias(express2);
        
        express2 = String.format(tempalte2, rulePropertyNameAlias2.getValue());
        
        String replace = rule.replace(rulePropertyNameAlias1.getKey(), express1);
        replace = replace.replace(rulePropertyNameAlias2.getKey(), express2);
        return replace;
    }
    
    private Pair<String, String> getRulePropertyNameAlias(final String content) {
        for (Entry<String, String> entry : rulePropertyNameAlias.entrySet()) {
            Pattern compile = Pattern.compile(entry.getKey());
            Matcher matcher = compile.matcher(content);
            if (matcher.find()) {
                String name = content.substring(matcher.regionStart(), matcher.regionEnd());
                String aliasName = entry.getValue();
                return Pair.of(name, aliasName);
            }
        }
        
        throw new UnsupportedOperationException(content);
    }
}

package com.beaver.drools.exampl.travel.rule;

import com.beaver.drools.common.model.RuleResult;
import com.beaver.drools.util.KieSessionUtil;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.kie.api.io.ResourceType;

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
    private TravelRuleInterface travelRule;
    
    //规则执行动作结果赋值属性名，如 $invoice.setResult(RuleResult.REJECT); 中的resultr;
    private String resultPropertyName;
    //规则执行动作结果的提示信息赋值属性名，如   $invoice.setRemarkMessage("您的房费高于公司标准 ");中的remarkMessage.
    private String alertMessagePropertyName;
    
    //规则匹配属性别名，如data["EXPENSE_LEVEL"] not in ("总裁"),
    // 中的EXPENSE_LEVEL在travelRule中droolsRule中的名不一样，类似mybatis映射一下
    //不过key为正则表达式
    private Map<String, String> rulePropertyNameAlias = new HashMap<>();
    
    private boolean rulePropertyNameAliasIsRegularExpression = true;
    
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
    
    public TravelRuleInterface getTravelRule() {
        return travelRule;
    }
    
    public void setTravelRule(TravelRuleInterface travelRule) {
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
    
    public boolean isRulePropertyNameAliasIsRegularExpression() {
        return rulePropertyNameAliasIsRegularExpression;
    }
    
    public void setRulePropertyNameAliasIsRegularExpression(boolean rulePropertyNameAliasIsRegularExpression) {
        this.rulePropertyNameAliasIsRegularExpression = rulePropertyNameAliasIsRegularExpression;
    }
    
    //规则生成
    public String Generate() {
        StringBuilder sb = new StringBuilder();
        
        //规则头部
        sb.append("package ").append(packageName).append("\n").append("\n");
        
        importPackages.stream().forEach(t -> sb.append("import ").append(t).append("\n"));
        
        sb.append("\n\n");
        
        //规则名称
        sb.append("rule  \"").append(generateRuleName()).append("\n\n");
        
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
        
        //规则执行结果
        sb.append("  ").append(objectVar).append(".set").append(StringUtils.capitalize(resultPropertyName)).append("(");
        RuleResult ruleResult = RuleResult.valueOf(travelRule.getRuleAction());
        sb.append(RuleResult.class.getSimpleName()).append(".").append(ruleResult.name()).append("); \n");
        
        //规则执行提示信息
        sb.append("  ").append(objectVar).append(".set").append(StringUtils.capitalize(alertMessagePropertyName)).append("(\"");
        sb.append(travelRule.getAlertMessage()).append(" \");");
        
        sb.append("\n").append("end");
        
        String generate = sb.toString();
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addContent(generate, ResourceType.DRL);
        try {
            kieSessionUtil.verifyRules();
        } catch (Exception e) {
            throw new RuntimeException(generate, e);
        }
        travelRule.setDroolsRule(generate);
        return travelRule.getDroolsRule();
    }
    
    //{EXP_LEVEL}IN[一般员工] AND {ER_LOCATION}NOT IN[北京/上海/深圳] AND {ER_EXP_FEE}>{ER_DAYS}*300
    private List<String> generateWhenContent() {
        String rule = travelRule.getRule().trim();
        if (rule.contains("OR") || rule.contains("or")) {
            throw new UnsupportedOperationException("请知悉drools 的基本使用：复杂的规则拆成简单的规则执行，or的规则请拆分");
        }
        
        if (rule.contains("and")) {
            throw new UnsupportedOperationException("AND条件单词大写");
        }
        
        
        List<String> ruelList = Splitter.on(Pattern.compile("AND")).trimResults().splitToList(rule);
        
        List<String> rules = ruelList.stream().map(this::generateARule).collect(Collectors.toList());
        return rules;
    }
    
    //去除AND条件的一条简单规则
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
        if (rule.contains("=")) {
            return getRulePropertyNameAliasAndValuesForCompare(rule, "=");
        }
        
        throw new UnsupportedOperationException(rule);
    }
    
    //{EXP_LEVEL}IN[一般员工] , {ER_LOCATION}NOT IN[北京/上海/深圳]  to
    //data["EXPENSE_LEVEL"] in ("一般员工"),	data["city"] not in ("上海","北京","深圳","广州"),
    private Pair<String, String> getRulePropertyNameAliasAndValuesForIn(final String rule, final String separators) {
        String[] splitByWholeSeparators = StringUtils.splitByWholeSeparator(rule, separators);
        
        String name = splitByWholeSeparators[0];
        if (splitByWholeSeparators.length > 2) {
            name = name + "IN" + splitByWholeSeparators[1];
        }
        
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
        
        Pair<String, String> rulePropertyNameAlias1 = getRulePropertyNameAlias(StringUtils.removeAll(express1, "\\d|\\*|\\+|-"));
        express1 = String.format(template1, rulePropertyNameAlias1.getValue());
        
        Pair<String, String> rulePropertyNameAlias2 = getRulePropertyNameAlias(StringUtils.removeAll(express2, "\\d|\\*|\\+|-"));
        
        String replace = rule.replace(rulePropertyNameAlias1.getKey(), express1);
        
        if (rulePropertyNameAlias2 == null) {
            return "data[\"" + rulePropertyNameAlias1.getValue() + "\"] == null";
        }
        express2 = String.format(tempalte2, rulePropertyNameAlias2.getValue());
        
        replace = replace.replace(rulePropertyNameAlias2.getKey(), express2);
        return replace;
    }
    
    private Pair<String, String> getRulePropertyNameAlias(final String content) {
        if (content.equals("")) {
            return null;
        }
        if (rulePropertyNameAliasIsRegularExpression) {
            for (Entry<String, String> entry : rulePropertyNameAlias.entrySet()) {
                Pattern compile = Pattern.compile(entry.getKey());
                Matcher matcher = compile.matcher(content);
                if (matcher.find()) {
                    String name = content.substring(matcher.regionStart(), matcher.regionEnd());
                    String aliasName = entry.getValue();
                    return Pair.of(name, aliasName);
                }
            }
        }
        
        for (Entry<String, String> entry : rulePropertyNameAlias.entrySet()) {
            String name = entry.getKey();
            if (name.equals(content.trim())) {
                return Pair.of(name, entry.getValue().trim());
                
            }
            if (name.equals(content.trim()) || ("{" + name + "}").equals(content.trim())) {
                return Pair.of("{" + name + "}", entry.getValue().trim());
                
            }
        }
        
        //值为空，null
        String empty = StringUtils.deleteWhitespace(content);
        if (empty.equals("[]")) {
            return null;
        }
        throw new UnsupportedOperationException(content);
    }
    
    private String generateRuleName() {
        StringBuilder sb = new StringBuilder();
        sb.append(travelRule.getCompanyName())
          .append("-")
          .append(travelRule.getExpenseType())
          .append("-")
          .append(ruleName).append("\"");
        
        return sb.toString();
    }
    
}

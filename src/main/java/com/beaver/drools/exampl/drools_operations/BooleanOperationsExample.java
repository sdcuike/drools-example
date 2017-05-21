package com.beaver.drools.exampl.drools_operations;

import com.beaver.drools.util.KieSessionUtil;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * <p>
 * Boolean operations are the ones that use AND, OR, XOR, and so on.  </p>
 * Using boolean expressions is possible, but if required, they will usually mark places  </p>
 * where the design of our rules needs revision. This is because the AND expressions  </p>
 * are intrinsic to the language and rules that use OR expressions should be considered  </p>
 * as more than one condition in the same rule (and therefore, non-atomic). Whenever  </p>
 * an OR expression is required to express a rule, consider splitting the rule into two  </p>
 * different rules.
 * </p>
 * <p>
 * It might seem that boolean expressions are something to avoid as much as possible,  </p>
 * however, we will see some other uses that they have when combining other  </p>
 * operations later in this chapter. For the moment, let's just remember that they exist  </p>
 * and try to use them only if really needed.  </p>
 * </p>
 * <p>
 * 规则中的条件关系：&&用简化的，表示，||拆分规则来表示
 * <p>
 * Created by beaver on 2017/4/7.
 */
public class BooleanOperationsExample {
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/drools_operations/BooleanOperationsExample.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        
    }
}

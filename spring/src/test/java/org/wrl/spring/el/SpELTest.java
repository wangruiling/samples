package org.wrl.spring.el;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wrl on 2015/11/9.
 */
public class SpELTest {
    /**
     * SpEL 在求表达式值时一般分为四步，其中第三步可选
     */
    @Test
    public void helloWorld() {
        //1. 创建解析器： SpEL 使用 ExpressionParser 接口表示解析器， 提供 SpelExpressionParser 默认实现
        ExpressionParser parser = new SpelExpressionParser();
        //2. 解析表达式：使用 ExpressionParser 的 parseExpression 来解析相应的表达式为 Expression 对象
        Expression expression = parser.parseExpression("('Hello' + ' World').concat(#end)");
        //3. 构造上下文：准备比如变量定义等等表达式需要的上下文数据
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("end", "!");
        //4. 求值：通过 Expression 接口的 getValue 方法根据上下文获得表达式值
        assertThat(expression.getValue(context)).isEqualTo("Hello World!");
    }
}

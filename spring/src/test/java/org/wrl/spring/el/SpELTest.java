package org.wrl.spring.el;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
        //2. 解析表达式：使用 ExpressionParser 的 parseExpression 来解析相应的表达式为 Expression 对象.(提供 getValue 方法用于获取表达式值，提供 setValue 方法用于设置对象值)
        Expression expression = parser.parseExpression("('Hello' + ' World').concat(#end)");
        //3. 构造上下文：准备比如变量定义等等表达式需要的上下文数据(使用setRootObject方法来设置根对象，使用setVariable方法来注册自定义变量，使用registerFunction 来注册自定义函数等等)
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("end", "!");
        //4. 求值：通过 Expression 接口的 getValue 方法根据上下文获得表达式值
        assertThat(expression.getValue(context)).isEqualTo("Hello World!");
    }

    @Test
    public void testParserContext() {
        ExpressionParser parser = new SpelExpressionParser();
        //ParserContext 接口用于定义字符串表达式是不是模板，及模板开始与结束字符
        ParserContext parserContext = new ParserContext() {
            @Override
            public boolean isTemplate() {
                return true;
            }
            @Override
            public String getExpressionPrefix() {
                return "#{";
            }
            @Override
            public String getExpressionSuffix() {
                return "}";
            }
        };
        String template = "#{'Hello '}#{'World!'}";
        //使用 parseExpression 解析时传入的模板必须以“#{”开头，以“}”结尾，如"#{'Hello '}#{'World!'}"
        //默认传入的字符串表达式不是模板形式，如之前演示的 Hello World
        Expression expression = parser.parseExpression(template, parserContext);
        assertThat(expression.getValue()).isEqualTo("Hello World!");
    }

    /**
     * 字面量表达式： SpEL 支持的字面量包括：字符串、数字类型（int、long、 float、 double） 、布尔类型、null 类型
     */
    @Test
    public void testBasicExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        //1.字符串
        String str1 = parser.parseExpression("'Hello World!'").getValue(String.class);
        String str2 = parser.parseExpression("\"Hello World!\"").getValue(String.class);
        assertThat(str2).isEqualTo(str1);

        int int1 = parser.parseExpression("1").getValue(Integer.class);//int类型
        assertThat(int1).isEqualTo(1);

        long long1 = parser.parseExpression("-1L").getValue(long.class);//long类型
        assertThat(long1).isEqualTo(-1L);

        float float1 = parser.parseExpression("1.1").getValue(Float.class);//float类型
        assertThat(float1).isEqualTo(1.1f);

        double double1 = parser.parseExpression("1.1E+2").getValue(double.class);//double类型
        assertThat(double1).isEqualTo(1.1E+2);

        int hex1 = parser.parseExpression("0xa").getValue(Integer.class);//16进制int类型
        assertThat(hex1).isEqualTo(0xa);

        long hex2 = parser.parseExpression("0xaL").getValue(long.class);//16进制long类型
        assertThat(hex2).isEqualTo(0xaL);

        boolean true1 = parser.parseExpression("true").getValue(boolean.class);//布尔类型
        assertThat(true1).isTrue();

        boolean false1 = parser.parseExpression("false").getValue(boolean.class);//布尔类型
        assertThat(false1).isEqualTo(false);

        Object null1 = parser.parseExpression("null").getValue(Object.class);//null类型
        assertThat(null1).isEqualTo(null);
    }

    /**
     * 算数运算表达式： SpEL 支持加(+)、减(-)、乘(*)、除(/)、求余（%）、幂（^）运算
     */
    @Test
    public void testArithmeticExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        int result1 = parser.parseExpression("1+2-3*4/2").getValue(Integer.class);
        assertThat(result1).isEqualTo(-3);

        int result2 = parser.parseExpression("4%3").getValue(Integer.class);
        assertThat(result2).isEqualTo(1);


        int result3 = parser.parseExpression("2^3").getValue(Integer.class);
        assertThat(result3).isEqualTo(8);

        int result4 = parser.parseExpression("6 DIV 2").getValue(Integer.class);
        assertThat(result4).isEqualTo(3);

        int result5 = parser.parseExpression("4 MOD 3").getValue(Integer.class);
        assertThat(result5).isEqualTo(1);
    }

    /**
     * 关系表达式：等于（==）、不等于(!=)、大于(>)、大于等于(>=)、小于(<)、小于等于(<=)，区间（between）运算，如“parser.parseExpression("1>2").getValue(boolean.class);”将返回false；而“parser.parseExpression("1 between {1, 2}").getValue(boolean.class);”将返回 true
     */
    @Test
    public void testRelationalExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        boolean result1 = parser.parseExpression("1 gt 2").getValue(boolean.class);
        assertThat(result1).isEqualTo(false);

        boolean result2 = parser.parseExpression("1 between {1, 2}").getValue(boolean.class);
        assertThat(result2).isEqualTo(true);
    }

    /**
     * 逻辑表达式：且（and）、或(or)、非(!或 NOT)
     * 注：逻辑运算符不支持 Java 中的 && 和 ||
     */
    @Test
    public void testLogicalExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String expression1 = "2>1 and (!true or !false)";
        boolean result1 = parser.parseExpression(expression1).getValue(boolean.class);
        assertThat(result1).isEqualTo(true);

        String expression2 = "2>1 and (NOT true or NOT false)";
        boolean result2 = parser.parseExpression(expression2).getValue(boolean.class);
        assertThat(result2).isEqualTo(true);
    }

    /**
     * 字符串连接及截取表达式：使用“+”进行字符串连接，使用“'String'[0] [index]”来截取一个字符， 目前只支持截取一个， 如“'Hello ' + 'World!'” 得到“Hello World!” ； 而“'HelloWorld!'[0]”将返回“H”。
     */
    @Test
    public void testStringConcatExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String expression1 = "'Hello ' + 'World!'";
        String result1 = parser.parseExpression(expression1).getValue(String.class);
        assertThat(result1).isEqualTo("Hello World!");

        String expression2 = "'Hello World!'[0]";
        String result2 = parser.parseExpression(expression2).getValue(String.class);
        assertThat(result2).isEqualTo("H");

    }

    /**
     * 三目运算及 Elivis 运算表达式：
     */
    @Test
    public void testTernaryExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String expression1 = "2>1?true:false";
        boolean result1 = parser.parseExpression(expression1).getValue(boolean.class);
        assertThat(result1).isEqualTo(true);

        String expression2 = "null?:false";
        boolean result2 = parser.parseExpression(expression2).getValue(boolean.class);
        assertThat(result2).isEqualTo(false);

        String expression3 = "true?:false";
        boolean result3 = parser.parseExpression(expression3).getValue(boolean.class);
        assertThat(result3).isEqualTo(true);

    }

    /**
     * 正则表达式：使用“str matches regex，如“'123' matches '\\d{3}'”将返回 true；
     */
    @Test
    public void testRegexExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String expression1 = "'123' matches '\\d{3}'";
        boolean result1 = parser.parseExpression(expression1).getValue(boolean.class);
        assertThat(result1).isEqualTo(true);
    }


    /***************************** 类相关表达式 start ******************************/

    /**
     * 类类型表达式：使用“T(Type)”来表示 java.lang.Class 实例，“Type”必须是类全限定名，“java.lang”包除外，即该包下的类可以不指定包名；使用类类型表达式还可以进行访问类静态方法及类静态字段
     */
    @Test
    public void testClassTypeExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        //java.lang包类访问
        Class<String> result1 = parser.parseExpression("T(String)").getValue(Class.class);
        assertThat(result1).isEqualTo(String.class);

        //其他包类访问
        String expression2 = "T(org.wrl.spring.el.SpELTest)";
        Class<String> result2 = parser.parseExpression(expression2).getValue(Class.class);
        assertThat(result2).isEqualTo(SpELTest.class);

        //类静态字段访问
        int result3 = parser.parseExpression("T(Integer).MAX_VALUE").getValue(int.class);
        assertThat(result3).isEqualTo(Integer.MAX_VALUE);

        //类静态方法调用
        int result4 = parser.parseExpression("T(Integer).parseInt('1')").getValue(int.class);
        assertThat(result4).isEqualTo(1);
    }

    /**
     * 类实例化：类实例化同样使用 java 关键字“new”，类名必须是全限定名，但 java.lang包内的类型除外，如 String、Integer。
     */
    @Test
    public void testConstructorExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String result1 = parser.parseExpression("new String('haha')").getValue(String.class);
        assertThat(result1).isEqualTo("haha");

        Date result2 = parser.parseExpression("new java.util.Date()").getValue(Date.class);
        assertThat(result2).isNotNull();
    }

    /**
     * instanceof 表达式： SpEL 支持 instanceof 运算符， 跟 Java 内使用同义； 如 “'haha' instanceof T(String)”将返回 true
     */
    @Test
    public void testInstanceofExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        boolean result1 = parser.parseExpression("'haha' instanceof T(String)").getValue(boolean.class);
        assertThat(result1).isEqualTo(true);
    }

    /**
     * 变量定义及引用： 变量定义通过 EvaluationContext接口的 setVariable(variableName, value)方法定义；在表达式中使用“#variableName”引用；
     * 除了引用自定义变量，SpE 还允许引用根对象及当前上下文对象，使用“#root”引用根对象，使用“#this”引用当前上下文对象
     */
    @Test
    public void testVariableExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("variable", "haha");
        String result1 = parser.parseExpression("#variable").getValue(context, String.class);
        assertThat(result1).isEqualTo("haha");

        context = new StandardEvaluationContext("haha");
        String result2 = parser.parseExpression("#root").getValue(context, String.class);
        assertThat(result2).isEqualTo("haha");

        String result3 = parser.parseExpression("#this").getValue(context, String.class);
        assertThat(result3).isEqualTo("haha");
    }


    /**
     * 自定义函数：目前只支持类静态方法注册为自定义函数；SpEL使用StandardEvaluationContext 的 registerFunction 方法进行注册自定义函数，其实完全可以使用setVariable 代替，两者其实本质是一样的
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    @Test
    public void testFunctionExpression() throws SecurityException, NoSuchMethodException {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        Method parseInt = Integer.class.getDeclaredMethod("parseInt", String.class);
        //此处可以看出“registerFunction”和“setVariable”都可以注册自定义函数，但是两个方法的含义不一样，推荐使用“registerFunction”方法注册自定义函数
        context.registerFunction("parseInt", parseInt);
        context.setVariable("parseInt2", parseInt);
        String expression1 = "#parseInt('3') == #parseInt2('3')";
        boolean result1 = parser.parseExpression(expression1).getValue(context, boolean.class);
        assertThat(result1).isEqualTo(true);

    }

    /**
     * 赋值表达式：SpEL 即允许给自定义变量赋值，也允许给跟对象赋值，直接使用“#variableName=value”即可赋值
     */
    @Test
    public void testAssignExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        //1.给root对象赋值
        EvaluationContext context = new StandardEvaluationContext("aaaa");
        String result1 = parser.parseExpression("#root='aaaaa'").getValue(context, String.class);
        assertThat(result1).isEqualTo("aaaaa");
        String result2 = parser.parseExpression("#this='aaaa'").getValue(context, String.class);
        assertThat(result2).isEqualTo("aaaa");

        //2.给自定义变量赋值
        context.setVariable("#variable", "variable");
        String result3 = parser.parseExpression("#variable=#root").getValue(context, String.class);
        assertThat(result3).isEqualTo("aaaa");
    }

    /**
     * 对象属性存取及安全导航表达式
     */
    @Test
    public void testPropertyExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        //1.访问root对象属性
        Date date = new Date();
        StandardEvaluationContext context = new StandardEvaluationContext(date);
        int result1 = parser.parseExpression("Year").getValue(context, int.class);
        assertThat(result1).isEqualTo(date.getYear());
        int result2 = parser.parseExpression("year").getValue(context, int.class);
        assertThat(result2).isEqualTo(date.getYear());

        //2.安全访问
        context.setRootObject(null);
        Object result3 = parser.parseExpression("#root?.year").getValue(context, Object.class);
        assertThat(result3).isEqualTo(null);

        //3.给root对象属性赋值
        context.setRootObject(date);
        int result4 = parser.parseExpression("Year = 4").getValue(context, int.class);
        assertThat(result4).isEqualTo(4);
        parser.parseExpression("Year").setValue(context, 5);
        int result5 = parser.parseExpression("Year").getValue(context, int.class);
        assertThat(result5).isEqualTo(5);
    }


    /**
     * 对象方法调用：对象方法调用更简单，跟 Java 语法一样；如“'haha'.substring(2,4)”将返回“ha”；而对于根对象可以直接调用方法
     */
    @Test
    public void testMethodExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String result1 = parser.parseExpression("'haha'.substring(2,4)").getValue(String.class);
        assertThat(result1).isEqualTo("ha");
        Date date = new Date();
        StandardEvaluationContext context = new StandardEvaluationContext(date);
        int result2 = parser.parseExpression("getYear()").getValue(context, int.class);
        assertThat(result2).isEqualTo(date.getYear());
    }

    /**
     * Bean 引用： SpEL 支持使用 “@” 符号来引用 Bean， 在引用 Bean 时需要使用 BeanResolver接口实现来查找 Bean，Spring 提供 BeanFactoryResolver 实现
     */
    @Test
    public void testBeanExpression() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        ctx.refresh();
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(ctx));
        Properties result1 = parser.parseExpression("@systemProperties").getValue(context, Properties.class);
        assertThat(result1).isEqualTo(System.getProperties());
    }
    /***************************** 类相关表达式 end ******************************/


    /***************************** 集合相关表达式 start ******************************/
    /**
     * 内联 List：从 Spring3.0.4 开始支持内联 List，使用{表达式，……}定义内联 List，如“{1,2,3}”将返回一个整型的 ArrayList，而“{}”将返回空的 List，对于字面量表达式列表，SpEL 会使用 java.util.Collections.unmodifiableList 方法将列表设置为不可修改
     */
    @Test
    public void testInnerListExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        List<Integer> result1 = parser.parseExpression("{1,2,3}").getValue(List.class);
        assertThat( result1.get(0)).isEqualTo(new Integer(1));
        try {
            result1.set(0, 2);
            //不可能执行到这，对于字面量列表不可修改
            fail("不可能执行到这");
        } catch (Exception e) {
        }
        List<Integer> result2 = parser.parseExpression("{}").getValue(List.class);
        assertThat(result2.size()).isEqualTo(0);

        String expression3 = "{{1+2,2+4},{3,4+4}}";
        List<List<Integer>> result3 = parser.parseExpression(expression3).getValue(List.class);
        result3.get(0).set(0, 1);
        assertThat(result3.size()).isEqualTo(2);
    }

    /**
     * 内联数组：和 Java 数组定义类似，只是在定义时进行多维数组初始化。
     */
    @Test
    public void testInnerArrayExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        int[] result1 = parser.parseExpression("new int[1]").getValue(int[].class);
        assertThat(result1.length).isEqualTo(1);
        int[] result2 = parser.parseExpression("new int[2]{1,2}").getValue(int[].class);
        assertThat(result2[1]).isEqualTo(2);

        String expression3 = "new int[1][2][3]";
        int[][][] result3 = parser.parseExpression(expression3).getValue(int[][][].class);
        assertThat(result3[0][0].length).isEqualTo(3);

        String expression4 = "new int[1][2][3]{{1}{2}{3}}";
        try {
            int[][][] result4 = parser.parseExpression(expression4).getValue(int[][][].class);
            fail("内联数组");
        } catch (Exception e) {
        }
    }

    /**
     * 集合，字典元素访问：SpEL 目前支持所有集合类型和字典类型的元素访问，使用“集合[索引]”访问集合元素，使用“map[key]”访问字典元素
     * 注：集合元素访问是通过 Iterator 遍历来定位元素位置的。
     */
    @Test
    public void testListAndMapGetValueExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        int result1 = parser.parseExpression("{1,2,3}[0]").getValue(int.class);
        //即list.get(0)
        assertThat(result1).isEqualTo(1);

        Collection<Integer> collection = new HashSet<>();
        collection.add(1);
        collection.add(2);
        EvaluationContext context2 = new StandardEvaluationContext();
        context2.setVariable("collection", collection);
        int result2 = parser.parseExpression("#collection[1]").getValue(context2, int.class);
        //对于任何集合类型通过Iterator来定位元素
        assertThat(result2).isEqualTo(2);

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        EvaluationContext context3 = new StandardEvaluationContext();
        context3.setVariable("map", map);
        int result3 = parser.parseExpression("#map['a']").getValue(context3, int.class);
        assertThat(result3).isEqualTo(1);
    }

    /**
     * 列表，字典，数组元素修改：可以使用赋值表达式或 Expression 接口的 setValue 方法修改
     */
    @Test
    public void testListAndMapSetValueExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();

        //1.修改数组元素值
        int[] array = new int[] {1, 2};
        EvaluationContext context1 = new StandardEvaluationContext();
        context1.setVariable("array", array);
        int result1 = parser.parseExpression("#array[1] = 3").getValue(context1, int.class);
        assertThat(result1).isEqualTo(3);

        //2.修改集合值
        Collection<Integer> collection = new ArrayList<Integer>();
        collection.add(1);
        collection.add(2);
        EvaluationContext context2 = new StandardEvaluationContext();
        context2.setVariable("collection", collection);
        int result2 = parser.parseExpression("#collection[1] = 3").getValue(context2, int.class);
        assertThat(result2).isEqualTo(3);
        parser.parseExpression("#collection[1]").setValue(context2, 4);
        result2 = parser.parseExpression("#collection[1]").getValue(context2, int.class);
        assertThat(result2).isEqualTo(4);

        //3.修改map元素值
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        EvaluationContext context3 = new StandardEvaluationContext();
        context3.setVariable("map", map);
        int result3 = parser.parseExpression("#map['a'] = 2").getValue(context3, int.class);
        assertThat(result3).isEqualTo(2);

    }

    /**
     * 集合投影：在 SQL 中投影指从表中选择出列，而在 SpEL 指根据集合中的元素中通过选择来构造另一个集合，该集合和原集合具有相同数量的元素； SpEL 使用“（list|map） .![投影表达式]”来进行投影运算
     */
    @Test
    public void testProjectionExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();

        //1.首先准备测试数据
        Collection<Integer> collection = new ArrayList<Integer>();
        collection.add(4);
        collection.add(5);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);

        //2.测试集合
        EvaluationContext context1 = new StandardEvaluationContext();
        context1.setVariable("collection", collection);
        Collection<Integer> result1 = parser.parseExpression("#collection.![#this+1]").getValue(context1, Collection.class);
        assertThat(result1.size()).isEqualTo(2);
        assertThat(result1.iterator().next()).isEqualTo(5);



        //3.测试字典
        EvaluationContext context2 = new StandardEvaluationContext();
        context2.setVariable("map", map);
        List<Integer> result2 = parser.parseExpression("#map.![value+1]").getValue(context2, List.class);
        assertThat(result2.size()).isEqualTo(2);
    }

    /**
     * 集合选择：在 SQL 中指使用 select 进行选择行数据，而在 SpEL 指根据原集合通过条件表达式选择出满足条件的元素并构造为新的集合，SpEL 使用“(list|map).?[选择表达式]” ，
     * 其中选择表达式结果必须是 boolean 类型，如果 true 则选择的元素将添加到新集合中，false将不添加到新集合中
     */
    @Test
    public void testSelectExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();

        //1.准备数据
        Collection<Integer> collection = new ArrayList<>();
        collection.add(4);
        collection.add(5);
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);

        //2.集合或数组测试
        EvaluationContext context1 = new StandardEvaluationContext();
        context1.setVariable("collection", collection);
        Collection<Integer> result1 = parser.parseExpression("#collection.?[#this>4]").getValue(context1, Collection.class);
        assertThat(result1.size()).isEqualTo(1);
        assertThat(result1.iterator().next()).isEqualTo(5);

        //3.字典测试
        EvaluationContext context2 = new StandardEvaluationContext();
        context2.setVariable("map", map);
        Map<String, Integer> result2 = parser.parseExpression("#map.?[#this.key != 'a']").getValue(context2, Map.class);
        assertThat(result2.size()).isEqualTo(1);

        List<Integer> result3 = parser.parseExpression("#map.?[key != 'a'].![value+1]").getValue(context2, List.class);
        assertThat(result3.iterator().next()).isEqualTo(3);
    }
    /***************************** 集合相关表达式 end ******************************/



    /***************************** 表达式模板 start ******************************/
    @Test
    public void testTemplateExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        ParserContext parserContext = new ParserContext() {
            @Override
            public boolean isTemplate() {
                return true;
            }
            @Override
            public String getExpressionPrefix() {
                return "${";
            }
            @Override
            public String getExpressionSuffix() {
                return "}";
            }
        };
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("v0", 1);
        context.setVariable("v1", 2);
        String result = parser.parseExpression("Error ${#v0} ${#v1}", parserContext).getValue(context, String.class);
        assertThat(result).isEqualTo("Error 1 2");
    }
    /***************************** 表达式模板 end ******************************/


    /***************************** 在 Bean 定义中使用 EL start ******************************/
    /**
     * xml 风格的配置
     */
    @Test
    public void testXmlExpression() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        String hello1 = ctx.getBean("hello1", String.class);
        String hello2 = ctx.getBean("hello2", String.class);
        String hello3 = ctx.getBean("hello3", String.class);
        assertThat(hello1).isEqualTo("Hello World!");
        assertThat(hello2).isEqualTo("Hello World!");
        assertThat(hello3).isEqualTo("Hello World!");
    }

    /**
     * 注解风格的配置
     * 基于注解风格的 SpEL 配置也非常简单，使用@Value 注解来指定 SpEL 表达式，该注解可以放到字段、方法及方法参数上。
     */
    @Test
    public void testAnnotationExpression() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        //其中“helloBean1 ”值是 SpEL 表达式的值，而“helloBean2”是通过 setter 注入的值，这说明 setter 注入将覆盖@Value 的值
        SpELBean helloBean1 = ctx.getBean("helloBean1", SpELBean.class);
        assertThat(helloBean1.getValue()).isEqualTo("Hello World!");

        SpELBean helloBean2 = ctx.getBean("helloBean2", SpELBean.class);
        assertThat(helloBean2.getValue()).isEqualTo("haha");
    }
    /***************************** 在 Bean 定义中使用 EL end ******************************/
//    @Test
//    public void testPrefixExpression() {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter5/el3.xml");
//        SpELBean helloBean1 = ctx.getBean("helloBean1", SpELBean.class);
//        assertThat("#{'Hello' + world}", helloBean1.getValue());
//
//        SpELBean helloBean2 = ctx.getBean("helloBean2", SpELBean.class);
//        assertThat("Hello World!", helloBean2.getValue());
//    }
}

package com.bluejean.modules.java8;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by wangrl on 2016/10/7.
 */
public class Lambda {

    public static void main(String[] args) {
        Lambda lambda = new Lambda();
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add(" ");
        list.add("");
        list.add("222");
//        利用filter的函数式接口过滤字符串集合中的空字符串
        List<String> result = lambda.filter(list, (String str) -> null != str && !str.isEmpty());

        System.out.println(list);
        System.out.println(result);

//        利用下面的函数式接口，遍历字符串集合，并打印非空字符串
        lambda.filter(list, (String str) -> {
            if (StringUtils.isNotBlank(str)) {
                System.out.println(str);
            }
        });

        //下面利用下面的函数式接口，将一个封装字符串（整型数字的字符串表示）的接口，转换成整型集合：
        List<Integer> result2 = lambda.filter(list, (String str) -> Integer.parseInt(str));
        System.out.println(result2);
    }

    /**
     * 按照指定的条件对集合元素进行过滤
     * @param list
     * @param predicate Predicate的功能，利用我们在外部设定的条件对于传入的参数进行校验，并返回验证结果boolean，下面利用Predicate对List集合的元素进行过滤
     * @param <T>
     * @return
     */
    public <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> newList = new ArrayList<>();
        for (final T t : list) {
            if (predicate.test(t)) {
                newList.add(t);
            }
        }
        return newList;
    }


    /**
     * 遍历集合，执行自定义行为
     * @param list
     * @param consumer Consumer提供了一个accept抽象函数，该函数接收参数，但不返回值，下面利用Consumer遍历集合
     * @param <T>
     */
    public <T> void filter(List<T> list, Consumer<T> consumer) {
        for (final T t : list) {
            consumer.accept(t);
        }
    }

    /**
     * 遍历集合，执行自定义转换操作
     * @param list
     * @param function Funcation执行转换操作，输入是类型T的数据，返回R类型的数据，下面利用Function对集合进行转换
     * @param <T>
     * @param <R>
     * @return
     */
    public <T, R> List<R> filter(List<T> list, Function<T, R> function) {
        List<R> newList = new ArrayList<R>();
        for (final T t : list) {
            newList.add(function.apply(t));
        }
        return newList;
    }
}

package org.wrl.spring.spring3;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wrl on 2015/11/4.
 */
public class HelloImpl implements HelloApi {
    private String name;
    private List<String> values;
    private String[] array;

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public static void main(String[] args) {
        List<Integer> n = Arrays.asList(1, 3, 2, 7, 6, 5, 9);
        n.stream().filter(x -> x % 2 == 0).map(y -> y * 2).forEach(z -> System.out.println(z));
    }
    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

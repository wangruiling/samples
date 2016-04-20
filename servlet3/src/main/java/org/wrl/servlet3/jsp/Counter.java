package org.wrl.servlet3.jsp;

/**
 * Created by wangrl on 2016/4/19.
 */
public class Counter {
    private static int count;

    public static synchronized int getCount() {
        count++;
        return count;
    }
}

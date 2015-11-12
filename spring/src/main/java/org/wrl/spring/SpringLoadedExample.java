package org.wrl.spring;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-12 12:36
 */
public class SpringLoadedExample {
    public static void main(String[] args) throws Exception{
        Reload reload = new Reload();

        while (true) {
            reload.load();

            TimeUnit.SECONDS.sleep(3);
        }
    }

    public static class Reload {

        public void load() {
            System.out.println("reload!");
        }
    }
}

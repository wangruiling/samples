package org.wrl.jvm.demo;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * 方法区
 * jdk1.6、1.7：-XX:+PrintGCDetails -XX:PermSize=5M -XX:MaxPermSize=5m
 * jdk1.8：-XX:+PrintGCDetails -XX:MaxMetaspaceSize=50M
 * @author: wangrl
 * @Date: 2015-06-10 16:50
 */
public class PermTest {

	public static void main(String[] args) {
		int i = 0;
		try {
			for (i = 0; i < 100000; i++) {
				CglibBean bean = new CglibBean("org.wrl.jvm.demo" + i, new HashMap<>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("total create count:" + i);
		}
	}

}

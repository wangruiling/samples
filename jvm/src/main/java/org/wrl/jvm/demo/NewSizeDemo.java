package org.wrl.jvm.demo;

/**
 * Created with IntelliJ IDEA.
 * 测试新生代配置
 * ①:-Xmx20m -Xms20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails
 * ②:-Xmx20m -Xms20m -Xmn7m -XX:SurvivorRatio=2 -XX:+PrintGCDetails
 * ③:-Xmx20m -Xms20m -Xmn15m -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 * ④:-Xmx20m -Xms20m -XX:NewRatio=2 -XX:+PrintGCDetails
 * @author: wangrl
 * @Date: 2015-06-10 15:40
 */
public class NewSizeDemo {

	public static void main(String[] args) {
		byte[] b = null;
		for (int i = 0; i < 10; i++) {
			b = new byte[1 * 1024 * 1024];
		}
	}

}

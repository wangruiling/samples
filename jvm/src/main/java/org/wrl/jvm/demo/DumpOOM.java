package org.wrl.jvm.demo;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * 堆溢出处理
 * -Xmx20m -Xms5m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d:/a.dump
 * -Xmx20m -Xms5m "-XX:OnOutOfMemoryError=C:/Java/jdk1.8.0_40/printstack.bat %p" -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d:/a.dump
 * @author: wangrl
 * @Date: 2015-06-10 16:21
 */
public class DumpOOM {

	public static void main(String[] args) {
		Vector v = new Vector();
		for (int i = 0; i < 25; i++) {
			v.add(new byte[1024 * 1024]);
		}
	}

}

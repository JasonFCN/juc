package org.example;

import org.openjdk.jol.info.ClassLayout;

/**
 * 偏向锁标识：在jdk1.6,jvm默认启用偏向锁 -XX:+UseBiasedLocking,
 * 所以对象头的标志位为：101
 */
public class SimpleInt {
    private int state;

    public static void main(String[] args) {
        SimpleInt simpleInt = new SimpleInt();
        System.out.println(ClassLayout.parseInstance(simpleInt).toPrintable());
    }
}

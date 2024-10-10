package org.example;

public class BreakTest {
    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            System.out.println("1");
//            for (int j = 0; j < 10; j++) {
//                if(i == 2 && j == 3){
//                    break;
//                }
//                System.out.println("2");
//            }
//        }
        int i = 1;
        System.out.println("1的二进制：" + Integer.toBinaryString(i));
        System.out.println("~1的二进制：" + Integer.toBinaryString(~i));

        int j = -1;
        System.out.println("-1的二进制：" + Integer.toBinaryString(j));
        System.out.println("~-1的二进制：" + Integer.toBinaryString(~j));
    }
}

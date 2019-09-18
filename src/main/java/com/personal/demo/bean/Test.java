package com.personal.demo.bean;

import lombok.Data;

import java.util.*;


@Data
public class Test {

    public Object a(){
        int i=1;
        return i;
    }

    public static void main(String[] args) {
        String str = "0123,1321,2,3,4,5";
        String[] arr = str.split(","); // 用,分割
        System.out.println(Arrays.toString(arr));

        for (int i = 0; i <arr.length ; i++) {
            String sa = arr[i];
            System.out.println(sa);
        }
    }
}

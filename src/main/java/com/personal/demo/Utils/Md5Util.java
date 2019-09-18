package com.personal.demo.Utils;

import org.apache.shiro.crypto.hash.Md5Hash;


public class Md5Util {
  //密码散列次数
  public static int HASHITERATIONS = 4;

  public static String getMD5(String msg,String salt){
    return new Md5Hash(msg,salt,HASHITERATIONS).toString();
  }



  /**
   * 测试
   * @param args
   */
  public static void main(String[] args) {
  String str= getMD5("123456","tom");
   System.out.println(str);
  }
}

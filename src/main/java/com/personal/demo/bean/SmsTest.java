package com.personal.demo.bean;




import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
    /*
    pom.xml
    <dependency>
      <groupId>com.aliyun</groupId>
      <artifactId>aliyun-java-sdk-core</artifactId>
      <version>4.0.3</version>
    </dependency>
    */
//    public class SmsTest {
//        public static void main(String[] args) {
//            DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIHU6PBK3V61uC", "dY1UlZtHPaxwOOYD5lvwZwn5v6uEuY");
//            IAcsClient client = new DefaultAcsClient(profile);
//
//            CommonRequest request = new CommonRequest();
//            request.setMethod(MethodType.POST);
//            request.setDomain("dysmsapi.aliyuncs.com");
//            request.setVersion("2017-05-25");
//            request.setAction("SendSms");
//            request.putQueryParameter("PhoneNumbers", "15559168675");
//            request.putQueryParameter("SignName", "博弈");
//            request.putQueryParameter("TemplateCode", "SMS_169897729");
//            request.putQueryParameter("TemplateParam", " {\"code\":\"1234\"}");
//
//            try {
//                CommonResponse response = client.getCommonResponse(request);
//                System.out.println(response.getData());
//            } catch (ServerException e) {
//                e.printStackTrace();
//            } catch (ClientException e) {
//                e.printStackTrace();
//            }
//        }
//
//}

package cn.wolfshadow.gs.message.util;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SmsUtil {

    public static void main(String[] args) {
        String regionId = "cn-hangzhou";
        String accessKeyId = "LTAI4G3ixVgwT2P1WUyAzrDV";
        String accessSecret = "OVQ1UVd9URinghkGCb30GWJdhpbmoC";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "13408102374");
        request.putQueryParameter("SignName", "WolfShadow");
        request.putQueryParameter("TemplateCode", "SMS_202750046");
        request.putQueryParameter("TemplateParam", "{ 	\"code\": \"999999\" }	");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static String sendSms(List<String> phoneNums, String regionId, String accessKeyId, String accessSecret,
                               String sysDomain, String sysVersion,
                               String singName, String templateCode,
                               String code) throws ClientException {
        //手机号用逗号隔开
        StringBuffer sb = new StringBuffer();
        phoneNums.stream().forEach(num ->{
            sb.append(num).append(",");
        });
        String nums = sb.toString();
        nums= nums.substring(0, nums.length() - 1);

        //阿里云验证要求低于6位

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(sysDomain);
        request.setSysVersion(sysVersion);
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", nums);
        request.putQueryParameter("SignName", singName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{ 	\"code\": \""+code+"\" }	");

        CommonResponse response = client.getCommonResponse(request);
        //System.out.println(response.getData());


        return response.getData();
    }
}

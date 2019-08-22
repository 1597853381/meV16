package com.qf.v16order.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaoxinmin
 * @Date 2019/8/19
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @RequestMapping("toPay")
    @ResponseBody
    public void toPay(HttpServletResponse httpResponse) throws IOException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do  ",
                "2016100200645647",
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCkzFZsMxwXdLXfLhHscxQAPlENhcXLx3ZVts9knHl/i/OrDFZoRcy4cwkWOznqqdMqhRMJgjjPe1uMVp1Oe+YEkZl5RSr+RseME+/CffQm1xVdHjpLxDHVr7rBAwh5rbIH1aCwmNyLBToI61gWQmjaSflWXQdyLa9NS9+SG38nMYIRJnDdPhRtsJ1uTjzIkpEUfFmzAQu4rBfSmfZE+GRMfrXXe3mJM6ix/o4kRoeDpKznSotvM1zXYwA+u5JmkpbmtXoAj0Jk/7vamJ6ljFB0x2CgTu14YaozG4a2W2/fIJu8b+NPs4LWH9KfRsLarclnRI7JQs8i3REjlo/jmkMXAgMBAAECggEAC0NH2Mc5cUTxtRoR8YX/5PBdW747wWyVYtBGpkqnZxcqhRMnyKKUljqOcW2EzMvkebB0l57FwTBeFl5JC/cdxkwpIAehI5W0x5cJONKg4BKtDBsnwsva2A7VhaFub9p2I2DvRYJ8+CdYSwnCkGrjfZ+2obdj7eK/a1n3IqYsb4IvQFPE/Afqr6+ktfcN4SFGBh55tzAtDzWf6Jwujc2ZePhjsOF3A+NdGDH66Ic2HfKkDHgfFzlhiPXkzKyiIx9m1lqJb5cmPojVgmxIwNzsu42vLsWJhZ39HVq/PcYyOLSQoIbePw1LfBbt1d0EWm232djDO+u0HrGcZiQu8dlX6QKBgQDxKzD8JLfpvuq2BK/gyuvnpTzmDYulH9FuJPMAIT7M/xFvQ89VOh7P/MrHcQDe8ZWd/cpRUqpOjqc9o+fygq6ymlBhtzQV8s2QiYfZQkpzUdV2W90qobJe6PmLVHNcBwYuTKoCDxqcMsNT06DW8lTeX9FS1xr1IQyXPStfX+gm0wKBgQCu7tEWT6YwpkuReRBkyEJJrS0OnUqiXB2kdrhxFQb2xuPzOoAx8uzICyj3xJVai0Vp1yTq38CCsMoQE0k0VleTuRVf89FhgSqQo42IG8YlIBA5AHH5EgpNGVRqk9Xm+41KkQJdDlOY/7N9jMkPUZEWCPVQ02jVz+AwTDyaTsDQLQKBgQCrPHSig1vGbxVuOfO7U7mel3efDP0bOHVWlIupivFxUqZJb0dWh1b7bM16QOVSZwxJyrqNdXmRH77liGyXh2yNSf5K827bKrQErXI0s9ny+absTBvEOl1RAHC3GjZyoFYmJ68KmTjWsUB5U6k/FX9nCXeyLyNn65hqBje2l31ZmwKBgHNw9JiFrYWzaHEy9IedxGj8mJxpXqRwp7suAZ/WoYbfT/TmaU0NJAcLnliL6YgcGo2zRhCd4lwcsOwU3h1k0VySKrHspAN1ixJybGci9Qrw8uO7EHuu1Q92JMhEkUdiQCayNV4Vo0N3kwrKirDKQjXJaaKSMT/QdAFLfj7Ss9ltAoGBAO3tAVHUy7CShLGmY+OZNMiQwBkx132MQvTtJpBwzCCIBM1UMX0qf/GbsufWSmleS7lUYpz/i3OuHtqI56oDKvA4RgFaNy1JcIc5/p9fMyimkKNwpw+bglRi6IfAAKS9UqvE/0n7mWLP3BoJdkE7xrpE0P8HFUU2l/vcBN9JA8eU",
                "json",
                "utf-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApMxWbDMcF3S13y4R7HMUAD5RDYXFy8d2VbbPZJx5f4vzqwxWaEXMuHMJFjs56qnTKoUTCYI4z3tbjFadTnvmBJGZeUUq/kbHjBPvwn30JtcVXR46S8Qx1a+6wQMIea2yB9WgsJjciwU6COtYFkJo2kn5Vl0Hci2vTUvfkht/JzGCESZw3T4UbbCdbk48yJKRFHxZswELuKwX0pn2RPhkTH6113t5iTOosf6OJEaHg6Ss50qLbzNc12MAPruSZpKW5rV6AI9CZP+72piepYxQdMdgoE7teGGqMxuGtltv3yCbvG/jT7OC1h/Sn0bC2q3JZ0SOyULPIt0RI5aP45pDFwIDAQAB",
                "RSA2");//获得初始化的AlipayClient
        //构建请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        //设置同步回调
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        //设置异步回调
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        //设置业务参数
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101001\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=utf-8");
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
}

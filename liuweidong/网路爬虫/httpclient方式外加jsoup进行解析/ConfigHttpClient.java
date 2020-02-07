package cn.heongda.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ConfigHttpClient {
    public static void main(String[] args) throws Exception {
        // 创建浏览器
        CloseableHttpClient httpClients = HttpClients.createDefault();

        // 创建httpGet对象，传递相关的url(如果是post请求，则直接将httpGet 改为 HttpPost 即可)
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        // 创建请求的配置信息
        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000) // 获取连接时长
                .setConnectionRequestTimeout(1000)   // 获取连接
                .setSocketTimeout(1000 * 10) // 数据传输时长
                .build();

        httpGet.setConfig(config);

        // 发送请求
        CloseableHttpResponse response = httpClients.execute(httpGet);

        // 判断状态码是否正常，对相应结果进行解析
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();

            //使用工具将html页面以字符串的形式进行输出
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s);
        }

    }
}

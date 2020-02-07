package cn.heongda.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws  Exception {
        // 创建浏览器
        CloseableHttpClient httpClients = HttpClients.createDefault();

        // 创建httpGet对象，传递相关的url(如果是post请求，则直接将httpGet 改为 HttpPost 即可)
        HttpGet httpGet = new HttpGet("https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&suggest=1.his.0.0&wq=&pvid=3fd4770ac8884b078c4c78dae817edf4");
        // 发送请求
        CloseableHttpResponse response = httpClients.execute(httpGet);

        // 判断状态码是否正常，对相应结果进行解析
        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity entity = response.getEntity();

            //使用工具将html页面以字符串的形式进行输出
            String s = EntityUtils.toString(entity, "utf-8");
            Document document = Jsoup.parse(s);
            String s1 = document.select("#J_goodsList").first().className();
            System.out.println(s1);
        }

    }

    @org.junit.Test
    public void testHttpGetParam() throws Exception{
        CloseableHttpClient httpClients = HttpClients.createDefault();

        /*https://daohang.qq.com/?fr=hmpage*/
        // 创建URIbuilder  和 设置参数
        URIBuilder uriBuilder = new URIBuilder("https://uland.taobao.com/sem/tbsearch");

        // 设置参数
        uriBuilder.setParameter("spm","a2e15.8261149.07626516003.2.8a4929b4M4Nzq0").setParameter("refpid","mm_15891853_2192459_8654707")
                .setParameter("clk1","6358118e58f01b58f0659864737adc8c").setParameter("keyword","%E6%89%8B%E6%9C%BA").setParameter("page","3")
        .setParameter("_input_charset","utf-8");


        HttpGet httpGet = new HttpGet(uriBuilder.build());

        CloseableHttpResponse response = httpClients.execute(httpGet);

        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s);
        }
    }


    /**
     * 带参数的post请求
     * @throws Exception
     */
    @org.junit.Test
    public void testHttpPostParam() throws Exception{
        CloseableHttpClient httpClients = HttpClients.createDefault();

        /*https://daohang.qq.com/?fr=hmpage*/

        HttpPost httpPost = new HttpPost("https://daohang.qq.com/");

        // 创建一个list用于封装表单的参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("fr","hmpage"));

        // 创建表单entity
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);

        // 将表单数据进行传送
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = httpClients.execute(httpPost);

        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s.length());
        }
    }

    /**
     * 测试httpClient连接池
     */
    @org.junit.Test
    public void testHttpClientPool(){
        // 创建httpclient连接池管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        // 设置最大连接数
        cm.setMaxTotal(100);

        // 设置每一个主机最大连接数( 100 个链接 百度最多10个 新浪10 个 ....
        cm.setDefaultMaxPerRoute(10);

        doGet(cm);
    }

    private void doGet(PoolingHttpClientConnectionManager cm) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        HttpGet httpget = new HttpGet("http://wwww.baidu.com");

        CloseableHttpResponse response = null;

        try {
             response = httpClient.execute(httpget);

             if(response.getStatusLine().getStatusCode() == 200){
                 HttpEntity entity = response.getEntity();
                 String s = EntityUtils.toString(entity, "utf-8");
                 System.out.println(s.length());
             }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 不必关闭httpclient
        }
    }
}

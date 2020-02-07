package cn.heongda.httpclient;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JsoupTest {
    public static void main(String[] args) {

    }

    /**
     * 使用jsoup来解析一个url
     */
    @Test
    public void testJsoupUrl() throws Exception{
        // 解析url地址，第一个参数：url 第二个参数 ： 有效时间
        Document document = Jsoup.parse(new URL("https://login.dangdang.com/Signin.aspx?ReturnUrl=http://myhome.dangdang.com/"), 100000);
        // 获取页面中dom及其内容
        String title = document.getElementsByTag("title").text();
        System.out.println(title);
    }

    /**
     * 使用jsoup解析html的字符串
     */

    @Test
    public void testJsoupString() throws Exception{
        // 读取文件成字符串
        String content = FileUtils.readFileToString(new File("C:\\Users\\Administrator\\Desktop\\index.html"), "utf-8");

        // 进行解析
        Document document = Jsoup.parse(content);

        String title = document.getElementsByTag("title").text();

        System.out.println(title);
    }

    /**
     * 解析文件
     */
    @Test
    public void testJsoupFile() throws IOException {
        Document document = Jsoup.parse(new File("C:\\Users\\Administrator\\Desktop\\index.html"), "utf-8");
        // 进行解析

        String title = document.getElementsByTag("title").first().text();

        System.out.println(title);
    }

    /**
     * Jsoup的dom进行解析
     */
    @Test
    public void testJsoupDom() throws Exception{
        Document document = Jsoup.parse(new File("C:\\Users\\Administrator\\Desktop\\index.html"), "utf-8");

        // 根据id进行进行解析
        String cars = document.getElementById("tip").text();
        System.out.println(cars);

        // 根据class属性进行解析
        String cars1 = document.getElementsByClass("cars").first().text();
        System.out.println(cars1);

        // 根据标签进行解析，不在进行演示

        // 根据attribute进行解析
        Elements elements = document.getElementsByAttribute("name");
        String text = elements.text();
        System.out.println(text);
    }

    /***
     *
     * Josup获取元素中的一些属性值
     */

    @Test
    public void testJsoupAttribute() throws Exception{
        Document document = Jsoup.parse(new File("C:\\Users\\Administrator\\Desktop\\index.html"), "utf-8");

        // 根据id进行进行解析
        Element element = document.getElementById("tip");

        // 获取id值
        String id = element.id();
        System.out.println(id);

        // 获取class名
        String s = element.className();
        System.out.println(s+"=======");

        // 根据attr获取相关的值
        String id1 = element.attr("id");
        System.out.println(id1);

        // 从一个元素中获取所有属性
        Attributes attributes = element.attributes();
        System.out.println(attributes);

        // 获取元素的文本内容
        String text = element.text();
    }

    /**
     * 通过selector选择器进行解析
     */
    @Test
    public void testSelector() throws Exception{
        Document document = Jsoup.parse(new File("C:\\Users\\Administrator\\Desktop\\index.html"), "utf-8");

        // 根据标签进行获取
        Element h2 = document.select("h2").first();
        System.out.println(h2.text());

        // 根据id
        Elements ele = document.select("#tip");
        System.out.println(ele.text());

        // 根据class
        Element first = document.select(".cars").first();
        System.out.println(first.text());

        // 根据属性进行查找
        Element first1 = document.select("[abc]").first();
        System.out.println(first1.text());

        // 根据属性值进行查找
        Element select = document.select("[abc='225']").first();
        System.out.println(select.text());
    }

}

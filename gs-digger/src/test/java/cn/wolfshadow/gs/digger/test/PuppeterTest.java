package cn.wolfshadow.gs.digger.test;


import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;

public class PuppeterTest {

    @SneakyThrows
    @Test
    public void testPuppeter(){
        //chrome浏览器地址
        String chromePath = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
        String url = "http://basic.10jqka.com.cn/000063/finance.html";
        String path = new String("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe".getBytes(),"UTF-8");

        ArrayList<String> arrayList = new ArrayList<>();
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(arrayList).withHeadless(false).build();
//        LaunchOptions options = new OptionsBuilder().withArgs(arrayList).withHeadless(false).withExecutablePath(path).build();
        arrayList.add("--no-sandbox");
        arrayList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo(url);

        String content = page.content();
        System.out.println("=======================content=============="+content);
    }

    @SneakyThrows
    @Test
    public void testJsoup(){
        String url = "http://basic.10jqka.com.cn/000063/finance.html";
        Document document = Jsoup.connect(url).timeout(3000).get();
        String body = document.getElementsByTag("body").text();
        System.out.println(body);

    }

    /**
     * 利用chrome方式获取页面信息
     * @return
     */
    @SneakyThrows
    @Test
    public void test(){
        String url = "http://basic.10jqka.com.cn/000063/finance.html";
        Document document = null;
        //chrome浏览器地址
        String chromePath = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";

        //nodejs地址  + 截图的js的地址（两个需要在同一个目录之下）
        //String nodeJSPath = "nodejs根目录地址   渲染页面所需要的js根目录地址.js";
        String nodeJSPath = "C:\\Software\\node-v14.8.0-win-x64\\node.exe C:\\Software\\node-v14.8.0-win-x64\\node_modules\\puppeteer\\cjs-entry-core.js";

        String BLANK = "    ";

        String exec =  nodeJSPath + BLANK + chromePath + BLANK + url;

        //执行脚本命令
        Process process = Runtime.getRuntime().exec(exec);

        System.err.println("ecec =======> " + exec);

        InputStream is = process.getInputStream();
        //document = Jsoup.parse(is, "UTF-8", url);
        document = Jsoup.parse(is, "GBK", url);

        process.waitFor();

        process.destroy();
        process = null;

    }


}

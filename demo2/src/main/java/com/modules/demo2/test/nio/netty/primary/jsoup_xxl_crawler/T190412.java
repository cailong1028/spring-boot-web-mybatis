package com.modules.demo2.test.nio.netty.primary.jsoup_xxl_crawler;

import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.parser.PageParser;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class T190412 {

    public static void main(String[] args) {
        String url = "https://gitee.com/xuxueli0323/projects?page=1";
        TJsoup(url);
//        TXxlCrawler(url);
//        TXxlCrawler2();
//        TXxlCrawler3();
    }

    private static void TJsoup(String url){
        try {
            Document doc = Jsoup.connect(url).get();
            System.out.println(doc.toString());
            System.out.println("title is: "+doc.title());
            System.out.println("all links");
            Elements eles = doc.select("a[href]");
            for(Element ele:eles){
                System.out.println("\tlink: "+ ele.attr("href"));
                System.out.println("\tlink: "+ ele.text(""));
            }
            eles = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            for (Element image : eles)
            {
                System.out.println("\tsrc : " + image.attr("src"));
                System.out.println("\theight : " + image.attr("height"));
                System.out.println("\twidth : " + image.attr("width"));
                System.out.println("\talt : " + image.attr("alt"));
            }


            if(doc.select("meta[name=description]").size() > 0){
                String description = doc.select("meta[name=description]").get(0).attr("content");
                System.out.println("Meta description : " + description);
            }

            if(doc.select("meta[name=keywords]").size() > 0){
                String keywords = doc.select("meta[name=keywords]").first().attr("content");
                System.out.println("Meta keyword : " + keywords);
            }

            Elements inputElements = doc.getElementsByTag("input");
            for (Element inputElement : inputElements) {
                String key = inputElement.attr("name");
                String value = inputElement.attr("value");
                System.out.println("\tParam name: "+key+" \n\tParam value: "+value);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void TXxlCrawler(String url){
        XxlCrawler crawler = new XxlCrawler.Builder().setUrls(url)
                .setThreadCount(3).setPageParser(new PageParser<AuthPageVO>() {
                    @Override
                    public void parse(Document document, Element element, AuthPageVO pageVo) {
                        String pageUrl = document.baseUri();
                        System.out.println(pageUrl + ":::" + pageVo.getLoginName());
                    }

                }).build();
        crawler.start(true);
    }

    @Getter
    @Setter
    @PageSelect(cssQuery = "body")
    public static class PageVo2 {

        @PageFieldSelect(cssQuery = "#cl-left-list-toggle > div > div > a")
        private String url;

    }
    private static void TXxlCrawler2(){
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("http://123.57.244.51/article/5672d960-46d0-11e9-9de1-491af286f86a")
                //.setWhiteUrlRegexs("/article/\\w+")
                .setThreadCount(1)
                .setPageParser(new PageParser<PageVo2>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, PageVo2 pageVo) {
                        // 解析封装 PageVo 对象
                        String pageUrl = html.baseUri();
                        System.out.println(pageUrl + "???" + pageVo.toString());
                    }
                })
                .build();
        crawler.start(true);
    }

    @Setter
    @Getter
    @PageSelect(cssQuery = "body")
    public static class PageVo {

        @PageFieldSelect(cssQuery = ".blog-heading .title")
        private String title;

        @PageFieldSelect(cssQuery = "#read")
        private int read;

        @PageFieldSelect(cssQuery = ".comment-content")
        private List<String> comment;
    }

    private static void TXxlCrawler3(){
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("https://my.oschina.net/xuxueli/blog")
                .setWhiteUrlRegexs("https://my\\.oschina\\.net/xuxueli/blog/\\d+")
                .setThreadCount(1)
                .setPageParser(new PageParser<PageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, PageVo pageVo) {
                        // 解析封装 PageVo 对象
                        String pageUrl = html.baseUri();
                        System.out.println(pageUrl + "：" + pageVo.toString());
                    }
                })
                .build();
        crawler.start(true);
    }



}

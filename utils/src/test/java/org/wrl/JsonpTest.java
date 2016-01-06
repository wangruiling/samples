package org.wrl;

import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author: wangrl
 * @Date: 2016-01-05 0:08
 */
public class JsonpTest {


    @Test
    public void testMain() throws IOException {

        String url = "http://t66y.com/read.php?tid=1784155&fpage=0&toread=&page=13";
        proceHtml(url);

    }

    private void proceHtml(String url) throws IOException {

        HttpEntity httpEntity = Request.Get(url).execute().returnResponse().getEntity();
        if (httpEntity != null) {
            String html = EntityUtils.toString(httpEntity, Charset.forName("gb2312"));
            Document doc = Jsoup.parse(html, "gb2312");
            Element body = doc.body();
            Elements divs = body.getElementsByAttributeValue("class", "tpc_content");
            for (Element element : divs) {
                System.out.println(element.text());
                System.out.println("---------------------------------------------");
            }
        }

    }


}

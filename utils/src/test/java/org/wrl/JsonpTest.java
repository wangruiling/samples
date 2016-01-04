package org.wrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * @author: wangrl
 * @Date: 2016-01-05 0:08
 */
public class JsonpTest {

    @Test
    public void testMain() throws IOException {
        String url = "";
        proceHtml(url);
    }

    private void proceHtml(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element body = doc.body();
        Elements divs = body.getElementsByAttributeValue("class", "tpc_content");
        for (Element element : divs) {
            System.out.println(element.text());
            System.out.println("---------------------------------------------");
        }
    }


}

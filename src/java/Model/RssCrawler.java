/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.chilkatsoft.CkRss;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;
import vn.hus.nlp.tokenizer.VietTokenizer;

/**
 *
 * @author Diep
 */
public class RssCrawler extends TimerTask {

//    public RssCrawler(String s) {
//
//        try {
//            System.loadLibrary(s+"/chilkat");
//        } catch (UnsatisfiedLinkError e) {
//            System.err.println("Native code library failed to load.\n" + e);
//            System.exit(1);
//        }
//
//    }
// tuoitre.vn,vietnamnet.vn,thanhnien.vn,dantri.com.vn,vietbao.vn,laodong.vn,tienphong.vn,vnexpress.vn
    String[] rssLink = {
        "http://thethao.thanhnien.vn/rss/home.rss",
        "http://thanhnien.vn/rss/chinh-tri-xa-hoi.rss",
        "http://thanhnien.vn/rss/the-gioi.rss",
        "http://thanhnien.vn/rss/van-hoa-nghe-thuat.rss",
        "http://thanhnien.vn/rss/doi-song.rss",
        "http://thanhnien.vn/rss/the-gioi-tre.rss",
        "http://thanhnien.vn/rss/giao-duc.rss",
        "http://thanhnien.vn/rss/cong-nghe-thong-tin.rss",
        "http://xe.thanhnien.vn/rss/home.rss",
        "http://tuoitre.vn/rss/thoi-su.rss",
        "http://tuoitre.vn/rss/the-gioi.rss",
        "http://tuoitre.vn/rss/the-gioi.rss",
        "http://tuoitre.vn/rss/giao-duc.rss",
        "http://tuoitre.vn/rss/the-thao.rss",
        "http://tuoitre.vn/rss/phap-luat.rss",
        "http://vietnamnet.vn/rss/thoi-su.rss",
        "http://vietnamnet.vn/rss/the-thao.rss",
        "http://vietnamnet.vn/rss/doi-song.rss",
        "http://vietnamnet.vn/rss/the-gioi.rss",
        "http://vietnamnet.vn/rss/cong-nghe.rss",
        "http://vietnamnet.vn/rss/giao-duc.rss",
        "http://dantri.com.vn/xa-hoi.rss",
        "http://dantri.com.vn/giao-duc-khuyen-hoc.rss",
        "http://dantri.com.vn/the-thao.rss",
        "http://dantri.com.vn/the-gioi.rss",
        "http://dantri.com.vn/o-to-xe-may.rss",
        "http://dantri.com.vn/doi-song.rss",
        "http://vietbao.vn/live/Bong-da/rss.xml",
        "http://vietbao.vn/live/Doi-song-Gia-dinh/rss.xml",
        "http://vietbao.vn/live/Giao-duc/rss.xml",
        "http://vietbao.vn/live/O-to-xe-may/rss.xml",
        "http://vietbao.vn/live/The-gioi/rss.xml",
        "http://vietbao.vn/live/The-thao/rss.xml",
        "http://vietbao.vn/live/Xa-hoi/rss.xml",
        "http://www.tienphong.vn/rss/xa-hoi-2.rss",
        "http://www.tienphong.vn/rss/the-gioi-5.rss",
        "http://www.tienphong.vn/rss/the-thao-11.rss",
        "http://www.tienphong.vn/rss/giao-duc-71.rss",
        "http://www.tienphong.vn/rss/cong-nghe-45.rss",
        "https://laodong.vn/rss/xa-hoi.rss",
        "https://laodong.vn/rss/thoi-su.rss",
        "https://laodong.vn/rss/the-gioi.rss",
        "https://laodong.vn/rss/the-thao.rss",
        "https://laodong.vn/rss/cong-nghe.rss",
        "https://laodong.vn/rss/van-hoa-giai-tri.rss",
        "https://vnexpress.net/rss/thoi-su.rss",
        "https://vnexpress.net/rss/the-gioi.rss",
        "https://vnexpress.net/rss/the-thao.rss",
        "https://vnexpress.net/rss/giao-duc.rss",
        "https://vnexpress.net/rss/oto-xe-may.rss",
        "https://vnexpress.net/rss/khoa-hoc.rss",};

    @Override
    public void run() {
        System.out.println("ngu vkl");
        /*HashMap title = new HashMap();
        HashMap article = new HashMap();
        VietTokenizer viet = new VietTokenizer();

        IOFile.readArticle(title, article,"article/");

        HashMap dictionary = IOFile.readDictionaryD("tudien.txt");

        HashMap stopword = IOFile.readDictionaryD("stopword.txt");

        int gram1=0;
        int gram2=0;
        
        for (int k = 0; k < rssLink.length; k++) {
            CkRss rss = new CkRss();

            //  Download bài từ URL:
            boolean success = rss.DownloadRss(rssLink[k]);
            if (success != true) {
                System.out.println(rss.lastErrorText());
                return;
            }

            //  lấy channel.
            CkRss rssChannel;

            rssChannel = rss.GetChannel(0);
            if (rssChannel == null) {
                System.out.println("No channel found in RSS feed.");
                return;
            }

            //  thông tin channel:
            System.out.println("Title: " + rssChannel.getString("title"));
            System.out.println("Link: " + rssChannel.getString("link"));
            System.out.println("Description: " + rssChannel.getString("description"));

            //  lấy từng title, link của bài viết
            
            int numItems = rssChannel.get_NumItems();
            int i;
            
            for (i = 0; i <= numItems - 1; i++) {
                CkRss rssItem = rssChannel.GetItem(i);

                System.out.println("----");
                System.out.println("Title: " + rssItem.getString("title"));
                System.out.println("Link: " + rssItem.getString("link"));
                System.out.println("pubDate: " + rssItem.getString("pubDate"));

                String s = rssItem.getString("title");

                if (title.containsValue(s) != true && rssItem.getString("link") != null) {

                    s = s.trim();
                    title.put(title.size() + 1, s);
                    article.put(s, rssItem.getString("link"));

                    s = viet.segment(s);
                    s = s.replaceAll("\\+", "");
                    s = s.replaceAll("-", "");
                    s = s.replaceAll(":", "");
                    s = s.replaceAll(",", "");
                    s = s.replaceAll("\\?", "");
                    s = s.replaceAll("\"", "");
                    s = s.replaceAll("“", "");
                    s = s.replaceAll("”", "");
                    s = s.replaceAll("\\.", "");
                    s = s.replaceAll("…", "");
                    s = s.replaceAll("!", "");
                    s = s.replaceAll("\\(", "");
                    s = s.replaceAll("\\)", "");
                    s = s.replaceAll("\\s+", " ");
                    s = s.replaceAll("’", "");
                    s = s.replaceAll("‘", "");
                    s = s.replaceAll("'", "");
                    s = s.replaceAll("'", "");

                    s = s.replaceAll("﻿", "");
                    s = s.trim();
                    s = s.toLowerCase();
                    System.out.println(s);
                    String[] word = s.split("\\s");
                    for (String w : word) {

                        w = w.replaceAll("_", " ");
                        w = w.trim();
                        w = w.replaceAll("﻿", "");
                        if (dictionary.containsValue(w) == false && stopword.containsValue(w) == false && w.equals("") == false
                                && w.length() != 1 && w.equals("﻿") == false && w != null) {
                            w = w.trim();
                            gram1++;
                            dictionary.put(dictionary.size() + 1, w);
                            System.out.println(i + "-----" + w);
                        }
                    }
                    for (int t = 0; t < word.length - 1; t++) {

                        String s3 = word[t] + " " + word[t + 1];
                        s3 = s3.replaceAll("_", " ");
                        s3 = s3.trim();
                        s3 = s3.replaceAll("﻿", "");
                        if (dictionary.containsValue(s3) == false && stopword.containsValue(s3) == false && s3.equals("") == false
                                && word[t].length() != 1 && word[t + 1].length() != 1 && s3.equals("﻿") == false) {
                            s3 = s3.trim();
                            gram2++;
                            dictionary.put(dictionary.size() + 1, s3);
                            System.out.println(i + "-----" + s3);
                        }
                    }

                }
            }

        }*/
//        System.out.println(dictionary.size());
//        System.out.println(stopword.size());
//        System.out.println(gram1);
//        System.out.println(gram2);
//        IOFile.writeDictionary("tudien.txt", dictionary);
//        IOFile.writeArticle(article);
    }

}

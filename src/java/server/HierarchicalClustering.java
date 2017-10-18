/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import AlgorithmClustering.*;
import Model.CosineSimilarity;
import Model.GenerateVector;
import Model.IOFile;
import Model.RssCrawler;
import Model.Word;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import opennlp.tools.coref.mention.Dictionary;
import vn.hus.nlp.tokenizer.VietTokenizer;

/**
 *
 * @author Diep
 */
public class HierarchicalClustering extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        TimerTask crawler = new RssCrawler();
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(crawler, 2*1000, 120*60*1000);
        String mes1 = null;
        String mes2 = null;
        String slice = request.getParameter("slice");
        try {
            if (slice == null || slice == "") {
                mes1 = "Không được để trống";
            } else {
                int i = Integer.parseInt(slice);
                if (i < 0) {
                    mes1 = "Không nhập số âm";
                }
            }
        } catch (NumberFormatException e) {
            mes2 = "Nhập sai định dạng";
        }
        if (mes1 == null && mes2 == null) {
            String s = getServletContext().getRealPath("/WEB-INF");

            System.out.println(s);

            HashMap dictionaryV = new HashMap();
            HashMap title = new HashMap();
            HashMap article = new HashMap();
            HashMap<Integer, Integer> df = new HashMap<>();

            ArrayList<HashMap<Integer, Word>> word = new ArrayList<>();

            // tạo file properties tới vntokenizer
            Properties pro = new Properties();
            pro = getProperties();

            // đọc các bài viết, từ điển, stopword
            IOFile.readArticle(title, article, s + "/article/");

            dictionaryV = IOFile.readDictionaryV(s + "/tudien.txt"); // đọc từ điển để sinh vector
            //System.out.println(title);
            //System.out.println(article);
            // System.out.println(dictionaryV);

            // sinh vector tần suất xuất hiện tf và df
            GenerateVector.generateVector(word, title, dictionaryV, df, pro);
            IOFile.writeVector(word, title, s);
            //System.out.println(df);
            dictionaryV.clear();
            // tính cosine similarity
            ArrayList<HashMap<Integer, Double>> W = CosineSimilarity.TfDf(word, df);
            ArrayList<Double> norm = CosineSimilarity.calculateNorm(W);
            double[][] cosine = CosineSimilarity.calculateCosine(W, norm);
            writeW(cosine, title.size(), s + "/w.txt");
            writen(norm, s + "/n.txt");
            writetf(W, s + "/tf.txt");
            String[] names = getName(title);

            ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
            Cluster cluster = alg.performClustering(cosine, names, new AverageLinkageStrategy());
            ArrayList<HashMap> hash = new ArrayList<HashMap>();
            cluster.toConsole(0, Integer.parseInt(slice), hash);
            ArrayList<String> titleHash = getTitleHash(cosine, hash, title);

            System.out.println(titleHash.size());
            request.setAttribute("title", titleHash);
            request.setAttribute("list", hash);
            request.setAttribute("article", article);

            W.clear();
            norm.clear();
        }
        request.setAttribute("mes1", mes1);
        request.setAttribute("mes2", mes2);
        RequestDispatcher r = getServletContext().getRequestDispatcher("/index.jsp");
        r.forward(request, response);
    }

    public ArrayList<String> getTitleHash(double[][] cosine, ArrayList<HashMap> hash, HashMap title) {
        ArrayList<String> titles = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap();
        for (int i = 1; i <= title.size(); i++) {
            map.put((String) title.get(i), i);
        }
        for (int i = 0; i < hash.size(); i++) {
            double max = 0;
            int k = 1;
            if (hash.get(i).size() > 2) {
                for (int j = 1; j <= hash.get(i).size(); j++) {

                    double max1 = 0;
                    for (int t = 1; t <= hash.get(i).size(); t++) {
                        if (j != t) {
                            max1 = max1 + cosine[map.get(hash.get(i).get(j)) - 1][map.get(hash.get(i).get(t)) - 1];
                        }
                    }
                    if (max1 > max) {
                        max = max1;
                        k = j;
                    }
                }
            }

            titles.add((String) hash.get(i).get(k));
        }
        map.clear();
        return titles;
    }

    public void writetf(ArrayList<HashMap<Integer, Double>> norm, String s) {
        PrintWriter wr = null;
        try {

            wr = new PrintWriter(s, "UTF-8");
            for (int i = 0; i < norm.size(); i++) {
                wr.write("\r\n");
                for (Map.Entry<Integer, Double> en : norm.get(i).entrySet()) {
                    wr.write("" + en.getValue() + "   ");
                }

            }
            wr.close();
        } catch (Exception ex) {
            ex.toString();
        }

    }

    public void writen(ArrayList<Double> norm, String s) {
        PrintWriter wr = null;
        try {

            wr = new PrintWriter(s, "UTF-8");
            for (int i = 0; i < norm.size(); i++) {
                if ((i % 50) == 0) {
                    wr.write("\r\n");
                }

                wr.write("" + norm.get(i) + "   ");

            }
            wr.close();
        } catch (Exception ex) {
            ex.toString();
        }

    }

    public Properties getProperties() {
        Properties pro = new Properties();

        try {
            String s = getServletContext().getRealPath("/WEB-INF");
            //OutputStream out = new FileOutputStream(s+"/article/config.properties");

            pro.setProperty("sentDetectionModel", s + "/models/sentDetection/VietnameseSD.bin.gz");
            pro.setProperty("lexiconDFA", s + "/models/tokenization/automata/dfaLexicon.xml");
            pro.setProperty("externalLexicon", s + "/models/tokenization/automata/externalLexicon.xml");
            pro.setProperty("normalizationRules", s + "/models/tokenization/normalization/rules.txt");
            pro.setProperty("lexers", s + "/models/tokenization/lexers/lexers.xml");
            pro.setProperty("unigramModel", s + "/models/tokenization/bigram/unigram.xml");
            pro.setProperty("bigramModel", s + "/models/tokenization/bigram/bigram.xml");
            pro.setProperty("namedEntityPrefix", s + "/models/tokenization/prefix/namedEntityPrefix.xml");

            //pro.store(out, null);
            //out.close();
        } catch (Exception e) {
            e.toString();
        }
        return pro;
    }

    public String[] getName(HashMap title) {
        System.out.println(title.size());
        String[] name = new String[title.size()];
        for (int i = 1; i <= title.size(); i++) {
            name[i - 1] = (String) title.get(i);
        }
        return name;
    }

    public void writeW(double[][] w, int t, String s) {
        PrintWriter wr = null;
        try {

            wr = new PrintWriter(s, "UTF-8");
            for (int i = 0; i < t; i++) {

                wr.write("\r\n");
                for (int j = 0; j < t; j++) {

                    wr.write("" + w[i][j] + "  ");
                }
            }
        } catch (Exception ex) {
            ex.toString();
        }

    }

}

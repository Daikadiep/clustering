/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Diep
 */
public class IOFile {

    public static void readArticle(HashMap title, HashMap article, String file) {
        int t = 1;
        while (true) {
            try {
                FileInputStream f = new FileInputStream(file + t + ".txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(f, "UTF-8"));
                String s = br.readLine();
                String s1 = br.readLine();
                if(s1==null) System.out.println(s+"  "+t);
                if (article.containsKey(s)) {
                    System.out.println(s);
                }

                article.put(s, s1);
                title.put(t, s);
                t++;

                br.close();
            } catch (Exception ex) {
                System.out.println(ex.toString());
                break;

            }
        }
    }

    public static void writeArticle(HashMap<String, String> article) {
        int i = 1;
        for (Map.Entry entry : article.entrySet()) {

            try {
                if (i <= article.size()) {

                    PrintWriter ar = new PrintWriter("D:\\DA\\Clustering\\article\\" + i + ".txt", "UTF-8");

                    String s = (String) entry.getKey();
                    String s2 = (String) entry.getValue();
                    ar.write(s);
                    ar.write("\r\n");
                    ar.write(s2);
                    i++;
                    ar.close();
                }
            } catch (Exception e) {
                e.toString();
            }
        }
    }

    public static HashMap readDictionaryD(String file) {
        HashMap hash = new HashMap();
        try {
            FileInputStream f = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(f, "UTF-8"));
            String s;
            int t = 1;
            while ((s = br.readLine()) != null) {
                hash.put(t, s);
                t++;
            }

            br.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());

        }
        return hash;
    }

    public static HashMap readDictionaryV(String file) {
        HashMap hash = new HashMap();
        try {
            FileInputStream f = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(f, "UTF-8"));
            String s;
            int t = 1;
            while ((s = br.readLine()) != null) {
                hash.put(s, t);
                t++;
            }

            br.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());

        }
        return hash;
    }

    public static void writeDictionary(String file, HashMap<Integer, String> hash) {
        try {
            PrintWriter dictionary = new PrintWriter(file, "UTF-8");
            for (Map.Entry word : hash.entrySet()) {
                dictionary.write((String) word.getValue());
                dictionary.write("\r\n");
            }
            dictionary.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public static void writeVector(ArrayList<HashMap<Integer, Word>> word, HashMap<Integer, String> title,
            String file) {
        try {
            int t = 1;
            PrintWriter vector = new PrintWriter(file + "/vector.txt", "UTF-8");
            for (t = 0; t < title.size(); t++) {
                vector.write((String) title.get(t + 1));

                vector.write("|");
                for (Map.Entry<Integer, Word> s : word.get(t).entrySet()) {
                    vector.write(s.getKey() + ":");

                    vector.write(s.getValue().getTf() + " ");

                }

                vector.write("\r\n");
            }
            vector.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}

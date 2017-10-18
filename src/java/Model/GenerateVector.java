/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import vn.hus.nlp.tokenizer.VietTokenizer;

/**
 *
 * @author Diep
 */
public class GenerateVector {

    public static void generateVector(ArrayList<HashMap<Integer, Word>> words, HashMap titles,
            HashMap dictionary,HashMap<Integer,Integer> word11,Properties pro) {
        
        VietTokenizer viet = new VietTokenizer(pro);

        for (int i = 1; i <= titles.size(); i++) {
            try {
                String title = (String) titles.get(i);
                
                String s = viet.segment(title);

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
                
                String[] word1 = s.split("\\s");

                HashMap wline = new HashMap();
                HashMap tf1 = new HashMap();
                int t = 1;
                for (int j = 0; j < word1.length; j++) {
                    String w = word1[j];
                    
                    w = w.replaceAll("_", " ");
                    w = w.trim();
                    w = w.replaceAll("﻿", "");
                    if (dictionary.containsKey(w) == true && wline.containsKey(w) == false) {
                        if (word11.containsKey(dictionary.get(w)) == true) {
                            word11.put((Integer) dictionary.get(w), word11.get((Integer) dictionary.get(w)) + 1);
                        } else {
                            word11.put((Integer) dictionary.get(w), 1);

                        }
                        int x = 1;
                        for (int jj = j + 1; jj < word1.length; jj++) {
                            if (word1[j].equals(word1[jj])) {
                                x++;
                            }
                        }
                        
                        Word w1 = new Word(w, x);
                        tf1.put(dictionary.get(w), w1);
                        t++;
                        wline.put(w, "");
                    }

                }
                for (int k = 0; k < word1.length - 1; k++) {

                    String s3 = word1[k] + " " + word1[k + 1];
                    s3 = s3.replaceAll("_", " ");
                    s3 = s3.trim();
                    s3 = s3.replaceAll("﻿", "");
                    
                    if (dictionary.containsKey(s3) == true && wline.containsKey(s3) == false) {

                        if (word11.containsKey(dictionary.get(s3)) == true) {
                            word11.put((Integer) dictionary.get(s3), word11.get((Integer) dictionary.get(s3)) + 1);
                        } else {
                            word11.put((Integer) dictionary.get(s3), 1);

                        }
                        int tt = 1;
                        for (int kk = k + 2; kk < word1.length - 1; kk++) {
                            if ((word1[k] + " " + word1[k + 1]).equals(word1[kk] + " " + word1[kk + 1])) {
                                tt++;
                            }
                        }
                        
                        Word w1 = new Word(s3, tt);
                        tf1.put(dictionary.get(s3), w1);
                        t++;
                        wline.put(s3, "");

                    }

                }

                words.add(tf1);

            } catch (Exception e) {
                e.toString();
            }
        }

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.Double.NaN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diep
 */
public class CosineSimilarity {
    
    
    
    public static double[][] calculateCosine(ArrayList<HashMap<Integer, Double>> W, ArrayList<Double> norm) {
       
        double[][] cosine = new double[W.size()][W.size()];
        
            for (int i = 0; i < W.size() - 1; i++) {
                cosine[i][i] = 0;
                
                for (int j = i + 1; j < W.size(); j++) {
                    double ts = 0;
                    for (Map.Entry<Integer, Double> en : W.get(i).entrySet()) {
                        if (W.get(j).containsKey(en.getKey())) {
                            ts = ts + en.getValue() * W.get(j).get(en.getKey());
                        }
                    }
                    cosine[i][j] = ts / (norm.get(i) * norm.get(j));
                    cosine[j][i] = cosine[i][j];
                    
                }
            }   
         
        return cosine;
    }
    
    public static ArrayList<Double> calculateNorm(ArrayList<HashMap<Integer, Double>> W) {
        ArrayList<Double> norm = new ArrayList<>();
        for (int i = 0; i < W.size(); i++) {
            double calNorm = 0;
            for (Map.Entry<Integer, Double> en : W.get(i).entrySet()) {
                calNorm = calNorm + Math.pow(en.getValue(), 2);
            }
            norm.add(Math.sqrt(calNorm));
            
        }
        return norm;
    }
    
    public static ArrayList<HashMap<Integer, Double>> TfDf(ArrayList<HashMap<Integer, Word>> word, HashMap df) {
        ArrayList<HashMap<Integer, Double>> W = new ArrayList<>();
        for (int i = 0; i < word.size(); i++) {
            HashMap<Integer, Double> ww = new HashMap<>();
            for (Map.Entry<Integer, Word> en : word.get(i).entrySet()) {
                ww.put(en.getKey(), en.getValue().getTf() * ((Math.log10((double) word.size() / (Integer) df.get(en.getKey())))/Math.log10(2.0)));
            }
            W.add(ww);
        }
        
        return W;
    }
    
}

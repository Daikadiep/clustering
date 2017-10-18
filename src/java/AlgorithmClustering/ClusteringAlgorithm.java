/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgorithmClustering;

import java.util.List;

/**
 *
 * @author Diep
 */
public interface ClusteringAlgorithm {

    public Cluster performClustering(double[][] distances, String[] clusterNames,
                                     LinkageStrategy linkageStrategy);

    public Cluster performWeightedClustering(double[][] distances, String[] clusterNames,
                                             double[] weights, LinkageStrategy linkageStrategy);

    public List<Cluster> performFlatClustering(double[][] distances,
                                               String[] clusterNames, LinkageStrategy linkageStrategy, Double threshold);
}

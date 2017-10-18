/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgorithmClustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Diep
 */
public class DefaultClusteringAlgorithm implements ClusteringAlgorithm
{

    @Override
    public Cluster performClustering(double[][] distances,
                                     String[] clusterNames, LinkageStrategy linkageStrategy)
    {

        checkArguments(distances, clusterNames, linkageStrategy);
    /* Setup model */
        List<Cluster> clusters = createClusters(clusterNames);
        DistanceMap linkages = createLinkages(distances, clusters);

    /* Process */
        HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
        while (!builder.isTreeComplete())
        {
            builder.agglomerate(linkageStrategy);
        }

        return builder.getRootCluster();
    }

    @Override
    public List<Cluster> performFlatClustering(double[][] distances,
                                               String[] clusterNames, LinkageStrategy linkageStrategy, Double threshold)
    {

        checkArguments(distances, clusterNames, linkageStrategy);
    /* Setup model */
        List<Cluster> clusters = createClusters(clusterNames);
        DistanceMap linkages = createLinkages(distances, clusters);

    /* Process */
        HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
        return builder.flatAgg(linkageStrategy, threshold);
    }

    private void checkArguments(double[][] distances, String[] clusterNames,
                                LinkageStrategy linkageStrategy)
    {
        if (distances == null || distances.length == 0
                || distances[0].length != distances.length)
        {
            throw new IllegalArgumentException("Invalid distance matrix");
        }
        if (distances.length != clusterNames.length)
        {
            throw new IllegalArgumentException("Invalid cluster name array");
        }
        if (linkageStrategy == null)
        {
            throw new IllegalArgumentException("Undefined linkage strategy");
        }
        int uniqueCount = new HashSet<String>(Arrays.asList(clusterNames)).size();
        System.out.println(uniqueCount+"   "+clusterNames.length+" "+Arrays.asList(clusterNames).size());
        if (uniqueCount != clusterNames.length)
        {
            throw new IllegalArgumentException("Duplicate names");
        }
    }

    @Override
    public Cluster performWeightedClustering(double[][] distances, String[] clusterNames,
                                             double[] weights, LinkageStrategy linkageStrategy)
    {

        checkArguments(distances, clusterNames, linkageStrategy);

        if (weights.length != clusterNames.length)
        {
            throw new IllegalArgumentException("Invalid weights array");
        }

    /* Setup model */
        List<Cluster> clusters = createClusters(clusterNames, weights);
        DistanceMap linkages = createLinkages(distances, clusters);

    /* Process */
        HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
        while (!builder.isTreeComplete())
        {
            builder.agglomerate(linkageStrategy);
        }

        return builder.getRootCluster();
    }

    private DistanceMap createLinkages(double[][] distances,
                                       List<Cluster> clusters)
    {
        DistanceMap linkages = new DistanceMap();
        for (int col = 0; col < clusters.size(); col++)
        {
            for (int row = col + 1; row < clusters.size(); row++)
            {
                ClusterPair link = new ClusterPair();
                Cluster lCluster = clusters.get(col);
                Cluster rCluster = clusters.get(row);
                link.setLinkageDistance(distances[col][row]);
                link.setlCluster(lCluster);
                link.setrCluster(rCluster);
                linkages.add(link);
            }
        }
        return linkages;
    }

    private List<Cluster> createClusters(String[] clusterNames)
    {
        List<Cluster> clusters = new ArrayList<Cluster>();
        for (String clusterName : clusterNames)
        {
            Cluster cluster = new Cluster(clusterName);
            clusters.add(cluster);
        }
        return clusters;
    }

    private List<Cluster> createClusters(String[] clusterNames, double[] weights)
    {
        List<Cluster> clusters = new ArrayList<Cluster>();
        for (int i = 0; i < weights.length; i++)
        {
            Cluster cluster = new Cluster(clusterNames[i]);
            cluster.setDistance(new Distance(0.0, weights[i]));
            clusters.add(cluster);
        }
        return clusters;
    }

}


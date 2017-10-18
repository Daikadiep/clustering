/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgorithmClustering;

import java.util.Collection;

/**
 *
 * @author Diep
 */
public interface LinkageStrategy {
    public Distance calculateDistance(Collection<Distance> distances);
}

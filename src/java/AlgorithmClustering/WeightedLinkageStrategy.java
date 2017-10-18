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
public class WeightedLinkageStrategy implements LinkageStrategy {

    @Override
    public Distance calculateDistance(Collection<Distance> distances) {
        double sum = 0;
        double weightTotal = 0;
        for (Distance distance : distances) {
            weightTotal += distance.getWeight();
            sum += distance.getDistance() * distance.getWeight();
        }

		return new Distance(sum / weightTotal, weightTotal);
    }
}


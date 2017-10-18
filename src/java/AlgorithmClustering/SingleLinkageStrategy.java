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
public class SingleLinkageStrategy implements LinkageStrategy {

	@Override
	public Distance calculateDistance(Collection<Distance> distances) {
		double min = Double.NaN;

		for (Distance dist : distances) {
		    if (Double.isNaN(min) || dist.getDistance() < min)
		        min = dist.getDistance();
		}
		return new Distance(min);
	}
}
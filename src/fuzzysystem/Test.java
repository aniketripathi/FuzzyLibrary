package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



public class Test {
	
	public static void main(String[] args) throws MembershipOutOfRangeException {
		
		LinearFuzzySet lset = new LinearFuzzySet(0, 0, 10, 1);
		TriangularFuzzySet gset = new TriangularFuzzySet(0, 10, 20);
		System.out.println(lset.getArea() + " " + lset.getWeightedMean());
		System.out.println(gset.getArea() + " " + gset.getWeightedMean());
		System.out.println(FuzzyUtility.defuzzify(FuzzyUtility.Defuzzification.CENTROID, gset, lset));
		
	}
}

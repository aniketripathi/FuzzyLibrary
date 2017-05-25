package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



public class Test {
	
	public static void main(String[] args) throws MembershipOutOfRangeException {
		
		SShapedFuzzySet lset = new SShapedFuzzySet(0, 5);
		System.out.println(lset.getArea());
		// System.out.println(lset.getWeightedMean() + "," + lset.getMembershipValue(lset.getWeightedMean()));
		System.out.println(lset.maxMembershipAt() + " " + lset.getMembershipValue(lset.maxMembershipAt()));
		
	}
}

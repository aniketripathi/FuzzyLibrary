package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



public class Test {
	
	public static void main(String[] args) throws MembershipOutOfRangeException {
		
		LinearFuzzySet lset = new LinearFuzzySet(0,0.5,5,1);
		System.out.println(lset.getArea());
		System.out.println(lset.getWeightedMean() + "," + lset.getMembershipValue(lset.getWeightedMean()));
		System.out.println(lset.maxMembershipAt() + " " + lset.getMembershipValue(lset.maxMembershipAt()));
		
	}
}

package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;
import fuzzysystem.exceptions.MultipleMembershipException;



public class Test {
	
	public static void main(String[] args) throws MembershipOutOfRangeException, MultipleMembershipException {

		
		TriangularFuzzySet lset = new TriangularFuzzySet(0, 7, 10);
		System.out.println(lset.getArea()+ ","+ lset.getLinearFuzzySet1().getArea() + "," + lset.getLinearFuzzySet2().getArea());
		System.out.println(lset.getWeightedMean() + "," + lset.getMembershipValue(lset.getWeightedMean()));
		System.out.println(lset.maxMembershipAt() + " " + lset.getMembershipValue(lset.maxMembershipAt()));
		
	}
}

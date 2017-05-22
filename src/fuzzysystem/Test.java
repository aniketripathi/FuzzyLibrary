package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;
import fuzzysystem.exceptions.MultipleMembershipException;



public class Test {
	
	public static void main(String[] args) throws MembershipOutOfRangeException, MultipleMembershipException {
		
		GaussianFuzzySet gset = new GaussianFuzzySet(2, 5);
		System.out.println(gset.getMembershipValue(0));
		
	}
}

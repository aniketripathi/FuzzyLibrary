package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;
import fuzzysystem.exceptions.MultipleMembershipException;



public class Test {
	
	public static void main(String[] args) throws MembershipOutOfRangeException, MultipleMembershipException {
		
		TriangularFuzzySet fset = new TriangularFuzzySet(10, 49.9999999999999999999, 50);
		System.out.print(fset.getMembershipValue(49.9999999999999999999999));
	}
}

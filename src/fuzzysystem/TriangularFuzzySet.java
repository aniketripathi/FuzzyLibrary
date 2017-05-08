package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;
import fuzzysystem.exceptions.MultipleMembershipException;



public class TriangularFuzzySet extends AbstractFuzzySet {
	
	// y = (yUpper - yLower)/(xMiddle - xLower) * (x - xLower) + yLower // eq 1
	// y = (yUpper - yLower)/(xMiddle - xUpper) * (x - xUpper) + yLower // eq 2
	
	private final LinearFuzzySet	lset1;
	private final LinearFuzzySet	lset2;
	
	
	
	public TriangularFuzzySet(double xLower, double xMiddle, double xUpper, double yLower, double yUpper)
			throws MembershipOutOfRangeException, MultipleMembershipException {
		
		lset1 = new LinearFuzzySet(xLower, xMiddle, yLower, yUpper);
		lset2 = new LinearFuzzySet(xMiddle, xUpper, yLower, yUpper);
	}
	
	
	
	public TriangularFuzzySet(double xLower, double xMiddle, double xUpper, double yUpper)
			throws MembershipOutOfRangeException, MultipleMembershipException {
		
		lset1 = new LinearFuzzySet(xLower, xMiddle, yUpper);
		lset2 = new LinearFuzzySet(xMiddle, xUpper, yUpper);
	}
	
	
	
	public TriangularFuzzySet(double xLower, double xMiddle, double xUpper) throws MultipleMembershipException {
		
		lset1 = new LinearFuzzySet(xLower, xMiddle);
		lset2 = new LinearFuzzySet(xMiddle, xUpper);
	}
	
	
	
	@Override
	public double getMembershipValue(double xValue) {
		
		if (xValue < lset1.getXLower() || xValue > lset2.getXUpper())
			return 0;
		
		if (xValue >= lset1.getXLower() && xValue < lset1.getXUpper())
			return lset1.getMembershipValue(xValue);
		
		return lset2.getMembershipValue(xValue);
	}
	
	
	
	@Override
	public double getMembershipValue(Element element) {
		
		return getMembershipValue(element.getValue());
		
	}
	
	
	
	public Singleton getSingleton(Element element) throws MembershipOutOfRangeException {
		
		return new Singleton(element, getMembershipValue(element));
		
	}
	
	
	
	public void shiftUp(double shiftBy) throws MembershipOutOfRangeException {
		
		lset1.shiftUp(shiftBy);
		lset2.shiftUp(shiftBy);
	}
	
	
	
	public void shiftDown(double shiftBy) throws MembershipOutOfRangeException {
		
		lset1.shiftDown(shiftBy);
		lset2.shiftDown(shiftBy);
		
	}
	
	
	
	public void shiftLeft(double shiftBy) {
		
		lset1.shiftLeft(shiftBy);
		lset2.shiftLeft(shiftBy);
	}
	
	
	
	public void shiftRight(double shiftBy) {
		
		lset1.shiftRightt(shiftBy);
		lset2.shiftRightt(shiftBy);
	}
	
	
	
	public LinearFuzzySet getLinearFuzzySet1() {
		
		return lset1;
	}
	
	
	
	public LinearFuzzySet getLinearFuzzySet2() {
		
		return lset2;
	}
	
}

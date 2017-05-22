package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;
import fuzzysystem.exceptions.MultipleMembershipException;



public class TrapezoidalFuzzySet extends AbstractFuzzySet {
	
	private LinearFuzzySet lset1, lset2;
	
	
	
	public TrapezoidalFuzzySet(double xLower, double yLower, double xMiddle1, double xMiddle2, double yMiddle, double xUpper)
			throws MembershipOutOfRangeException, MultipleMembershipException {
		lset1 = new LinearFuzzySet(xLower, yLower, xMiddle1, yMiddle);
		lset2 = new LinearFuzzySet(xMiddle2, yMiddle, xUpper, yLower);
		
	}
	
	
	
	public TrapezoidalFuzzySet(double xLower, double xMiddle1, double xMiddle2, double xUpper) throws MultipleMembershipException {
		lset1 = new LinearFuzzySet(xLower, xMiddle1);
		
		try {
			
			lset2 = new LinearFuzzySet(xMiddle2, 1.0, xUpper, 0.0);
		}
		catch (MembershipOutOfRangeException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public TrapezoidalFuzzySet(double xLower, double xMiddle1, double xMiddle2, double yMiddle, double xUpper)
			throws MembershipOutOfRangeException, MultipleMembershipException {
		
		this(xLower, 0.0, xMiddle1, xMiddle2, yMiddle, xUpper);
	}
	
	
	
	@Override
	public double getMembershipValue(double xValue) {
		
		if (xValue < lset1.getXLower() && xValue > lset2.getXUpper())
			return 0;
		
		if (xValue >= lset1.getXLower() && xValue <= lset1.getXUpper())
			return lset1.getMembershipValue(xValue);
		
		if (xValue > lset1.getXUpper() && xValue < lset2.getXLower())
			return lset1.getYUpper();
		
		else
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
	
	
	
	public void shiftRight(double shiftBy) {
		
		lset1.shiftRightt(shiftBy);
		lset2.shiftRightt(shiftBy);
		
	}
	
	
	
	public void shiftLeft(double shiftBy) {
		
		lset1.shiftLeft(shiftBy);
		lset2.shiftLeft(shiftBy);
	}
	
	
	
	public LinearFuzzySet getLinearFuzzySet1() {
		
		return lset1;
	}
	
	
	
	public LinearFuzzySet getLinearFuzzySet2() {
		
		return lset2;
	}
	
}

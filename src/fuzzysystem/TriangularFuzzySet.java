package fuzzysystem;

import fuzzysystem.exceptions.InvalidShapeException;
import fuzzysystem.exceptions.MembershipOutOfRangeException;



public class TriangularFuzzySet extends AbstractFuzzySet {
	
	// y = (yUpper - yLower)/(xMiddle - xLower) * (x - xLower) + yLower // eq 1
	// y = (yUpper - yLower)/(xMiddle - xUpper) * (x - xUpper) + yLower // eq 2
	
	private final LinearFuzzySet	lset1;
	private final LinearFuzzySet	lset2;
	
	
	
	public TriangularFuzzySet(double xLower, double yLower, double xMiddle, double yMiddle, double xUpper)
			throws MembershipOutOfRangeException, InvalidShapeException {
		
		if (xLower >= xMiddle || xMiddle >= xUpper)
			throw new InvalidShapeException("Triangle", "Unknown");
		
		lset1 = new LinearFuzzySet(xLower, yLower, xMiddle, yMiddle);
		lset2 = new LinearFuzzySet(xMiddle, yMiddle, xUpper, yLower);
	}
	
	
	
	public TriangularFuzzySet(double xLower, double xMiddle, double yMiddle, double xUpper)
			throws MembershipOutOfRangeException, InvalidShapeException {
		
		this(xLower, 0.0, xMiddle, yMiddle, xUpper);
	}
	
	
	
	public TriangularFuzzySet(double xLower, double xMiddle, double xUpper) throws InvalidShapeException {
		
		if (xLower >= xMiddle || xMiddle >= xUpper)
			throw new InvalidShapeException("Triangle", "Unkown");
		
		lset1 = new LinearFuzzySet(xLower, xMiddle);
		
		LinearFuzzySet temp;
		
		try {
			temp = new LinearFuzzySet(xMiddle, 1.0, xUpper, 0.0);
		}
		catch (MembershipOutOfRangeException e) {
			temp = null;
			e.printStackTrace();
		}
		
		lset2 = temp;
	}
	
	
	
	@Override
	public double getMembershipValue(double xValue) {
		
		double yValue;
		
		if (xValue < lset1.getXLower() || xValue > lset2.getXUpper())
			yValue = 0;
		
		else if (xValue >= lset1.getXLower() && xValue < lset1.getXUpper())
			yValue = lset1.getMembershipValue(xValue);
		
		else
			yValue = lset2.getMembershipValue(xValue);
		
		return yValue;
	}
	
	
	
	@Override
	public double getArea() {
		
		return lset1.getArea() + lset2.getArea();
	}
	
	
	
	@Override
	public double maxMembershipAt() {
		
		return Math.max(lset1.getYLower(), lset1.getYUpper());
	}
	
	
	
	@Override
	public double getWeightedMean() {
		
		double x1 = lset1.getWeightedMean(), x2 = lset2.getWeightedMean(),
				w1 = lset1.getMembershipValue(x1), w2 = lset2.getMembershipValue(x2);
		
		return (x1 * w1 + x2 * w2) / (w1 + w2);
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

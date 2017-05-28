package fuzzysystem;

import fuzzysystem.exceptions.InvalidShapeException;
import fuzzysystem.exceptions.MembershipOutOfRangeException;



public class TrapezoidalFuzzySet extends AbstractFuzzySet {
	
	private LinearFuzzySet lset1, lset2;
	
	
	
	public TrapezoidalFuzzySet(double xLower, double yLower, double xMiddle1, double xMiddle2, double yMiddle, double xUpper)
			throws MembershipOutOfRangeException, InvalidShapeException {
		
		if (xLower >= xMiddle1 || xMiddle2 >= xUpper)
			throw new InvalidShapeException("Trapezoid", "Unknown");
		
		double m1 = (yMiddle - yLower) / (xMiddle1 - xLower),
				m2 = (yMiddle - yLower) / (xMiddle2 - xUpper);
		
		if (m1 * m2 > 0)
			throw new InvalidShapeException("Trapezoid", "Parallelogram");
		
		if (xMiddle1 >= xMiddle2)
			throw new InvalidShapeException("Trapezoid", "Triangle");
		
		lset1 = new LinearFuzzySet(xLower, yLower, xMiddle1, yMiddle);
		lset2 = new LinearFuzzySet(xMiddle2, yMiddle, xUpper, yLower);
		
	}
	
	
	
	public TrapezoidalFuzzySet(double xLower, double xMiddle1, double xMiddle2, double xUpper) throws InvalidShapeException {
		
		if (xLower >= xMiddle1 || xMiddle2 >= xUpper)
			throw new InvalidShapeException("Trapezoid", "Unknown");
		
		double m1 = 1.0 / (xMiddle1 - xLower),
				m2 = 1.0 / (xMiddle2 - xUpper);
		
		if (m1 * m2 > 0)
			throw new InvalidShapeException("Trapezoid", "Parallelogram");
		
		if (xMiddle1 >= xMiddle2)
			throw new InvalidShapeException("Trapezoid", "Triangle");
		
		lset1 = new LinearFuzzySet(xLower, xMiddle1);
		
		try {
			lset2 = new LinearFuzzySet(xMiddle2, 1.0, xUpper, 0.0);
		}
		catch (MembershipOutOfRangeException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public TrapezoidalFuzzySet(double xLower, double xMiddle1, double xMiddle2, double yMiddle, double xUpper)
			throws MembershipOutOfRangeException, InvalidShapeException {
		
		this(xLower, 0.0, xMiddle1, xMiddle2, yMiddle, xUpper);
	}
	
	
	
	@Override
	public double getMembershipValue(double xValue) {
		
		double yValue;
		
		if (xValue < lset1.getXLower() && xValue > lset2.getXUpper())
			yValue = 0;
		
		if (xValue >= lset1.getXLower() && xValue <= lset1.getXUpper())
			yValue = lset1.getMembershipValue(xValue);
		
		if (xValue > lset1.getXUpper() && xValue < lset2.getXLower())
			yValue = lset1.getYUpper();
		
		else
			yValue = lset2.getMembershipValue(xValue);
		
		return yValue;
		
	}
	
	
	
	@Override
	public double maxMembershipAt() {
		
		return (lset1.maxMembershipAt() + lset2.maxMembershipAt())/2.0;
	}
	
	
	
	@Override
	public double getWeightedMean() {
		
		double x1 = lset1.getWeightedMean(),
				x2 = lset2.getWeightedMean(),
				x3 = (lset1.getXUpper() + lset2.getXLower()) / 2,
				w1 = lset1.getMembershipValue(x1),
				w2 = lset2.getMembershipValue(x2),
				w3 = lset1.getYUpper();
		
		return (x1 * w1 + x2 * w2 + x3 * w3) / (w1 + w2 + w3);
	}
	
	
	
	@Override
	public double getArea() {
		
		double area = lset1.getArea() + lset2.getArea();
				
		return area + lset1.getYUpper() * (lset2.getXLower() - lset1.getXUpper());
		
		
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

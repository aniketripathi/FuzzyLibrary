package fuzzysystem;

import fuzzysystem.exceptions.InvalidShapeException;
import fuzzysystem.exceptions.MembershipOutOfRangeException;



public class SShapedFuzzySet extends AbstractFuzzySet {
	
	double xLower, yLower, xUpper, yUpper;
	
	
	
	public SShapedFuzzySet(double xLower, double yLower, double xUpper, double yUpper) throws MembershipOutOfRangeException, InvalidShapeException {
		
		if (xLower >= xUpper)
			throw new InvalidShapeException("S shape", "Unknown");
		
		this.xLower = xLower;
		this.xUpper = xUpper;
		
		if (outOfRange(yLower) || outOfRange(yUpper))
			throw new MembershipOutOfRangeException();
		
		this.yLower = yLower;
		this.yUpper = yUpper;
		
	}
	
	
	
	public SShapedFuzzySet(double xLower, double xUpper) throws InvalidShapeException {
		
		if (xLower >= xUpper)
			throw new InvalidShapeException("S shape", "Unknown");
		
		this.xLower = xLower;
		this.yLower = 0;
		this.xUpper = xUpper;
		this.yUpper = 1;
	}
	
	
	
	@Override
	public double getMembershipValue(double xValue) {
		
		double yValue;
		final double factor = getFactor();
		
		if (xValue <= xLower)
			yValue = yLower;
		
		else if (xValue <= (xLower + xUpper) / 2.0)
			yValue = yLower + factor * Math.pow((xValue - xLower) / (xUpper - xLower), 2);
		
		else if (xValue <= xUpper)
			yValue = yUpper - factor * Math.pow((xValue - xUpper) / (xUpper - xLower), 2);
		
		else
			yValue = yUpper;
		
		return yValue;
	}
	
	
	
	@Override
	public double maxMembershipAt() {
		
		return xUpper;
	}
	
	
	
	@Override
	public double getArea() {
		
		double area = (yUpper + yLower) * (xUpper - xLower) / 2.0;
		
		return area + (xUpper - xLower) * Math.min(yUpper, yLower);
	}
	
	
	
	public double getFactor() {
		
		return (yUpper - yLower) * 2.0;
	}
	
	
	
	// formula
	// ( y0(3a+b) + y1(3b+a) - k(b-a)/12 )/ 4(y0 + y1)
	@Override
	public double getWeightedMean() {
		
		double y0 = yLower, y1 = yUpper,
				a = xLower, b = xUpper, k = getFactor();
		
		return (y0 * (3.0 * a + b) + y1 * (3.0 * b + a) - k * (b - a) / 12.0) /
				(y0 + y1) / 4.0;
		
	}
	
	
	
	public void shiftUp(double shiftBy) throws MembershipOutOfRangeException {
		
		if (outOfRange(yLower + shiftBy) || outOfRange(yUpper + shiftBy))
			throw new MembershipOutOfRangeException();
		
		this.yUpper += shiftBy;
		this.yLower += shiftBy;
	}
	
	
	
	public void shiftDown(double shiftBy) throws MembershipOutOfRangeException {
		
		if (outOfRange(yLower - shiftBy) || outOfRange(yUpper - shiftBy))
			throw new MembershipOutOfRangeException();
		
		this.yUpper -= shiftBy;
		this.yLower -= shiftBy;
	}
	
	
	
	public void shiftLeft(double shiftBy) {
		
		this.xLower -= shiftBy;
		this.xUpper -= shiftBy;
	}
	
	
	
	public void shiftRight(double shiftBy) {
		
		this.xLower += shiftBy;
		this.xUpper += shiftBy;
	}
	
	
	
	public double getxLower() {
		
		return xLower;
	}
	
	
	
	public double getyLower() {
		
		return yLower;
	}
	
	
	
	public double getxUpper() {
		
		return xUpper;
	}
	
	
	
	public double getyUpper() {
		
		return yUpper;
	}
	
}

package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



public class SShapedFuzzySet extends AbstractFuzzySet {
	
	double xLower, yLower, xUpper, yUpper;
	
	
	
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
	
	
	
	public SShapedFuzzySet(double xLower, double yLower, double xUpper, double yUpper) throws MembershipOutOfRangeException {
		
		this.xLower = xLower;
		this.xUpper = xUpper;
		
		if (outOfRange(yLower) || outOfRange(yUpper))
			throw new MembershipOutOfRangeException();
		
		this.yLower = yLower;
		this.yUpper = yUpper;
		
	}
	
	
	
	public SShapedFuzzySet(double xLower, double xUpper) {
		this.xLower = xLower;
		this.yLower = 0;
		this.xUpper = xUpper;
		this.yUpper = 1;
	}
	
	
	
	@Override
	public double getMembershipValue(double xValue) {
		
		double yValue;
		
		if (xValue <= xLower)
			yValue = yLower;
		
		else if (xValue <= (xLower + xUpper) / 2)
			yValue = 2 * Math.pow((xValue - xLower) / (xUpper - xLower), 2);
		
		else if (xValue <= xUpper)
			yValue = yUpper - 2 * Math.pow((xValue - xLower) / (xUpper - xLower), 2);
		
		else
			yValue = yUpper;
		
		return yValue;
	}
	
	
	
	public Singleton getSingleton(Element element) throws MembershipOutOfRangeException {
		
		return new Singleton(element, getMembershipValue(element));
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
	
}

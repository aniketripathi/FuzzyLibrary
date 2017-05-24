package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



public class ZShapedFuzzySet extends SShapedFuzzySet {
	
	public ZShapedFuzzySet(double xLower, double yLower, double xUpper, double yUpper) throws MembershipOutOfRangeException {
		super(xLower, yLower, xUpper, yUpper);
		
	}
	
	
	
	public ZShapedFuzzySet(double xLower, double xUpper) {
		super(xLower, xUpper);
	}
	
	
	
	@Override
	public double getMembershipValue(double xValue) {
		
		double yValue;
		
		if (xValue <= xLower)
			yValue = yLower;
		
		else if (xValue <= (xLower + xUpper) / 2)
			yValue = 1 - 2 * Math.pow((xValue - xLower) / (xUpper - xLower), 2);
		
		else if (xValue <= xUpper)
			yValue = 2 * Math.pow((xValue - xLower) / (xUpper - xLower), 2);
		
		yValue = yUpper;
		
		return yValue;
	}
}

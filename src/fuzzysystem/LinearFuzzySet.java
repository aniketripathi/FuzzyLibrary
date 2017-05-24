package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;
import fuzzysystem.exceptions.MultipleMembershipException;



public class LinearFuzzySet extends AbstractFuzzySet {
	
	private double xLower, xUpper, yLower, yUpper;
	
	// y = (yUpper - yLower)/(xUpper - xLower) * (xValue - xLower) + yLower
	
	
	
	public LinearFuzzySet(double xLower, double yLower, double xUpper, double yUpper)
			throws MembershipOutOfRangeException, MultipleMembershipException {
		this.xLower = xLower;
		this.xUpper = xUpper;
		
		if (xLower == xUpper)
			throw new MultipleMembershipException();
		
		if (outOfRange(yLower) || outOfRange(yUpper))
			throw new MembershipOutOfRangeException();
		
		this.yLower = yLower;
		this.yUpper = yUpper;
		
	}
	
	
	
	public LinearFuzzySet(double xLower, double xUpper, double yUpper) throws MembershipOutOfRangeException, MultipleMembershipException {
		this.xLower = xLower;
		this.xUpper = xUpper;
		
		if (xLower == xUpper)
			throw new MultipleMembershipException();
		
		if (outOfRange(yUpper))
			throw new MembershipOutOfRangeException();
		
		this.yLower = 0;
		this.yUpper = yUpper;
		
	}
	
	
	
	public LinearFuzzySet(double xLower, double xUpper) throws MultipleMembershipException {
		this.xLower = xLower;
		this.xUpper = xUpper;
		
		if (xLower == xUpper)
			throw new MultipleMembershipException();
		
		this.yLower = 0;
		this.yUpper = 1;
		
	}
	
	
	
	@Override
	public double getMembershipValue(double xValue) {
		
		return (xValue < xLower || xValue > xUpper)
				? 0
				: (yUpper - yLower) / (xUpper - xLower) * (xValue - xLower) + yLower;
	}
	
	
	
	public Singleton getSingleton(Element element) throws MembershipOutOfRangeException {
		
		return new Singleton(element, getMembershipValue(element));
	}
	
	
	
	public void shiftUp(double shiftBy) throws MembershipOutOfRangeException {
		
		if (outOfRange(yLower + shiftBy) || outOfRange(yUpper + shiftBy))
			throw new MembershipOutOfRangeException();
		
		yLower += shiftBy;
		yUpper += shiftBy;
		
	}
	
	
	
	public void shiftDown(double shiftBy) throws MembershipOutOfRangeException {
		
		if (outOfRange(yLower - shiftBy) || outOfRange(yUpper - shiftBy))
			throw new MembershipOutOfRangeException();
		
		yLower -= shiftBy;
		yUpper -= shiftBy;
		
	}
	
	
	
	public void shiftRightt(double shiftBy) {
		
		xLower += shiftBy;
		xUpper += shiftBy;
		
	}
	
	
	
	public void shiftLeft(double shiftBy) {
		
		xLower -= shiftBy;
		xUpper -= shiftBy;
		
	}
	
	
	public double getSlope(){
		return (yUpper - yLower)/(xUpper - xLower);
	}
	
	
	public double getWeightedMean(){
		/**   weighted mean (based on integration) = 
			2/3m[(x0+x1)^2 - x0x1] +  c[x0+x1] /  [ m(x0+x1) + 2c ]
			where m and c are slope and y intercept of the equation 
		**/
		double sum = xUpper + xLower, m = getSlope(), c = yUpper - m * xUpper;
		return ((2.0/3.0) * m * ( sum * sum - xUpper * xLower) + c * sum) /
				(m * sum + 2.0 * c);
		
	}
	
	
	public double maxMembershipAt(){
		
		return (yLower > yUpper)? xLower : xUpper;
	}
	
	
	
	public double getArea(){
		
		
		return (yUpper == yLower)
				? (Math.abs(xUpper - xLower) * yLower)		// sloper is zero
				: (0.5 * Math.abs(xUpper - xLower) * Math.abs(yUpper - yLower)); 		// non zero slope
	}
	
	public double getXLower() {
		
		return xLower;
	}
	
	
	
	public double getXUpper() {
		
		return xUpper;
	}
	
	
	
	public double getYLower() {
		
		return yLower;
	}
	
	
	
	public double getYUpper() {
		
		return yUpper;
	}
	
}

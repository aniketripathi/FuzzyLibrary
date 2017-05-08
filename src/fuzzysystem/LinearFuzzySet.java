package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;
import fuzzysystem.exceptions.MultipleMembershipException;



public class LinearFuzzySet extends AbstractFuzzySet {
	
	private double	xLower;
	private double	xUpper;
	private double	yLower;
	private double	yUpper;
	
	// y = (yUpper - yLower)/(xUpper - xLower) * (xValue - xLower) + yLower
	
	
	
	public LinearFuzzySet(double xLower, double xUpper, double yLower, double yUpper)
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
		
		if (xValue < xLower || xValue > xUpper)
			return 0;
		
		return ((yUpper - yLower) / (xUpper - xLower) * (xValue - xLower) + yLower);
	}
	
	
	
	@Override
	public double getMembershipValue(Element element) {
		
		return getMembershipValue(element.getValue());
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

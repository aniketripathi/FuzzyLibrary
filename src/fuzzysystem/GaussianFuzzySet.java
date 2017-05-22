package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;

public class GaussianFuzzySet extends AbstractFuzzySet{
	
		private double a, b;
	
	public GaussianFuzzySet(double a, double b) {
		this.a = a;
		this.b = b;
	
	}

	@Override
	public double getMembershipValue(double xValue) {
		
		return Math.exp(- (xValue - b) * (xValue - b)/ (2 * a * a));
	}

	@Override
	public double getMembershipValue(Element element) {
		
		return getMembershipValue(element.getValue());
	}
	
	
	public Singleton getSingleton(Element element) throws MembershipOutOfRangeException{
		
		return new Singleton(element, getMembershipValue(element));
	}
	
	
	
	
}

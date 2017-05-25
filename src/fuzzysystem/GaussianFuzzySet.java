package fuzzysystem;

import fuzzysystem.exceptions.InvalidShapeException;



public class GaussianFuzzySet extends AbstractFuzzySet {
	
	private double a, b;
	
	
	
	public GaussianFuzzySet(double a, double b) throws InvalidShapeException {
		
		if (a <= 0)
			throw new InvalidShapeException("Gaussian", "Unknown");
		
		this.a = a;
		this.b = b;
		
	}
	
	
	
	@Override
	public double getMembershipValue(double xValue) {
		
		return Math.exp(-(xValue - b) * (xValue - b) / (2 * a * a));
	}
	
	
	
	public double getWeightedMean() {
		
		return b;
	}
	
	
	
	public double maxMembershipAt() {
		
		return b;
	}
	
	
	
	public double getArea() {
		
		// Math.sqrt(Math.PI * 2) = 2.50662827463
		return 2.50662827463 * a;
	}
	
	
	
	public double getA() {
		
		return a;
	}
	
	
	
	public double getB() {
		
		return b;
	}
	
}

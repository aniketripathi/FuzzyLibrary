package fuzzysystem;

public abstract class AbstractFuzzySet {
	
	protected boolean outOfRange(double membership) {
		
		return (membership > 1 || membership < 0);
		
	}
	
	
	
	public abstract double getMembershipValue(double xValue);
	
	
	
	public double getMembershipValue(Element element) {
		
		return getMembershipValue(element.getValue());
	}
}

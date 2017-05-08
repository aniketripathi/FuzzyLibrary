package fuzzysystem;

public abstract class AbstractFuzzySet {
	
	protected boolean outOfRange(double membership) {
		
		if (membership > 1 || membership < 0)
			return true;
		
		return false;
	}
	
	
	
	public abstract double getMembershipValue(double xValue);
	
	
	
	public abstract double getMembershipValue(Element element);
}

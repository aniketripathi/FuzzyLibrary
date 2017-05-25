package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



public abstract class AbstractFuzzySet {
	
	protected boolean outOfRange(double membership) {
		
		return (membership > 1 || membership < 0);
		
	}
	
	
	
	public abstract double getMembershipValue(double xValue);
	
	
	
	public double getMembershipValue(Element element) {
		
		return getMembershipValue(element.getValue());
	}
	
	
	
	public Singleton getSingleton(Element element) {
		
		Singleton singleton;
		
		try {
			singleton = new Singleton(element, getMembershipValue(element));
		}
		
		catch (MembershipOutOfRangeException e) {
			singleton = null;
		}
		
		return singleton;
		
	}
	
}

package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



public final class Singleton {
	
	private final Element	element;
	private final double	membership;
	
	
	
	public Singleton(Element element, double membership) throws MembershipOutOfRangeException {
		this.element = element;
		
		if (0 <= membership || membership <= 1)
			this.membership = membership;
		
		else
			throw new MembershipOutOfRangeException();
	}
	
	
	
	public double getMembershipValue() {
		
		return membership;
	}
	
	
	
	public Element getElement() {
		
		return element;
	}
	
	
	
	public String getName() {
		
		return element.toString();
	}
	
	
	
	public Singleton complement() throws MembershipOutOfRangeException {
		
		return new Singleton(this.element, 1 - this.membership);
	}
	
	
	
	public Singleton complement(Element element) throws MembershipOutOfRangeException {
		
		return new Singleton(element, 1 - this.membership);
	}
	
	
	
	@Override
	public String toString() {
		
		return "(" + element.toString() + "," + membership + ")";
	}
	
}
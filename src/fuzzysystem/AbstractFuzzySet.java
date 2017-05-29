package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



/***
 * AbstractFuzzySet is an abstract class and encapsulates the membership functions. These functions are continuous and
 * they also behave
 * like set. Thus membership function and set can be used interchangeably
 * 
 * @author aniket
 *
 */
public abstract class AbstractFuzzySet {
	
	/**
	 * Determines whether the given membership value is valid or not. Membership value is only valid if it lies between
	 * [0, 1] inclusive.
	 * 
	 * @param membership
	 *            Membership value to check
	 * @return True if membership is valid otherwise false
	 */
	protected boolean outOfRange(double membership) {
		
		return (membership > 1 || membership < 0);
		
	}
	
	
	
	/**
	 * Returns the membership value at the given point xValue depending upon the implementation of this set..
	 * 
	 * @param xValue
	 *            Point at which the membership value is to be calculated
	 * @return Membership value at xValue
	 */
	public abstract double getMembershipValue(double xValue);
	
	
	
	/**
	 * Returns the membership value of the element depending upon the implementation of this set. It gives the same
	 * value
	 * as that of {@link #getMembershipValue(double xValue)} where xValue is {@link Element#getValue()}
	 * 
	 * @param element
	 *            Element whose membership value is to be calculated
	 * @return membership value of element
	 */
	public double getMembershipValue(Element element) {
		
		return getMembershipValue(element.getValue());
	}
	
	
	
	/**
	 * Returns a new {@link Singleton} object which encapsulates both the element and its membership value.
	 * 
	 * @param element
	 *            Element to be encapsulated
	 * @return {@link Singleton} object containing element and its membership value
	 */
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
	
	
	
	/**
	 * This method gives the area under the curve formed by this membership function with respect to x axis. The y axis
	 * represents the membership value.
	 * The area is not calculated with respect to the lower limit of this set. The area is calculated with y = 0. The
	 * area is calculated by using the
	 * appropriate formula obtained by integration of the function definition. The area varies according the membership
	 * function implemented
	 *
	 * @return Area under this curve
	 */
	public abstract double getArea();
	
	
	
	/**
	 * This method returns the weighted mean of the x-value of this membership function. The weight is the membership
	 * value. The formula used is obtained
	 * by the integration form of the formula.
	 * (x1.w1 + x2.w2 + ... + xn.wn) / ( w1 + w2 + ... wn)
	 * 
	 * 
	 * @return Weighted mean of the fuzzy set
	 */
	public abstract double getWeightedMean();
	
	
	
	/**
	 * This method gives the point which has the maximum membership value in the entire set. If more than two points
	 * exist then it depends upon the implementation.
	 * 
	 * @return Point having maximum membership in the set
	 */
	public abstract double maxMembershipAt();
	
}

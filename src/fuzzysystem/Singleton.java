package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;


/**
 * Singleton class represents the pair ({@link Element} element, double membership). The singleton element should always be obtained in
 * context of a fuzzy set. The membership value is always in reference to a fuzzy set. A singleton element should never be used independently or
 * with the other instances of this class.
 * 
 * <p> Consider instances of this class as {@linkplain Element} instance associated to a fuzzy set or membership function. 
 * @author Aniket Kumar Tripathi
 * @see DiscreteFuzzySet 
 * 		AbstractFuzzySet </br>
 * 		LinearFuzzySet 
 */
public final class Singleton {
	
	/**
	 * The first member of the pair. The part on which all the operations are performed.
	 */
	private final Element	element;
	
	/**
	 * Membership value of the element in context to a membership function.
	 */
	private final double	membership;
	
	
	/**
	 * Creates a <i>Singleton</i> instance from the element and its membership values. Since the membership value can be entered directly,
	 * it should be avoided for consistency.
	 * 
	 * @throws MembershipOutOfException If the membership value is beyond [0, 1] inclusive.
	 *  
	 */
	public Singleton(Element element, double membership) throws MembershipOutOfRangeException {
		this.element = element;
		
		if (0 <= membership || membership <= 1)
			this.membership = membership;
		
		else
			throw new MembershipOutOfRangeException();
	}
	
	
	
	/**
	 * A generic constructor. Creates a <i>Singleton</i> instance from the element. The membership value of the element is obtained by the <b>Type</b> fuzzySet.
	 * Type is any class which extends {@linkplain AbstractFuzzySet}.
	 */
	public <Type extends AbstractFuzzySet> Singleton(Element element, Type fuzzySet) {
		this.element = element;
		membership = fuzzySet.getMembershipValue(element.getValue());
	}
	
	
	/**
	 * Getter method of {@linkplain #membership} field
	 * @return membership value of the element.
	 */
	public double getMembershipValue() {
		
		return membership;
	}
	
	
	/**
	 * Getter method of {@linkplain #element} field
	 * @return The first member of the pair.
	 */
	public Element getElement() {
		
		return element;
	}
	
	
	
	/**
	 * Returns the name of the instance. It is the same as the string representation of the {@linkplain Singleton#element}.
	 * @return The string representation of element.
	 */
	public String getName() {
		
		return element.toString();
	}
	
	
	
	/**
	 * Creates a new <i>Singleton</i> instance which is complement of the this instance. The complement of <i>Singleton</i> <em><b>A</em></b> 
	 * is the ordered pair (element, 1 - membership value of element). The reference to {@linkplain Element} object in <em><b>this</em></b> is same as in the complement
	 * <i>Singleton</i> instance. 
	 * @return The fuzzy complement of this instance
	 **/
	public Singleton complement()  {
		Singleton singleton;
		try {
			singleton = complement(this.element);
		}
		catch(MembershipOutOfRangeException e){
			singleton = null;
			e.printStackTrace();
		}
		return singleton;
	}
	
	
	
	/**
	 * Creates a new <i>Singleton</i> instance which is complement of the this instance. The complement of <i>Singleton</i> <em><b>A</em></b> 
	 * is the ordered pair (element, 1 - membership value of element). The reference to {@linkplain Element} object in complement is specified in the parameter.
	 * In a way, it binds the complement of this instance to another <i>ELement</i>.
	 * 
	 * @return The fuzzy complement of this instance
	 **/
	public Singleton complement(Element element) throws MembershipOutOfRangeException {
		
		return new Singleton(element, 1 - this.membership);
	}
	
	
	/**
	 * Returns the string representation of <i>this</i>. The string representation of the element is the following </br>
	 *  (String representation of <i>Element</i> element, <i>membership value</i>)
	 *  
	 *  <p>for example : (x1, 1.0) (Aniket, 0.5) etc. The first part is the string representation of {@linkplain#element}.</p>
	 * 
	 */
	@Override
	public String toString() {
		
		return "(" + element.toString() + "," + membership + ")";
	}
	
}
package fuzzysystem;

/**
 * <p>
 * Element is the basic unit of the fuzzy set. It contains two fields name and value. The name is a string which
 * describes the
 * element. The value is used to calculate the membership value of the function.
 * </p>
 * 
 * <p>
 * Element is more useful in {@link DiscreteFuzzySet}. Every member of {@linkplain DiscreteFuzzySet} is an instance of
 * this class. This is an
 * immutable class.
 * </p>
 * 
 * 
 * @author Aniket Kumar Tripathi
 * @version 1.0
 * 
 * @see DiscreteFuzzySet
 */
public final class Element {
	
	/**
	 * Represents the name of this element. It is an optional field. Default value is "". It gives more readability.
	 */
	private final String name;
	
	/**
	 * Stores of the value of the element. It is used for all the operations and calculation performed by an element.
	 */
	private final double value;
	
	/**
	 * This determines whether the {@link #name} field or {@link #value} field will be used for representation of
	 * element in string..
	 */
	private final boolean useValueAsName;
	
	
	
	/**
	 * Creates a new <i>Element</i> instance from the given value and the given name. The <i>useValueAsName</i> field is
	 * set to
	 * false.
	 */
	public Element(String name, double value) {
		this.name = name;
		this.value = value;
		this.useValueAsName = false;
	}
	
	
	
	/**
	 * Creates a new <i>Element</i> instance from the given value and the given name.
	 */
	public Element(String name, double value, boolean useValueAsName) {
		this.name = name;
		this.value = value;
		this.useValueAsName = useValueAsName;
	}
	
	
	
	/**
	 * Creates a new <i>Element</i> instance from the given value only. The <i>useValueAsName</i> field is set to
	 * false. The <i>name</i> field is set as "" and <i>useValueasName<i> field is set to true.
	 */
	public Element(double value) {
		this.name = "";
		this.value = value;
		this.useValueAsName = true;
	}
	
	
	
	/**
	 * Creates a new <i>Element</i> instance from the given value.
	 */
	public Element(double value, boolean useNameAsValue) {
		this.name = "";
		this.value = value;
		this.useValueAsName = useNameAsValue;
	}
	
	
	
	/**
	 * Creates a new <i>Element</i> instance from another <i>Element</i> instance. It is a copy constructor.
	 */
	public Element(Element element) {
		this.name = element.name;
		this.value = element.value;
		this.useValueAsName = element.useValueAsName;
	}
	
	
	
	/**
	 * Getter method for {@linkplain #name} field
	 * 
	 * @return The name of the element
	 */
	public String getName() {
		
		return name;
	}
	
	
	
	/**
	 * Getter method for {@linkplain #value} field
	 * 
	 * @return The value of the element
	 */
	public double getValue() {
		
		return value;
	}
	
	
	
	/**
	 * Returns whether the value will be representation of element in string.
	 * 
	 * @return True - If value will be used as string representation</br>
	 *         False - Otherwise
	 */
	public boolean useValueAsName() {
		
		return useValueAsName;
	}
	
	
	
	/**
	 * Returns the string representation of the element. If the {@linkplain #useValueAsName} field is set to true then
	 * string representation of the value is returned otherwise the name is returned.
	 */
	@Override
	public String toString() {
		
		return (useValueAsName) ? Double.toString(value) : name;
	}
	
}

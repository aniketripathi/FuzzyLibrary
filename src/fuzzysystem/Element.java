package fuzzysystem;

/**
 * 
 * 
 * 
 * @author Aniket Kumar Tripathi
 * @version 1.0
 * 
 */
public final class Element {
	
	private final String	name;
	private final double	value;
	private final boolean	useValueAsName;
	
	
	
	public Element(String name, double value) {
		this.name = name;
		this.value = value;
		this.useValueAsName = false;
	}
	
	
	
	public Element(String name, double value, boolean useValueAsName) {
		this.name = name;
		this.value = value;
		this.useValueAsName = useValueAsName;
	}
	
	
	
	public Element(double value) {
		this.name = "";
		this.value = value;
		this.useValueAsName = true;
	}
	
	
	
	public Element(double value, boolean useNameAsValue) {
		this.name = "";
		this.value = value;
		this.useValueAsName = useNameAsValue;
	}
	
	
	
	public Element(Element element) {
		this.name = element.name;
		this.value = element.value;
		this.useValueAsName = element.useValueAsName;
	}
	
	
	
	public Element(String name) {
		this.value = 0;
		this.name = name;
		this.useValueAsName = false;
	}
	
	
	
	public String getName() {
		
		return name;
	}
	
	
	
	public double getValue() {
		
		return value;
	}
	
	
	
	public boolean useValueAsName() {
		
		return useValueAsName;
	}
	
	
	
	@Override
	public String toString() {
		
		return (useValueAsName) ? Double.toString(value) : name;
	}
	
}

package fuzzysystem;

public final class Element {

	private final String name;
	private final double value;
	private final boolean useValueAsName;
	
	public Element(String name, double value){
			this.name = name;
			this.value = value;
			this.useValueAsName = false;
	}
	
	public Element(double value){
		this.name = null;
		this.value = value;
		this.useValueAsName = true;
	}
	
	public Element(double value, boolean useNameAsValue){
		this.name = null;
		this.value = value;
		this.useValueAsName = useNameAsValue;
	}
	
	public Element(Element element){
		this.name = element.name;
		this.value = element.value;
		this.useValueAsName = element.useValueAsName;
	}
	
	public Element(String name){
		this.value = 0;
		this.name = name;
		this.useValueAsName = false;
	}
	
	public String getName(){
		return name;
	}
	
	public double getValue(){
		return value;
	}
	
	public boolean useValueAsName(){
		return useValueAsName;
	}
	
	
	
	
	
	@Override	
	public String toString(){
		if(useValueAsName)		return new Double(value).toString();
		return name;
	}
	
	
	
}

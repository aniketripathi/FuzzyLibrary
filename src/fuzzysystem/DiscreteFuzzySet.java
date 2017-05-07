package fuzzysystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import fuzzysystem.exceptions.MembershipOutOfRangeException;

public class DiscreteFuzzySet {

	private final HashMap<Element, Double> map;
	
	
	
	
	public DiscreteFuzzySet(int capacity){
		map = new HashMap<Element, Double>(capacity);
	}
	
	
	
	public DiscreteFuzzySet(){
		map = new HashMap<Element, Double>(20);				//default capacity 20
	}
	
	
	
	public void add(Element element, Double value)throws MembershipOutOfRangeException{
		if(value < 0 || value > 1)		throw new MembershipOutOfRangeException(); 	
		map.put(element, value);	 
	}
	
	
	
	public void remove(Element element){
		map.remove(element);
	}
	
	
	
	public int size(){
		return map.size();
	}
	
	
	
	public boolean contains(Element element){
		return map.containsKey(element);
	}
	
	
	
	public boolean belongs(Element element){
		 return (this.contains(element) && map.get(element) > 0);
	}
	
	
	
	public double getMembershipValue(Element element){
		if( this.contains(element))			return map.get(element);
		return 0.0;
	}
	
	
	
	public Iterator<Element> iterator(){
		return this.map.keySet().iterator();
	}
	
	
	
	//removes elements with 0 membership value
	public int clean(){
		int count = 0;
		Iterator<Element> iterator = this.iterator();
		Element element;
		
		while(iterator.hasNext()){
			element = iterator.next();
			if(this.contains(element)	&&	this.map.get(element) == 0){
				this.remove(element);
				++count;
			}
		}
		return count;
	}
	
	
	
	public DiscreteFuzzySet union(DiscreteFuzzySet fuzzySet){
		Iterator<Element>	iterator = this.iterator();
		DiscreteFuzzySet unionSet = new DiscreteFuzzySet(this.size() + fuzzySet.size());
		
		Element element;
		while(iterator.hasNext()){
			element = iterator.next();
			if(fuzzySet.contains(element))				unionSet.map.put(element, Math.max(this.map.get(element), fuzzySet.getMembershipValue(element)) );
			else 										unionSet.map.put(element, this.map.get(element));
		}
		
		 iterator = fuzzySet.iterator();
		while(iterator.hasNext()){
			element = iterator.next(); 
			if(!unionSet.contains(element))	
			unionSet.map.put(element, fuzzySet.getMembershipValue(element));
		}
		 
		return unionSet;
	}
	
	
	
	public DiscreteFuzzySet intersection(DiscreteFuzzySet fuzzySet) {
		DiscreteFuzzySet intersectionSet = new DiscreteFuzzySet(Math.max(this.size(), fuzzySet.size()));
		Iterator<Element> iterator = this.iterator();
		
		Element element;
		while(iterator.hasNext()){
			element = iterator.next();
			if(fuzzySet.contains(element))
				intersectionSet.map.put(element, Math.min(this.map.get(element), fuzzySet.getMembershipValue(element)));
		}
		
		return intersectionSet;
	}
	
	
	
	public DiscreteFuzzySet complement(){
		DiscreteFuzzySet complementSet = new DiscreteFuzzySet(this.size());
		Iterator<Element> iterator = this.iterator();
		
		Element element;
		while(iterator.hasNext()){
			element = iterator.next();
			complementSet.map.put(element, 1 - this.map.get(element));
		}
		
		return complementSet;
		
	}
	
	
	
	public DiscreteFuzzySet product(DiscreteFuzzySet fuzzySet){
		DiscreteFuzzySet productSet = new DiscreteFuzzySet(Math.max(this.size(), fuzzySet.size()));
		Iterator<Element> iterator = this.iterator();
		
		Element element;
		while(iterator.hasNext()){
			
			element = iterator.next();
			if(fuzzySet.belongs(element))
				productSet.map.put(element, this.map.get(element) * fuzzySet.getMembershipValue(element));
		
		}
		
		return productSet;
	}
	
	
	
	//using A = B if A is subset of B and B is subset of A
	public boolean equals(DiscreteFuzzySet fuzzySet){
		Iterator<Element> iterator = this.iterator();
		
		Element element;
		while(iterator.hasNext()){
			element = iterator.next();
			if(this.map.get(element) != fuzzySet.getMembershipValue(element))			
				return false;
		}
		
		iterator = fuzzySet.iterator();
		while(iterator.hasNext()){
			element = iterator.next();
			if(fuzzySet.getMembershipValue(element)  != this.getMembershipValue(element))
				return false;
		}
		
		return true;
	}
	
	
	
	public DiscreteFuzzySet product(double scaler) throws MembershipOutOfRangeException{
		DiscreteFuzzySet fuzzySet = new DiscreteFuzzySet(size());
		Iterator<Element>	iterator = iterator();
		
		Element element;
		while(iterator.hasNext()){
			element = iterator.next();
			fuzzySet.add(element, scaler * this.map.get(element));
		}
		
		return fuzzySet;
	}
	
	
	
	public DiscreteFuzzySet power(float power){
		DiscreteFuzzySet fuzzySet = new DiscreteFuzzySet(size());
		Iterator<Element>	iterator = iterator();
		
		Element element;
		while(iterator.hasNext()){
			element = iterator.next();
			fuzzySet.map.put(element, Math.pow(this.map.get(element), power));
		}
		
		return fuzzySet;
	}
	
	
	
	public HashSet<Singleton> getSingletonSet(){
		
		HashSet<Singleton>  singletonSet = new HashSet<Singleton>(map.size());
		Iterator<Element> iterator = iterator();
		Element element;
		while(iterator.hasNext()){
			element = iterator.next();
		try {	
			singletonSet.add(new Singleton(element, map.get(element)));				// values in singletonSet will be reflected
		}
		catch(MembershipOutOfRangeException e){
			// Although it is made sure that this will never happen , still if it happens then -
			System.err.println("Membership value beyond [0,1] present in set for element: " + element);
		}
	}
		return singletonSet;
	}
	
	
	
	@Override
	public String toString(){
		
		StringBuilder buffer = new StringBuilder( map.size() * 25 );
		buffer.append("[ ");
		Iterator<Element> iterator = iterator();
		
		Element e;
		if(iterator.hasNext())	{
			buffer.append("(");
			e = iterator.next();
			buffer.append(e.toString() + "," + map.get(e).toString());
			buffer.append(")");
		}
		
		
		while(iterator.hasNext()){
			buffer.append(",(");
			e = iterator.next();
			buffer.append(e.toString() + "," + map.get(e).toString());
			buffer.append(")");
		}
		
		buffer.append(" ]");
		return buffer.toString();
	}

	
	
}

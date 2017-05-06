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
	
	//TODO correct it
	public DiscreteFuzzySet union(DiscreteFuzzySet fuzzySet) throws MembershipOutOfRangeException{
		Iterator<Element>	iterator = this.iterator();
		DiscreteFuzzySet unionSet = new DiscreteFuzzySet(this.size() + fuzzySet.size());
		
		Element element;
		while(iterator.hasNext()){
			element = iterator.next();
			if(fuzzySet.contains(element))				unionSet.add(element, Math.max(this.map.get(element), fuzzySet.getMembershipValue(element)) );
			else 										unionSet.add(element, this.map.get(element));
		}
		
		 iterator = fuzzySet.iterator();
		while(iterator.hasNext()){
			element = iterator.next();
			if(unionSet.contains(element))					continue; 
			unionSet.add(element, fuzzySet.getMembershipValue(element));
		}
		 
		return unionSet;
	}
	
	
	public HashSet<Singleton> getSingletonSet()throws MembershipOutOfRangeException{
		
		HashSet<Singleton>  singletonSet = new HashSet<Singleton>(map.size());
		Iterator<Element> iterator = iterator();
		Element element;
		while(iterator.hasNext()){
			element = iterator.next();
			singletonSet.add(new Singleton(element, map.get(element)));				// values in singletonset will be reflected
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

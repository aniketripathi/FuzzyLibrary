package fuzzysystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Predicate;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



/***
 * 
 * @author Aniket Kumar Tripathi
 * @version 1.0
 *
 */

public class DiscreteFuzzySet implements FuzzySet {
	
	private final HashMap<Element, Double>	map;
	private boolean							autoClean;
	
	
	
	public DiscreteFuzzySet(int capacity) {
		map = new HashMap<Element, Double>(capacity);
		autoClean = false;
	}
	
	
	
	public DiscreteFuzzySet() {
		map = new HashMap<Element, Double>(20); // default capacity 20
		autoClean = false;
	}
	
	
	
	public DiscreteFuzzySet(int capacity, boolean autoClean) {
		map = new HashMap<Element, Double>(capacity);
		this.autoClean = autoClean;
	}
	
	
	
	public DiscreteFuzzySet(boolean autoClean) {
		map = new HashMap<Element, Double>(20);
		this.autoClean = autoClean;
	}
	
	
	
	@Override
	public DiscreteFuzzySet add(Element element, Double value) throws MembershipOutOfRangeException {
		
		if (value < 0 || value > 1)
			throw new MembershipOutOfRangeException();
		
		map.put(element, value);
		
		return this;
	}
	
	
	
	@Override
	public DiscreteFuzzySet remove(Element element) {
		
		map.remove(element);
		return this;
	}
	
	
	
	@Override
	public int size() {
		
		return map.size();
	}
	
	
	
	@Override
	public boolean contains(Element element) {
		
		return map.containsKey(element);
	}
	
	
	
	@Override
	public boolean belongs(Element element) {
		
		return (this.contains(element) && map.get(element) > 0);
	}
	
	
	
	@Override
	public double getMembershipValue(Element element) {
		
		if (this.contains(element))
			return map.get(element);
		
		return 0.0;
	}
	
	
	
	@Override
	public Iterator<Element> iterator() {
		
		return this.map.keySet().iterator();
	}
	
	
	
	// removes elements with 0 membership value
	public int clean() {
		
		int count = 0;
		Iterator<Element> iterator = this.iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (this.contains(element) && this.map.get(element) == 0) {
				iterator.remove();
				++count;
			}
		}
		return count;
	}
	
	
	
	@Override
	public DiscreteFuzzySet union(DiscreteFuzzySet fuzzySet) {
		
		DiscreteFuzzySet unionSet = new DiscreteFuzzySet(this.size() + fuzzySet.size());
		
		Iterator<Element> iterator = this.iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			unionSet.map.put(element, Math.max(this.map.get(element), fuzzySet.getMembershipValue(element)));
		}
		
		iterator = fuzzySet.iterator();
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (!unionSet.contains(element)) unionSet.map.put(element, fuzzySet.getMembershipValue(element));
		}
		
		if (autoClean)
			unionSet.clean();
		
		return unionSet;
	}
	
	
	
	@Override
	public DiscreteFuzzySet intersection(DiscreteFuzzySet fuzzySet) {
		
		DiscreteFuzzySet intersectionSet = new DiscreteFuzzySet(Math.max(this.size(), fuzzySet.size()));
		
		Iterator<Element> iterator = this.iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (fuzzySet.contains(element))
				intersectionSet.map.put(element, Math.min(this.map.get(element), fuzzySet.getMembershipValue(element)));
			
		}
		
		if (autoClean)
			intersectionSet.clean();
		
		return intersectionSet;
	}
	
	
	
	@Override
	public DiscreteFuzzySet complement() {
		
		DiscreteFuzzySet complementSet = new DiscreteFuzzySet(this.size());
		
		Iterator<Element> iterator = this.iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			complementSet.map.put(element, 1 - this.map.get(element));
		}
		
		if (autoClean)
			complementSet.clean();
		
		return complementSet;
		
	}
	
	
	
	@Override
	public DiscreteFuzzySet product(DiscreteFuzzySet fuzzySet) {
		
		DiscreteFuzzySet productSet = new DiscreteFuzzySet(Math.max(this.size(), fuzzySet.size()));
		
		Iterator<Element> iterator = this.iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			productSet.map.put(element, this.map.get(element) * fuzzySet.getMembershipValue(element));
			
		}
		
		if (autoClean)
			productSet.clean();
		
		return productSet;
	}
	
	
	
	// using A = B if A is subset of B and B is subset of A
	@Override
	public boolean equals(DiscreteFuzzySet fuzzySet) {
		
		Iterator<Element> iterator = this.iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (this.map.get(element) != fuzzySet.getMembershipValue(element))
				return false;
			
		}
		
		iterator = fuzzySet.iterator();
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (fuzzySet.getMembershipValue(element) != this.getMembershipValue(element))
				return false;
			
		}
		
		return true;
	}
	
	
	
	public DiscreteFuzzySet product(double scaler) throws MembershipOutOfRangeException {
		
		DiscreteFuzzySet productSet = new DiscreteFuzzySet(size());
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			productSet.add(element, scaler * this.map.get(element));
		}
		
		if (autoClean)
			productSet.clean();
		
		return productSet;
	}
	
	
	
	public DiscreteFuzzySet power(float power) {
		
		DiscreteFuzzySet powerSet = new DiscreteFuzzySet(size());
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			powerSet.map.put(element, Math.pow(this.map.get(element), power));
		}
		
		if (autoClean)
			powerSet.clean();
		
		return powerSet;
	}
	
	
	
	public HashSet<Singleton> getSingletonSet() {
		
		HashSet<Singleton> singletonSet = new HashSet<Singleton>(map.size());
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			try {
				singletonSet.add(new Singleton(element, map.get(element)));
			}
			catch (MembershipOutOfRangeException e) {
				// Although it is made sure that this will never happen
				System.err.println("Membership value beyond [0,1] present in set for element: " + element);
			}
		}
		
		return singletonSet;
	}
	
	
	
	@Override
	public String toString() {
		
		StringBuilder buffer = new StringBuilder(map.size() * 25);
		buffer.append("[ ");
		Iterator<Element> iterator = iterator();
		Element element;
		
		if (iterator.hasNext()) {
			buffer.append('(');
			element = iterator.next();
			buffer.append(element.toString() + "," + map.get(element).toString());
			buffer.append(')');
		}
		
		while (iterator.hasNext()) {
			buffer.append(",(");
			element = iterator.next();
			buffer.append(element.toString());
			buffer.append(',');
			buffer.append(map.get(element).toString());
			buffer.append(')');
		}
		
		buffer.append(" ]");
		
		return buffer.toString();
	}
	
	
	
	public boolean autoCleanEnabled() {
		
		return autoClean;
	}
	
	
	
	public void setAutoClean(boolean autoClean) {
		
		this.autoClean = autoClean;
	}
	
	
	
	@Override
	public double cardinalValue() {
		
		double sum = 0;
		Iterator<Element> iterator = iterator();
		
		while (iterator.hasNext())
			sum += this.map.get(iterator.next());
		
		return sum;
	}
	
	
	
	@Override
	public HashSet<Element> getCrispAll() {
		
		HashSet<Element> crisp = new HashSet<Element>(size());
		Iterator<Element> iterator = iterator();
		
		while (iterator.hasNext())
			crisp.add(iterator.next());
		
		return crisp;
	}
	
	
	
	@Override
	public HashSet<Element> getCore() {
		
		HashSet<Element> crisp = new HashSet<Element>(size());
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (map.get(element) >= 0.9999999999)		// 0.9999999999 instead of 1.0 to maintain precision
				crisp.add(element);
			
		}
		
		return crisp;
	}
	
	
	
	@Override
	public HashSet<Element> getSupport() {
		
		HashSet<Element> crisp = new HashSet<Element>(size());
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (map.get(element) > 0)
				crisp.add(element);
			
		}
		
		return crisp;
	}
	
	
	
	@Override
	public Element height() {
		
		double maxMembership = 0;
		Iterator<Element> iterator = iterator();
		Element element, maxElement = null;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			double membership = map.get(element);
			
			if (membership > maxMembership) {
				maxElement = element;
				maxMembership = membership;
			}
		}
		
		return maxElement;
	}
	
	
	
	public DiscreteFuzzySet retainIf(Predicate<Element> condition) {
		
		DiscreteFuzzySet fuzzySet = new DiscreteFuzzySet(size());
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (condition.test(element))
				fuzzySet.map.put(element, map.get(element));
			
		}
		
		return fuzzySet;
	}
	
}

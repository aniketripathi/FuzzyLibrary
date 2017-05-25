package fuzzysystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



/***
 * 
 * @author Aniket Kumar Tripathi
 * @version 1.0
 *
 */

public class DiscreteFuzzySet {
	
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
	
	
	
	public DiscreteFuzzySet add(Element element, Double value) throws MembershipOutOfRangeException {
		
		if (value < 0 || value > 1)
			throw new MembershipOutOfRangeException();
		
		map.put(element, value);
		
		return this;
	}
	
	
	
	public DiscreteFuzzySet remove(Element element) {
		
		map.remove(element);
		return this;
	}
	
	
	
	public int size() {
		
		return map.size();
	}
	
	
	
	public boolean contains(Element element) {
		
		return map.containsKey(element);
	}
	
	
	
	public boolean belongs(Element element) {
		
		return (contains(element) && map.get(element) > 0);
	}
	
	
	
	public double getMembershipValue(Element element) {
		
		return (contains(element)) ? map.get(element) : 0.0;
	}
	
	
	
	public Iterator<Element> iterator() {
		
		return map.keySet().iterator();
	}
	
	
	
	// removes elements with 0 membership value
	public int clean() {
		
		int count = 0;
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (contains(element) && map.get(element) == 0) {
				iterator.remove();
				++count;
			}
		}
		return count;
	}
	
	
	
	public DiscreteFuzzySet union(DiscreteFuzzySet fuzzySet) {
		
		DiscreteFuzzySet unionSet = new DiscreteFuzzySet(size() + fuzzySet.size());
		
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			unionSet.map.put(element, Math.max(map.get(element), fuzzySet.getMembershipValue(element)));
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
	
	
	
	public DiscreteFuzzySet intersection(DiscreteFuzzySet fuzzySet) {
		
		DiscreteFuzzySet intersectionSet = new DiscreteFuzzySet(Math.max(size(), fuzzySet.size()));
		
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (fuzzySet.contains(element))
				intersectionSet.map.put(element, Math.min(map.get(element), fuzzySet.getMembershipValue(element)));
			
		}
		
		if (autoClean)
			intersectionSet.clean();
		
		return intersectionSet;
	}
	
	
	
	public DiscreteFuzzySet complement() {
		
		DiscreteFuzzySet complementSet = new DiscreteFuzzySet(size());
		
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			complementSet.map.put(element, 1 - map.get(element));
		}
		
		if (autoClean)
			complementSet.clean();
		
		return complementSet;
		
	}
	
	
	
	public DiscreteFuzzySet product(DiscreteFuzzySet fuzzySet) {
		
		DiscreteFuzzySet productSet = new DiscreteFuzzySet(Math.max(size(), fuzzySet.size()));
		
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			productSet.map.put(element, map.get(element) * fuzzySet.getMembershipValue(element));
			
		}
		
		if (autoClean)
			productSet.clean();
		
		return productSet;
	}
	
	
	
	// using A = B if A is subset of B and B is subset of A
	
	public boolean equalsFuzzySet(DiscreteFuzzySet fuzzySet) {
		
		boolean isEqual = true;
		
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (map.get(element) != fuzzySet.getMembershipValue(element))
				isEqual = false;
			
		}
		
		iterator = fuzzySet.iterator();
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (fuzzySet.getMembershipValue(element) != getMembershipValue(element))
				isEqual = false;
			
		}
		
		return isEqual;
	}
	
	
	
	public DiscreteFuzzySet product(double scaler) throws MembershipOutOfRangeException {
		
		DiscreteFuzzySet productSet = new DiscreteFuzzySet(size());
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			productSet.add(element, scaler * map.get(element));
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
			powerSet.map.put(element, Math.pow(map.get(element), power));
		}
		
		if (autoClean)
			powerSet.clean();
		
		return powerSet;
	}
	
	
	
	public HashSet<Singleton> getSingletonSet() {
		
		HashSet<Singleton> singletonSet = new HashSet<Singleton>(map.size());
		Iterator<Element> iterator = iterator();
		Element element;
		
		Logger logger = Logger.getLogger(getClass());
		
		while (iterator.hasNext()) {
			element = iterator.next();
			try {
				singletonSet.add(new Singleton(element, map.get(element)));
			}
			catch (MembershipOutOfRangeException e) {
				// Although it is made sure that this will never happen
				if (logger.isLoggable(Level.SEVERE))
					logger.log(Level.SEVERE, "Membership value beyond [0,1] present in set for element: " + element);
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
			buffer.append(element.toString());
			buffer.append(',');
			buffer.append(map.get(element).toString());
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
	
	
	
	public double cardinalValue() {
		
		double sum = 0;
		Iterator<Element> iterator = iterator();
		
		while (iterator.hasNext())
			sum += map.get(iterator.next());
		
		return sum;
	}
	
	
	
	public HashSet<Element> getCrispIf(Predicate<Element> condition) {
		
		HashSet<Element> crisp = new HashSet<Element>(size());
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (condition.test(element))
				crisp.add(element);
			
		}
		return crisp;
	}
	
	
	
	public HashSet<Element> getCrispAll() {
		
		return getCrispIf(element -> true);
	}
	
	
	
	public HashSet<Element> getCore() {
		
		final double CORE_VALUE = 0.999999999;
		
		return getCrispIf(element -> element.getValue() >= CORE_VALUE);
	}
	
	
	
	public HashSet<Element> getSupport() {
		
		return getCrispIf(element -> element.getValue() > 0);
	}
	
	
	
	public Element height() {
		
		double maxMembership = 0;
		Iterator<Element> iterator = iterator();
		Element element,
				maxElement = null;
		
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

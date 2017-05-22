/**
 * 
 */
package fuzzysystem;

import java.util.HashSet;
import java.util.Iterator;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



/**
 * @author Aniket Kumar Tripathi
 *
 */
public interface FuzzySet {
	
	public DiscreteFuzzySet add(Element element, Double value) throws MembershipOutOfRangeException;
	
	
	
	public DiscreteFuzzySet remove(Element element);
	
	
	
	public boolean belongs(Element element);
	
	
	
	public boolean contains(Element element);
	
	
	
	public Iterator<Element> iterator();
	
	
	
	public int size();
	
	
	
	public double getMembershipValue(Element element);
	
	
	
	public double cardinalValue();
	
	
	
	public DiscreteFuzzySet union(DiscreteFuzzySet fuzzySet);
	
	
	
	public DiscreteFuzzySet intersection(DiscreteFuzzySet fuzzySet);
	
	
	
	public DiscreteFuzzySet complement();
	
	
	
	public DiscreteFuzzySet product(DiscreteFuzzySet fuzzySet);
	
	
	
	public boolean equals(DiscreteFuzzySet fuzzySet);
	
	
	
	public HashSet<Element> getCrispAll();
	
	
	
	public HashSet<Element> getCore();
	
	
	
	public HashSet<Element> getSupport();
	
	
	
	public Element height();
	
}

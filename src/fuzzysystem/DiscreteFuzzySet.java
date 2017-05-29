package fuzzysystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

import fuzzysystem.exceptions.MembershipOutOfRangeException;



/***
 * DiscreteFuzzySet is an implementation of a discrete fuzzy set. Discrete fuzzy set contains a finite number of
 * members. Each
 * member is represented by the pair <b>({@linkplain Element}, double).</b> The pair is called a Singleton. Just like
 * normal set, fuzzy set is also a collection
 * of similar data items, but there is a difference.
 * 
 * <p>
 * We refer the normal set as <i>Crisp Set</i>. Each member in <i>Universal Set</i> either belongs or does not belongs
 * to the
 * crisp set. In <i>Fuzzy Set System</i>, every member in universal set or universe of discourse belongs to a set with a
 * <b>membership
 * value</b> which indicates the participation level of that member in that particular set. If membership value is
 * closer to zero then
 * the member almost doesn't belongs to the set. If membership value is close to 1 then the member almost belongs to the
 * set. The membership
 * value should always between 0 and 1 inclusive. Thus it represents an uncertain situation. The operations of fuzzy
 * sets have different working than that of crisp sets.
 * </p>
 * 
 * <p>
 * This class is implemented using {@linkplain HashMap}. Each member of the set is represented by instance of
 * {@linkplain Element}.
 * The membership value is a {@linkplain Double}. Each member can independently can be represented by
 * {@linkplain Singleton} instance,
 * but it should be avoided. Discrete fuzzy set is implemented using primitive <i>double</i> so there will be some loss
 * in precision.
 * </p>
 * 
 * <p>
 * To get started with discrete set, first create an <i>Universal set</i> of {@linkplain Element} instances. Avoid using
 * fuzzy sets without
 * a <i>universal set</i>. Then create discrete fuzzy sets using any of the given constructor and add the members to it
 * from <i>universal set</i>.Then you will be able to perform any operation
 * on the sets using the provided methods. You can also add membership values using continuous membership functions.
 * </p>
 * 
 * <p>
 * The class provides cleaning operation. If a membership value of any element is zero, it means that the element does
 * not belongs to the
 * present set. So it can be removed. Cleaning removes the elements from the which have zero membership value. Most of
 * the operations are not affected
 * by the cleaning process except complement. So enable cleaning only if you need it. It is disabled by default.
 * </p>
 * 
 * @author Aniket Kumar Tripathi
 * @version 1.0
 *
 * @see
 * 		Set</br>
 *      AbstractFuzzySet
 **/

public class DiscreteFuzzySet {
	
	/**
	 * The discrete fuzzy set is implemented through a map. The keys are the {@linkplain Element} elements and the value
	 * to the keys are the membership value.
	 */
	private final HashMap<Element, Double> map;
	
	/**
	 * It sets whether the fuzzy set will be cleaned after the operations which modifies the set. Refer to the class
	 * description
	 * for more.
	 */
	private boolean autoClean;
	
	
	
	/**
	 * Create a discrete fuzzy set instance specifying the capacity.
	 */
	public DiscreteFuzzySet(int capacity) {
		map = new HashMap<Element, Double>(capacity);
		autoClean = false;
	}
	
	
	
	/**
	 * Create a discrete fuzzy set with default capacity of 20 members.
	 */
	public DiscreteFuzzySet() {
		map = new HashMap<Element, Double>(20); // default capacity 20
		autoClean = false;
	}
	
	
	
	/**
	 * Create a discrete fuzzy set instance specifying the capacity. If auto clean is true, this set will be cleaned
	 * after every operation that modifies
	 * it.
	 */
	public DiscreteFuzzySet(int capacity, boolean autoClean) {
		map = new HashMap<Element, Double>(capacity);
		this.autoClean = autoClean;
	}
	
	
	
	/**
	 * Create a discrete fuzzy set instance with default capacity. If auto clean is true, this set will be cleaned after
	 * every operation that modifies
	 * it.
	 */
	public DiscreteFuzzySet(boolean autoClean) {
		map = new HashMap<Element, Double>(20);
		this.autoClean = autoClean;
	}
	
	
	
	/**
	 * This method adds a member to the fuzzy set having a valid membership value.
	 * 
	 * @param element
	 *            - Member to be added
	 * @param value
	 *            - Membership value of element
	 * 
	 * @return The present set.
	 * 
	 * @throws MembershipOutOfRangeException
	 *             If the membership value is beyond [0,1] inclusive.
	 */
	public DiscreteFuzzySet add(Element element, Double value) throws MembershipOutOfRangeException {
		
		if (value < 0 || value > 1)
			throw new MembershipOutOfRangeException();
		
		map.put(element, value);
		
		return this;
	}
	
	
	
	/**
	 * This method adds a member to the fuzzy set whose membership value is calculated according to the
	 * given membership function..
	 * 
	 * @param element
	 *            - Member to be added
	 * @param membershipFunction
	 *            - This membership function will be used to evaluate the membership value of element.
	 * 
	 * @return The present set.
	 */
	public DiscreteFuzzySet add(Element element, AbstractFuzzySet membershipFunction) {
		
		map.put(element, membershipFunction.getMembershipValue(element));
		return this;
	}
	
	
	
	/**
	 * This method adds a member using singleton instance.
	 * 
	 * @param element
	 *            - Member to be added
	 * @param value
	 *            - Membership value of element
	 * 
	 * @return The present set.
	 */
	public DiscreteFuzzySet add(Singleton member) {
		
		map.put(member.getElement(), member.getMembershipValue());
		return this;
	}
	
	
	
	/**
	 * Removes a member from this set if it is present.
	 * 
	 * @param element
	 *            member to be removed
	 * 
	 * @return The present set.
	 */
	public DiscreteFuzzySet remove(Element element) {
		
		map.remove(element);
		return this;
	}
	
	
	
	/**
	 * Returns the number of elements in this set.
	 * 
	 * @return Number of members in this set.
	 */
	public int size() {
		
		return map.size();
	}
	
	
	
	/**
	 * This method determines whether the given element is present in the set or not. The membership value
	 * is not taken into count.
	 * 
	 * @param element
	 *            Member to check whether it is present in set or not.
	 * @return true - If element is present in set with any membership value including 0 <br/>
	 *         false - Otherwise
	 */
	public boolean contains(Element element) {
		
		return map.containsKey(element);
	}
	
	
	
	/**
	 * This method determines whether the given element is present in the set and have a membership value
	 * greater than zero.
	 * 
	 * @param element
	 *            member to check for belonging
	 * @return true - If the set contains the element with membership value greater than 0 </br>
	 *         false - Otherwise
	 */
	public boolean belongs(Element element) {
		
		return (contains(element) && map.get(element) > 0);
	}
	
	
	
	/**
	 * This methods returns the membership value of the specified element. If the set contains the element then the its
	 * membership value is returned which is stored in the set. Otherwise 0 is return. All the elements which are not
	 * present
	 * in the fuzzy set are assumed to have 0 membership value.
	 * 
	 * @param element
	 *            Element whose membership value is to b
	 * @return membership value of the given element
	 */
	public double getMembershipValue(Element element) {
		
		return (contains(element)) ? map.get(element) : 0.0;
	}
	
	
	
	/**
	 * Returns the iterator associated with the set.
	 * 
	 * @return Iterator instance associated with the set.
	 */
	public Iterator<Element> iterator() {
		
		return map.keySet().iterator();
	}
	
	
	
	/**
	 * Cleans the present set. The cleaning operation includes removing the members from the set which have 0 membership
	 * value.
	 * Note that cleaning the set may affect some operations like complement.
	 * 
	 * @return Number of members removed.
	 */
	public int clean() {
		
		int count = 0;
		Iterator<Element> iterator = iterator();
		Element element;
		
		while (iterator.hasNext()) {
			element = iterator.next();
			
			if (contains(element) && map.get(element) < 0.00000001) {
				iterator.remove();
				++count;
			}
		}
		return count;
	}
	
	
	
	/**
	 * Performs union of this set with the given set. The union operation for the fuzzy set can be defined as
	 * follows</br>
	 * </br>
	 * membership value of element <u>X</u> in <u>A <b>Union</b> B </u> = maximum of membership value of <u>X</u> in
	 * <u>A</u> and <u>B</u>
	 * </br>
	 * for every <u>X</u> in <u>A</u> or <u>B</u>
	 * 
	 * @param fuzzySet
	 *            Set with whom union is to be performed.
	 * @return new set containing union of set <u>A</u> and <u>B</u>
	 */
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
	
	
	
	/**
	 * Performs intersection of this set with the given set. The intersection operation for the fuzzy set can be defined
	 * as follows</br>
	 * </br>
	 * membership value of element <u>X</u> in <u>A <b>Intersection</b> B </u> = minimum of membership value of <u>X</u>
	 * in <u>A</u> and <u>B</u>
	 * </br>
	 * for every <u>X</u> in <u>A</u> and <u>B</u>
	 * 
	 * @param fuzzySet
	 *            Set with whom intersection is to be performed.
	 * @return new set containing union of set <u>A</u> and <u>B</u>
	 */
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
	
	
	
	/**
	 * Performs complement of this set with the given set. The complement operation for the fuzzy set can be defined as
	 * follows</br>
	 * </br>
	 * membership value of element <u>X</u> in <u>A <b>Complement</b> </u> = 1 - membership value of <u>X</u> in
	 * <u>A</u>
	 * </br>
	 * for every <u>X</u> in <u>A</u>.
	 * 
	 * @return new set containing union of set <u>A</u> and <u>B</u>
	 */
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
	
	
	
	/**
	 * Performs product of this set with the given set. The product operation for the fuzzy set can be defined as
	 * follows</br>
	 * </br>
	 * membership value of element <u>X</u> in <u>A <b>X</b> B </u> = product of membership value of <u>X</u> in
	 * <u>A</u> and <u>B</u>
	 * </br>
	 * for every <u>X</u> in <u>A</u> and <u>B</u>
	 * 
	 * @param fuzzySet
	 *            Set with whom product is to be performed.
	 * @return new set containing union of set <u>A</u> and <u>B</u>
	 */
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
	
	
	
	/**
	 * This method determines whether two discrete fuzzy set are equal or not. Two discrete fuzzy set are equals only if
	 * they both contains the same element with same membership value. We use the following property to find equality
	 * between two sets </br>
	 * </br>
	 * <u>A</u> equals <u>B</u> if <u>A</u> is subset of <u>B</u> and <u>B</u> is subset of <u>A</u>.
	 * 
	 * @param fuzzySet
	 *            Set with whom equality is to be tested with with this set.
	 * @return true - If this set is equal to fuzzyset</br>
	 *         false - Otherwise
	 */
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
	
	
	
	/**
	 * Performs the multiplication of the set with a numerical value. The membership value of each element of this set
	 * is multiplied
	 * by this set.
	 * 
	 * @param scaler
	 *            Multiplication factor
	 * @return A new set containing the elements of this set but membership values multiplied by the scaler.
	 * @throws MembershipOutOfRangeException
	 *             If any the membership value of any element after goes beyond [0,1] inclusive.
	 */
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
	
	
	
	/**
	 * Performs the power of the set with a numerical value. The membership value of each element of this set is raised
	 * to the power
	 * given in parameters..
	 * 
	 * @param scaler
	 *            power factor factor
	 * @return A new set containing the elements of this set but membership values raised to the power power.
	 */
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
	
	
	
	/**
	 * Returns a {@linkplain HashSet} containing all the singleton members of the set. The singleton members
	 * encapsulates both element and its membership value.
	 * The set returned is a crisp set and not a fuzzy set.
	 * 
	 * @return Crisp set encapsulating elements with its membership values.
	 */
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
	
	
	
	/**
	 * The method gives string representation of the set. The string representation begins and ends with square brackets
	 * <code> [ ] </code>.
	 * Inside the brackets each member of the set is there separated by a comma. Each member of the set is represented
	 * by <code> (element.toString(), membershipValue)</code>.
	 * 
	 */
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
	
	
	
	/**
	 * Getter method of field {@linkplain #autoClean}
	 * 
	 * @return Whether automatic cleaning of this set after operations is enabled or not.
	 */
	public boolean autoCleanEnabled() {
		
		return autoClean;
	}
	
	
	
	/**
	 * Setter method of field {@linkplain #autoClean}
	 * 
	 * @param autoClean
	 *            Enable or disable automatic cleaning of this set after operations.
	 */
	public void setAutoClean(boolean autoClean) {
		
		this.autoClean = autoClean;
	}
	
	
	
	/**
	 * The cardinal value of fuzzy set is different from that of crisp set. It is the sum of membership values of
	 * all the members of this set.
	 * 
	 * @return Sum of membership values of all elements of this set.
	 */
	public double cardinalValue() {
		
		double sum = 0;
		Iterator<Element> iterator = iterator();
		
		while (iterator.hasNext())
			sum += map.get(iterator.next());
		
		return sum;
	}
	
	
	
	/**
	 * This method returns a new crisp set of those elements present in this set which satisfies a given condition. The
	 * condition is specified
	 * using {@linkplain Predicate} functional interface.
	 * 
	 * @param condition
	 *            Condition which the element of this set must to be present in the crisp set.
	 * @return {@linkplain HasSet} of {@linkplain Element} containing all the elements belonging to this set and
	 *         satisfying the condition condition.
	 */
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
	
	
	
	/**
	 * This method returns a new crisp set of all the elements present in this set.
	 * 
	 * @return {@linkplain HasSet} of {@linkplain Element} containing all the elements belonging to this set.
	 */
	public HashSet<Element> getCrispAll() {
		
		return getCrispIf(element -> true);
	}
	
	
	
	/**
	 * This method returns a new crisp set of all the elements present in this set which form the core of the set. The
	 * members of a fuzzy set belong to
	 * the core set only if their membership value is equal to 1. Due to lack of precision, the element having
	 * membership value very close to 1 are also
	 * considered.
	 * 
	 * @return {@linkplain HasSet} of {@linkplain Element} containing all the elements belonging to this set and having
	 *         membership value equal to 1.
	 */
	public HashSet<Element> getCore() {
		
		final double CORE_VALUE = 0.999999999;
		
		return getCrispIf(element -> element.getValue() >= CORE_VALUE);
	}
	
	
	
	/**
	 * This method returns a new crisp set of all the elements present in this set which form the support of the set.
	 * The members of a fuzzy set belong to
	 * the support set only if their membership value is greater than 0. Due to lack of precision, the element having
	 * membership value very close to 0 are not
	 * included in the crisp set.
	 * 
	 * @return {@linkplain HasSet} of {@linkplain Element} containing all the elements belonging to this set and having
	 *         membership value greater than 0...
	 */
	public HashSet<Element> getSupport() {
		
		return getCrispIf(element -> element.getValue() > 0.00000001);
	}
	
	
	
	/**
	 * The height of a fuzzy set is the member with maximum membership value. This method returns the height of this
	 * set.
	 * 
	 * @return Element with maximum membership value
	 */
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
	
	
	
	/**
	 * This method creates a new <i>Discrete Fuzzy Set</i> instance which contains the members of this set along with
	 * their membership value only if
	 * they satisfy the given condition. In a way, it returns a subset of this set. The condition is spedified through
	 * {@linkplain Predicate} functional
	 * interface.
	 * 
	 * @param condition
	 *            Condition which must be satisfied to present in the subset.
	 * @return New fuzzy set containing all the elements of this set satisfying the given condition.
	 * 
	 * @see #getCrispIf(Predicate)
	 */
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

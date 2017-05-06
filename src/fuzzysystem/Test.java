package fuzzysystem;

import fuzzysystem.exceptions.MembershipOutOfRangeException;

public class Test {

	
	
	public static void main(String[] args) throws MembershipOutOfRangeException{
		
		DiscreteFuzzySet fset1= new DiscreteFuzzySet(), fset2 = new DiscreteFuzzySet();
		Element element[] = new Element[7];
		
		for(int i = 0; i < 7; i++){
			element[i] = new Element("x"+i);
		}
		
		for(int i = 0; i < 5; i++){
			fset1.add(element[i], i/10.0);
		}
		
		for(int i = 3; i < 7; i++){
			fset2.add(element[i], i/6.0);
		}
		
		System.out.println(fset1);
		System.out.println(fset2);
		System.out.println(fset1.union(fset2));
	}
}

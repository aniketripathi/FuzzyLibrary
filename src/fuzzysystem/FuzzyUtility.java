package fuzzysystem;


public class FuzzyUtility {
	

	public static enum Defuzzification {
		MAX_MEMBERSHIP_MEAN,
		WEIGHTED_MAX_MEMBERSHIP_MEAN,
		WEIGHTED_MEAN,
		CENTROID;
		
	}
	
	private double defuzzifyMaxMembershipMean(AbstractFuzzySet[] fuzzySets){
		
		double value = 0;	
		
		for(AbstractFuzzySet fuzzySet : fuzzySets)
			value += fuzzySet.maxMembershipAt();
		
		value /= fuzzySets.length;
		
		return value;
	}
	
	
	
	private double defuzzifyWeightedMaxMembershipMean(AbstractFuzzySet[] fuzzySets){
		
		double value = 0;	
		double weightSum = 0;
		
		for(AbstractFuzzySet fuzzySet : fuzzySets){
			double  x = fuzzySet.maxMembershipAt(),
					w = fuzzySet.getMembershipValue(x);
			weightSum += w;
			
			value += x * w;
		}
		
		value /= weightSum;
		
		return value;
	}
	
	
	
	private double defuzzifyWeightedMean(AbstractFuzzySet[] fuzzySets){
		
		double value = 0;	
		double weightSum = 0;
		
		for(AbstractFuzzySet fuzzySet : fuzzySets){
			double  x = fuzzySet.getWeightedMean(),
					w = fuzzySet.getMembershipValue(x);
			weightSum += w;
			
			value += x * w;
		}
		
		value /= weightSum;
		
		return value;
	}
	
	
	
	private double defuzzifyCentroid(AbstractFuzzySet[] fuzzySets){
		
		double value = 0;	
		double weightSum = 0;
		
		for(AbstractFuzzySet fuzzySet : fuzzySets){
			double  x = fuzzySet.getWeightedMean(),
					w = fuzzySet.getArea();
			weightSum += w;
			
			value += x * w;
		}
		
		value /= weightSum;
		
		return value;
	}
	
	
	
	
	public double defuzzify(Defuzzification method, AbstractFuzzySet ... fuzzySets ) {
		double value;
		
		switch(method){
			
			case MAX_MEMBERSHIP_MEAN :
				value = defuzzifyMaxMembershipMean(fuzzySets);
			
			case WEIGHTED_MAX_MEMBERSHIP_MEAN :
				value = defuzzifyWeightedMaxMembershipMean(fuzzySets);
				
			case WEIGHTED_MEAN :
				value = defuzzifyWeightedMean(fuzzySets);
				
			case CENTROID :
				value = defuzzifyCentroid(fuzzySets);
			
			default :
				value = 0;
				
		}
		
		return value;
	}
	
	
}
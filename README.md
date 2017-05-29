# FuzzyLibrary
This is a java library consisting of fuzzy system. At present it only contains basic features of the fuzzy set.


The library consist of two main part - 

1. Discrete Fuzzy Set
2. Continuous Membership Function </ul>

## Discrete Fuzzy Set

The class DiscreteFuzzySet encapsulates a discrete fuzzy set. A discrete fuzzy set contains discrete and finite number of elements each having a separate membership value. The basic operation supported by the class are -


1. Union, Intersection, Complement
2. Cardinality
3. Conversion to crisp set
4. Equality
5. Power, products	 


## Continuous Membership Function


The following membership functions are supported -

1. Linear
2. Triangle
3. Trapezoidal
4. Gaussian
5. S shaped


FuzzyUtilities can be used to defuzzify the continuous membership functions.


### Exceptions

 **MembershipOutOfRangeException** - When membership value is less than 0 or greater than 1.


 **InvalidShapeException**		- Invalid parameters are passed to continuous membership functions resulting in invalid shape.

__Note__:

Double primitive type is used, so membership values may lack precision.

Some Defuzzification methods considers overlapping area twice.
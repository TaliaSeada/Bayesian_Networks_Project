# Bayesian Network Project
This project is implementing Bayesian Network, Bayes Ball algorithm and Variable Elimination Algorithm.

A <b> Bayesian network </b> (also known as a Bayes network, Bayes net, belief network, or decision network) is a probabilistic graphical model that represents a set of variables and their conditional dependencies via a directed acyclic graph (DAG). Bayesian networks are ideal for taking an event that occurred and predicting the likelihood that any one of several possible known causes was the contributing factor. For example, a Bayesian network could represent the probabilistic relationships between diseases and symptoms. Given symptoms, the network can be used to compute the probabilities of the presence of various diseases. <br>
https://en.wikipedia.org/wiki/Bayesian_network <br>

### Bayes Ball Algorithm: 
https://arxiv.org/pdf/1301.7412 <br>
In this algorithm we wish to determine whether a given conditional statement such as J-M|B (is J independant M given B?) is true given a directed graph. <br>
The algorithm is as follows: <br>
1. Shade nodes, B, that are conditioned on.
2. If there is not a path between J and M, then the nodes J and M must be conditionally independent.
3. If there is a path between J and M, then the nodes J and M are conditionally dependent.

![image](https://user-images.githubusercontent.com/78349342/153060929-670b7dd9-9d76-4abd-9f46-24ca8450ac51.png) <br>

### Variable elimination Algorithm:
Variable elimination (VE) is a simple and general exact inference algorithm in probabilistic graphical models, such as Bayesian networks and Markov random fields.[1] It can be used for inference of maximum a posteriori (MAP) state or estimation of conditional or marginal distributions over a subset of variables. The algorithm has exponential time complexity, but could be efficient in practice for low-treewidth graphs, if the proper elimination order is used. <br>
https://en.wikipedia.org/wiki/Variable_elimination <br>
The algorithm is as follows: <br>
We would like to compute: P(Q|E1=e1,...,Ek=ek) <br>
* Start with initial factors <br>
  ~ local CPTs instantiated by evidence <br>
  ~ If an instantiated CPT becomes one-valued, discard the factor <br>
  
* While there are still hidden variables: <br>
  ~ Pick a hidden variable H <br>
  ~ Join all factors mentioning H <br>
  ~ Eliminate (sum out) H <br>
  ~ If the factor becomes one-valued, discard the factor <br>
  
* Join all remaining factors and normalize <br>

<b> There are 3 operations in this process: Join Factors, Eliminate, Normalize </b> <br>
1. JOIN: <br>
&emsp; Get all factors over the joining variable <br>
&emsp; Build a new factor over the union of the variables <br>
2. ELIMINATE <br>
&emsp; Take a factor and sum out a variable - marginalization <br>
&emsp; Shrinks a factor to a smaller one <br>
3. NORMALIZE <br>
&emsp; Take every probability in the last factor and divide it with the sum of all probabilities in the factor (including the one we are dividing). <br>
&emsp; The answer we are looking for is the probability of the query value. <br>



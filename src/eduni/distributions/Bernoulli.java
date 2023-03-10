package eduni.distributions;

public class Bernoulli extends Generator implements DiscreteGenerator {
    
    private double prob;

    
    public Bernoulli(double prob) {
	super();
	set(prob);
    }

    
    public Bernoulli(double prob, long seed) {
	super(seed);
	set(prob);
    }

    private void set(double prob) throws ParameterException {
	if ((prob < 0.0) || (prob > 1.0))
	    throw new ParameterException("Bernouilli: The probability of success must be between 0 and 1.");
	this.prob = prob;
    }

  
    public long sample() { return distrib.bernoulli(prob); }

 
    public String toString() { return "Bernoulli("+prob+")"; }
}


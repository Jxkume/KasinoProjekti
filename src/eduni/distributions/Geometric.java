package eduni.distributions;


public class Geometric extends Generator implements DiscreteGenerator {
    
    private double prob;
    
    
    public Geometric(double prob) {
        super();
        set(prob);
    }

    
    public Geometric(double prob, long seed) {
	super(seed);
	set(prob);
    }

    
    private void set(double prob) {
        if (prob<=0 || prob>=1)
            throw new ParameterException("Geometric: The probability of success must be between 0 and 1.");
        this.prob = prob;
    }

    
    public long sample() { 
         return distrib.geometric(prob);
    }
}

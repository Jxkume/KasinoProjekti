package eduni.distributions;


public class Binomial extends Generator implements DiscreteGenerator {
  
  private double prob;
  
  private int trials;

    public Binomial(double prob, int trials) {
	super();
	set(prob, trials);
    }

    public Binomial(double prob, int trials, long seed) {
	super(seed);
	set(prob, trials);
    }


    private void set(double prob, int trials) {
	if (prob <= 0.0)
	    throw new ParameterException("Binomial: The probability of success must be between 0 and 1.");
	if (trials <= 0)
	    throw new ParameterException("Binomial: The number of trials must be a positive integer.");
	this.prob = prob;
	this.trials = trials;
    }

 
    public long sample() { return distrib.binomial(prob, trials); }

 
    public String toString() { return "Binomial("+prob+", "+trials+")"; }
}


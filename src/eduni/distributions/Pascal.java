package eduni.distributions;



public class Pascal extends Generator implements DiscreteGenerator {
    
    private double prob;
    
    private int successes;
    
    
    public Pascal(double prob, int successes) {
        super();
        set(prob, successes);
    }

    
    public Pascal(double prob, int successes, long seed) {
        super(seed);
        set(prob, successes);
    }

    
    private void set(double prob, int successes) {
        if (prob<=0 || prob>=1)
            throw new ParameterException("Pascal: The probability of success must be between 0 and 1.");
        if (successes<=0)
            throw new ParameterException("Pascal: The number of successes must be a positive integer.");
        this.prob = prob;
        this.successes = successes;
    }

    
    public long sample() { 
         return distrib.pascal(prob, successes);
    }
}

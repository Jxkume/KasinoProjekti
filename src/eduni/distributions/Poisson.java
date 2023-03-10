package eduni.distributions;



public class Poisson extends Generator implements DiscreteGenerator {
    
    private double mean;
    
    
    public Poisson(double mean) {
        super();
        set(mean);
    }

    
    public Poisson(double mean, long seed) {
        super(seed);
        set(mean);
    }

    
    private void set(double mean) {
        if (mean<=0)
            throw new ParameterException("Poisson: The mean must be greater than 0.");
        this.mean = mean;
    }
    
    public long sample() { 
         return distrib.poisson(mean);
    }
}

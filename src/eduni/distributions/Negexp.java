package eduni.distributions;


public class Negexp extends Generator implements ContinuousGenerator {
    
    private double mean;
    
    
    public Negexp(double mean) {
        super();
        set(mean);
    }

    
    public Negexp(double mean, long seed) {
        super(seed);
        set(mean);
    }

   
    private void set(double mean) {
        if (mean<=0)
            throw new ParameterException("Negexp: The mean must be greater than 0.");
        this.mean = mean;
    }

    public double sample() { 
         return distrib.negexp(mean);
    }
}

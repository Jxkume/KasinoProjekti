package eduni.distributions;



public class Uniform extends Generator implements ContinuousGenerator {
    
    private double min;
    
    private double max;
    
    
    public Uniform(double min, double max) {
        super();
        set(min, max);
    }

    
    public Uniform(double min, double max, long seed) {
        super(seed);
        set(min, max);
    }

   
    private void set(double min, double max) {
        if (max<=min)
            throw new ParameterException("Uniform: The maximum must be greater than the minimum.");
        this.min = min;
        this.max = max;
    }

    
    public double sample() { 
         return distrib.uniform(min, max);
    }
}

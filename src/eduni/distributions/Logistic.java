package eduni.distributions;



public class Logistic extends Generator implements ContinuousGenerator {
    
    private double location;
    
    private double scale;
    
    
    public Logistic(double location, double scale) {
        super();
        set(location, scale);
    }

    
    public Logistic(double location, double scale, long seed) {
        super(seed);
        set(location, scale);
    }

    
    private void set(double location, double scale) {
        if (scale<=0)
            throw new ParameterException("Logistic: The scale parameter must be greater than 0.");
        this.location = location;
        this.scale = scale;
    }

    
    public double sample() { 
         return distrib.logistic(location, scale);
    }
}

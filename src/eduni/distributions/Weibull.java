package eduni.distributions;



public class Weibull extends Generator implements ContinuousGenerator {
    
    private double scale;
    
    private double shape;
    
    
    public Weibull(double scale, double shape) {
        super();
        set(scale, shape);
    }

    
    public Weibull(double scale, double shape, long seed) {
        super(seed);
        set(scale, shape);
    }

   
    private void set(double scale, double shape) {
        if (scale<=0 || shape <= 0)
            throw new ParameterException("Weibull:  Scale and shape parameters must be greater than 0.");
        this.scale = scale;
        this.shape = shape;
    }

    
    public double sample() { 
         return distrib.weibull(scale, shape);
    }
}

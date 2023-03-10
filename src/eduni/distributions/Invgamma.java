package eduni.distributions;


public class Invgamma extends Generator implements ContinuousGenerator {
    
    private double scale;
    
    private double shape;
    
    
    public Invgamma(double scale, double shape) {
        super();
        set(scale, shape);
    }

    
    public Invgamma(double scale, double shape, long seed) {
	    super(seed);
	    set(scale, shape);
    }

    
    private void set(double scale, double shape) {
        if (scale <= 0.0 || shape <= 0.0)
            throw new ParameterException("Invgamma: The scale and shape parameters must be greater than 0.");
        this.scale = scale;
        this.shape = shape;
    }

    
    public double sample() { 
         return distrib.invgamma(scale, shape);
    }
}

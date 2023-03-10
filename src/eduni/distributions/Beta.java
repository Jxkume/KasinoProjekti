package eduni.distributions;



public class Beta extends Generator implements ContinuousGenerator {
    
    protected double shape_a, shape_b;
    
 
    public Beta(double shape_a, double shape_b) {
	super();
	set(shape_a, shape_b);
    }


    public Beta(double shape_a, double shape_b, long seed) {
	super(seed);
	set(shape_a, shape_b);
    }

    private void set(double shape_a, double shape_b) {
	if ((shape_a <= 0.0) || (shape_b <= 0.0))
	    throw new ParameterException("Beta: The shape parameters must be greater than 0.");
	this.shape_a = shape_a;
	this.shape_b = shape_b;
    }

 
    public double sample() { return distrib.beta(shape_a, shape_b); }
}

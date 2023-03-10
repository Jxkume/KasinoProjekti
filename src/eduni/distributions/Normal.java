package eduni.distributions;


public class Normal extends Generator implements ContinuousGenerator {
    
    protected double mean, std_dev;
    
    
    public Normal(double mean, double variance) {
	super();
	set(mean, variance);
    }

    
    public Normal(double mean, double variance, long seed) {
	super(seed);
	set(mean, variance);
    }
    
    
    private void set(double mean, double variance) {
	if (variance <= 0.0)
	    throw new ParameterException("Normal: The variance must be greater than 0.");
	this.mean = mean;
	this.std_dev = Math.sqrt(variance);
    }

    
    public double sample() { return distrib.normal2(mean, std_dev); }
}

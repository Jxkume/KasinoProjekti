package eduni.distributions;


public class Cauchy extends Generator implements ContinuousGenerator {
    
    private double median, scale;
    

    public Cauchy(double median, double scale) {
	super();
	set(median, scale);
    }

    public Cauchy(double median, double scale, long seed) {
	super(seed);
	set(median, scale);
    }
    

    private void set(double median, double scale) {
	if (scale <= 0.0)
	    throw new ParameterException("Cauchy: The scale parameter must be greater than 0.");
	this.median = median;
	this.scale = scale;
    }

    public double sample() { return distrib.cauchy(median, scale); }
}

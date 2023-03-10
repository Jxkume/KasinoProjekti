package eduni.distributions;



public class FDistribution extends Generator implements ContinuousGenerator {
  
  private long num_deg_freedom, den_deg_freedom;

    
    public FDistribution(long num_deg_freedom, long den_deg_freedom) {
	super();
	set(num_deg_freedom, den_deg_freedom);
    }
    
    
    public FDistribution(long num_deg_freedom, long den_deg_freedom, long seed) {
	super(seed);
	set(num_deg_freedom, den_deg_freedom);
    }
    
    
    private void set(long num_deg_freedom, long den_deg_freedom) {
	if ((num_deg_freedom <= 0L) || (den_deg_freedom <= 0L))
	    throw new ParameterException("FDistribution: The degrees of freedom must be positive integers.");
	this.num_deg_freedom = num_deg_freedom;
	this.den_deg_freedom = den_deg_freedom;
    }

    
    public double sample() { 
	return distrib.f(num_deg_freedom, den_deg_freedom); 
    }
}

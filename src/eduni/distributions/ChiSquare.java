package eduni.distributions;



public class ChiSquare extends Generator implements ContinuousGenerator {
    
    private long deg_freedom;
  
    public ChiSquare(long deg_freedom) {
	super();
	set(deg_freedom);
    }


    public ChiSquare(long deg_freedom, long seed) {
	super(seed);
	set(deg_freedom);
    }
    

    private void set(long deg_freedom) {
	if (deg_freedom <= 0L)
	    throw new ParameterException("ChiSquare: The degrees of freedom must be a positive integer.");
	this.deg_freedom = deg_freedom;
    }
    
  
    public double sample() { return distrib.chisquare(deg_freedom); }
}

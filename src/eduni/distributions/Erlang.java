package eduni.distributions;



public class Erlang extends Generator implements ContinuousGenerator {
  
  private double scale, shape;
    
    
    public Erlang(double shape, double scale) {
	super();
	set(scale, shape);
    }

    
    public Erlang(double shape, double scale, long seed) {
	super(seed);
	set(scale, shape);
    }
    
    
    private void set(double scale, double shape) {
	if (scale <= 0.0)
	    throw new ParameterException("Erlang: The scale parameter must be greater than 0.");
	this.scale = scale;
	this.shape = shape;
    }

    
    public double sample() { return distrib.erlang(shape, scale); }
}

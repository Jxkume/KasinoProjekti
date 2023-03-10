package eduni.distributions;



public class Pareto extends Generator implements ContinuousGenerator {
    
    private double shape;
    
    private double scale;
    
    public Pareto(double scale, double shape) {
        super();
        set(shape, scale);
    }

    
    public Pareto(double scale, double shape, long seed) {
        super(seed);
        set(shape, scale);
    }

    
    private void set(double shape, double scale) {
        if (shape<=0 || scale<=0)
            throw new ParameterException("Pareto: Shape and scale parameters must be greater than 0.");
        this.shape = shape;
        this.scale = scale;
    }

    
    public double sample() { 
         return distrib.pareto(shape, scale);
    }
}

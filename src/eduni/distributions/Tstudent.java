package eduni.distributions;


public class Tstudent extends Generator implements ContinuousGenerator {
    
    private long deg_freedom;
    
    
    public Tstudent(long deg_freedom) {
        super();
        set(deg_freedom);
    }

    
    public Tstudent(long deg_freedom, long seed) {
        super(seed);
        set(deg_freedom);
    }

    
    private void set(long deg_freedom) {
        if (deg_freedom<=0)
            throw new ParameterException("Tstudent: The degrees of freedom must be a positive integer.");
        this.deg_freedom = deg_freedom;
    }

   
    public double sample() { 
         return distrib.tstudent(deg_freedom);
    }
}

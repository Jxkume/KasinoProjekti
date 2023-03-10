package eduni.distributions;

public class LogNormal extends Normal {
    
    
    public LogNormal(double mean, double variance) {
	super(mean, variance);
    }

    
    public LogNormal(double mean, double variance, long seed) {
	super(mean, variance, seed);
    }
    
    
    public double sample() { return distrib.lognormal2(mean, std_dev); }
}

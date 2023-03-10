package eduni.distributions;


public class BetaPrime extends Beta {
    

    public BetaPrime(double shape_a, double shape_b) { 
	super(shape_a, shape_b);
    }


    public BetaPrime(double shape_a, double shape_b, long seed) {
	super(shape_a, shape_b, seed);
    }

    public double sample() { return distrib.betaprime(shape_a, shape_b); }
}

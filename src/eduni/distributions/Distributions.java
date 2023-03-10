package eduni.distributions;

public class Distributions {
  
    ContinuousGenerator source;
    
  
    public Distributions() { source = new RandomGenerator(); }
    
   
    public Distributions(long seed) { source = new RandomGenerator(seed); }
    
  
    public Distributions(ContinuousGenerator gen) { source = gen; }

   
    public long bernoulli(double prob) { return source.sample()<=prob?1:0; }

  
    public long binomial(double prob, int trials) {
	long sum = 0l;
	for (int i=0; i<trials; i++) sum += bernoulli(prob);
	return sum;
    }

     
    public long geometric(double prob) {
	return (long)Math.ceil(Math.log(source.sample()) / Math.log(1.0-prob));
    }

   
    public long pascal(double prob, int successes) {
	long sum = 0L;
	for (int i=0; i < successes; i++)
	    sum += geometric(prob);
	return sum;
    }

    public long poisson(double mean) {
	long x = -1L;
	double m = Math.exp(-mean), product=1;
	do {
	    x++;
	    product *= source.sample();
	} while(m < product);
	return x;
    }
    
  
    private double power(double a) {
	return Math.pow(source.sample(), 1.0/a);
    }
    
    public double beta(double shape_a, double shape_b) {
	if (shape_a == 1.0)
	    return 1.0-power(shape_b);
	if (shape_b == 1.0)
	    return power(shape_a);
	
	if ((shape_a > 1.0) || (shape_b > 1.0)) {
	    // Cheng's method (1978)
	    double alpha = shape_a + shape_b;
	    double min = Math.min(shape_a, shape_b);
	    double beta;
	    if (min <= 1.0) beta = 1.0/min;
	    else
		beta = Math.sqrt((alpha - 2.0)/(2.0*shape_a*shape_b - alpha));
	    
	    double gamma = shape_a + 1.0/shape_b;
	    double w, el1, el2;
	    do {
		double u1 = source.sample(), 
		    u2 = source.sample(),
		    u3 = source.sample();
		double v = beta*Math.log(u1/(1.0-u1));
		w = alpha*Math.exp(v);
		el1 = alpha*Math.log(alpha/(beta+w)) + gamma*v - Math.log(4.0);
		el2 = Math.log(u1*u2*u3);
	    } while(el1 < el2);
	    return w/(beta+w);
	} 
	
	double x, y;
	do {
	    x = power(shape_a);
	    y = power(shape_b);
	} while((x + y) > 1.0);
	return x/(x+y);
    }


    public double betaprime(double shape_a, double shape_b) {
	return 1.0/beta(shape_a, shape_b) - 1.0;
    }
    
 
    public double cauchy(double median, double scale) {
	return median + scale/Math.tan(Math.PI*source.sample());
    }

    public double chisquare(long deg_freedom) {
	double result = 0.0;
	for (long i=0L; i < deg_freedom; i++)
	    result += normal2(0.0, 1.0);
	return result;
    }
    

    public double erlang(double scale, double shape) {
	double product = 1.0;
	for (int i=0; i<shape; i++)
	    product *= source.sample();
	return -scale * Math.log(product);
    }

    public double f(long num_deg_freedom, long den_deg_freedom) {
	return (chisquare(num_deg_freedom)/num_deg_freedom) 
	    / (chisquare(den_deg_freedom)/den_deg_freedom);
    }

    
    public double gamma(double scale, double shape) {
	if (shape == Math.floor(shape)) {
	    double product = 1.0;
	    for (long i=0L; i < shape; i++)
	    product *= source.sample();
	    
	    return -scale*Math.log(product);
	} 
	if (shape < 1.0)
	    return scale * beta(shape, 1.0-shape) * negexp(1.0);
	
	double floor = Math.floor(shape);
	return gamma(scale, floor) + gamma(scale, shape-floor);
    }
    
    
    public double invgamma(double scale, double shape) { 
	return 1.0/gamma(scale, shape); 
    }
    
    
    public double logistic(double location, double scale) {
	return location-scale*Math.log((1/source.sample())-1);
    }
    
    
    public double lognormal(double mean, double variance) { 
	return lognormal2(mean, Math.sqrt(variance));
    }

    
    public double lognormal2(double mean, double std_dev) { 
	return Math.exp(mean+std_dev*normal2(0.0, 1.0));
    }

    
    public double negexp(double mean) {
	return -mean * Math.log(source.sample());
    }
    
    
    public double normal(double mean, double variance) { 
	return normal2(mean, Math.sqrt(variance));
    }

    
    public double normal2(double mean, double std_dev) {
	double u1 = source.sample(), u2 = source.sample();
	return mean + std_dev * Math.cos(2 * Math.PI * u1) * Math.sqrt(-2 * Math.log(u2));
    }

    
    public double pareto(double scale, double shape) { 
	return scale / Math.pow(source.sample(), 1/shape);
    }

    
    public double tstudent(long deg_freedom) {
	return normal2(0.0, 1.0)/Math.sqrt(chisquare(deg_freedom)/deg_freedom);
    }

    
    public double uniform(double min, double max) {
	return (max-min) * source.sample() + min;
    }

    
    public double weibull(double scale, double shape) {
	return scale * Math.pow(Math.log(source.sample()), 1/shape);
    }
}


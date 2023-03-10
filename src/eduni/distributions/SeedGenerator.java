package eduni.distributions;


public class SeedGenerator implements DiscreteGenerator {
    
    private static long root = 4851L; 
    
    
    private int spacing;
    
    private boolean not_sampled = true; 
    
    
    private RandomGenerator source;

   
    public SeedGenerator () { this(root, 100000); } 
    
    
    public SeedGenerator (long seed, int spacing) { 
	source = new RandomGenerator(seed);
	this.spacing = spacing;
    }

    
    public void setSeed(long seed) { source.setSeed(seed); not_sampled=true; }
    
    
    public long getSeed() { return source.getSeed(); }
    
   
    public void reseed() { source.reseed(); not_sampled=true; }
    
    public long sample() {
	if (not_sampled) not_sampled = false;
	else
	    for (int i=0; i<spacing; i++) source.nextLong();
	return getSeed();
    }

    private static SeedGenerator defaut = new SeedGenerator();
    
    
    static SeedGenerator getDefaultSeedGenerator() { return defaut; }
    
    
    static void setDefaultSeedGenerator(long seed, int spacing) {
	defaut = new SeedGenerator(seed, spacing);
    }
}

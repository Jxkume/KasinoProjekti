package eduni.distributions;



public class RandomGenerator implements ContinuousGenerator {
    

    private final long a = 742938285;
    

    private final long m = 2147483647;
    
    
    private long seed;
    
    
    public RandomGenerator () { reseed(); }
    
   
    public RandomGenerator (long seed) { setSeed(seed); }
    
    public double sample() { 
	return ((double)nextLong()) / m;
    }
   
    public long nextLong() {
	return seed = (a * seed) % m;
    }
    
    
    public void setSeed(long seed) { this.seed = seed; }
    
   
    public long getSeed() { return seed; }
    
    
    public void reseed() { this.seed = SeedGenerator.getDefaultSeedGenerator().sample(); }
}

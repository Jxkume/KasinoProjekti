package eduni.distributions;


public abstract class Generator implements Seedable {
    
    protected Distributions distrib;
    
    
    Generator () { distrib = new Distributions(); }
    
    
    Generator (long seed) { distrib = new Distributions(seed); }
    
    
    public void setSeed(long seed) { distrib.source.setSeed(seed); }
    
    
    public long getSeed() { return distrib.source.getSeed(); }
    
    
    public void reseed() { distrib.source.reseed(); }
}

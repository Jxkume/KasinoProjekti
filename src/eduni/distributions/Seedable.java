package eduni.distributions;


public interface Seedable {
  
    void setSeed(long seed);
    
   
    long getSeed();

    
    void reseed();
}

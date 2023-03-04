package eduni.distributions;


import jakarta.persistence.SequenceGenerator;

/** A continuous generator provides a double value according to the distribution it relies on.
 */
public interface ContinuousGenerator extends Seedable {
    double sample(); 
}

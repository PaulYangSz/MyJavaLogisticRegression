/**
 * This is the abstract father class of each other optimization methods.
 */

/**
 * @author Yang
 *
 */
public abstract class OptiMethod {
    abstract double[] doOpti(MapFunction mapF);
    abstract double[] doOpti(MapFunction mapF, int iteration);
}

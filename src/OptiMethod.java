/**
 * This is the abstract father class of each other optimization methods.
 */

/**
 * @author Yang
 *
 */
public abstract class OptiMethod {
    /**
     * 
     * @param mapF
     */
    abstract void doOpti(MapFunction mapF);
    
    /**
     * This method is used to get trained mapF.theta[]
     * @param mapF: map function, include Xdata[], Ydata[], factors and theta[]
     * @param alpha: optimization used a coefficient.
     * @param iteration: run times
     */
    abstract void doOpti(MapFunction mapF, double alpha, int iteration);
    
    /**
     * Add weight to train mapF.theta[]
     * @param mapF: map function, include Xdata[], Ydata[], factors and theta[]
     * @param alpha: optimization used a coefficient.
     * @param iteration: run times
     * @param w: weight to every x_i
     */
    abstract void doOpti(MapFunction mapF, double alpha, int iteration, double[] w);
}

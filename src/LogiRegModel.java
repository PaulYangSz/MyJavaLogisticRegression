/**
 * This is a Logistic Regression Model, which means have a Map-Function and trained Theta
 */

/**
 * @author Yang
 *
 */
public class LogiRegModel {
    
    MapFunction mapF;
    private OptiMethod  optiM;
    /**
     * Need a MapFuction(with data) and a optimization-method to construct
     * @param 
     */
    public LogiRegModel (MapFunction mapFunc, OptiMethod optiMethod) {
        mapF = mapFunc;
        optiM = optiMethod;
    }
    
    /**
     * Use doOpti(MapFunction mapF, double alpha, int iteration) to do
     * @param alpha: a coefficient used in optimization method
     * @param iteration: times to calculate
     */
    public void startOpti(double alpha, int iteration) {
        optiM.doOpti(mapF, alpha, iteration);
    }
}

/**
 * This is a Logistic Regression Model, which means have a Map-Function and trained Theta
 */

/**
 * @author Yang
 *
 */
public class LogiRegModel {
    
    private MapFunction mapF;
    private OptiMethod  optiM;
    /**
     * Need a MapFuction(with data) and a optimization-method to construct
     * @param 
     */
    public LogiRegModel (MapFunction mapFunc, OptiMethod optiMethod) {
        mapF = mapFunc;
        optiM = optiMethod;
    }
}

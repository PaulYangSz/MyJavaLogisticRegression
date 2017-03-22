/**
 * This is a gradient-decent method to get the optimal Theta
 */

/**
 * @author Yang
 *
 */
public class GradientDecent extends OptiMethod{

    /**
     * No need to construct only provide the Gradient Decent method.
     */
    public GradientDecent() {
        // TODO Auto-generated constructor stub
    }

    @Override
    /**
     * Do gradient decent, stop condition is the iteration.
     */
    double[] doOpti(MapFunction mapF, int iteration) {
        //Total calculate iteration times
        for(int iter = 0; iter < iteration; iter++) {
            
            //GD the theta_j
            for(int i = 0; i < mapF.yData.length; i++) {
                
            }
        }
        return null;
    }

    @Override
    /**
     * Do gradient decent, stop condition is the minus value of cost-function.
     */
    double[] doOpti(MapFunction mapF) {
        // TODO Auto-generated method stub
        return null;
    }

}

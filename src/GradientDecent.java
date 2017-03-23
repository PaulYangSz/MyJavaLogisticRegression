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
    void doOpti(MapFunction mapF, double alpha, int iteration) {
        //Total calculate iteration times
        for(int iter = 0; iter < iteration; iter++) {
            
            //GD the theta_j
            for(int j = 0; j < mapF.thetaLen; j++) {
                double tmpSum = 0;
                for(int i = 0; i < mapF.yData.length; i++)
                {
                    double hypothesis = MyMathApi.sigmoid(mapF.thetaTx(i));
                    double error = hypothesis - mapF.yData[i];
                    double x_j = mapF.facList.get(j).calcXj(i);
                    tmpSum += error * x_j;
                }
                mapF.theta[j] -= alpha * tmpSum;
            }
        }
    }

    @Override
    /**
     * Do gradient decent, stop condition is the minus value of cost-function.
     */
    void doOpti(MapFunction mapF) {
        // TODO Auto-generated method stub
        return;
    }

}

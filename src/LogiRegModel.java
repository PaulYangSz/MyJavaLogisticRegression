/**
 * This is a Logistic Regression Model, which means have a Map-Function and trained Theta
 */

/**
 * @author Yang
 *
 */
public class LogiRegModel {
    
    MapFunction mapF;
    OptiMethod  optiM;
    /**
     * Need a MapFuction(with data) and a optimization-method to construct
     * @param 
     */
    public LogiRegModel (MapFunction mapFunc, OptiMethod optiMethod) {
        mapF = mapFunc;
        optiM = optiMethod;
    }
    
    public LogiRegModel(LogiRegModel origLR) {
        // TODO Auto-generated constructor stub
        if(origLR.mapF instanceof LinearMapFunction) {
            mapF = new LinearMapFunction(origLR.mapF);
        }
        else if(origLR.mapF instanceof NonlinearMapFunction) {
            mapF = new NonlinearMapFunction(origLR.mapF);
        }
        else {
            assert(false) : "Can't construct a unknown map function";
        }
        
        optiM = origLR.optiM;
    }

    /**
     * Use doOpti(MapFunction mapF, double alpha, int iteration) to do
     * @param alpha: a coefficient used in optimization method
     * @param iteration: times to calculate
     */
    public void startOpti(double alpha, int iteration) {
        System.out.println("Before optimization cost is " + this.costValue());
        optiM.doOpti(mapF, alpha, iteration);
        System.out.println("After optimization cost is " + this.costValue());
    }

    /**
     * Use doOpti(MapFunction mapF, double alpha, int iteration, double[] w) to do
     * @param alpha
     * @param iteration
     * @param weights
     */
    public void startOpti(double alpha, int iteration, double[] weights) {
        System.out.println("Before optimization cost(with w[]) is " + this.costValue(weights));
        optiM.doOpti(mapF, alpha, iteration, weights);
        System.out.println("After optimization cost(with w[]) is " + this.costValue(weights));
    }

    public double predictProb(double[] testIn) {
        return MyMathApi.sigmoid(mapF.thetaTx(testIn));
    }
    

    public int predictClassify(double[] testIn) {
        assert(testIn.length == mapF.xData[0].length) : "In size=" + testIn.length + ", X[].len="+mapF.xData[0].length;
        return (MyMathApi.sigmoid(mapF.thetaTx(testIn)) > 0.5) ? 1 : 0;
    }
    
    public double costValue() {
        double cost = 0;
        double hypothesis = 0;
        for(int i = 0; i < mapF.yData.length; i++) {
            hypothesis = MyMathApi.sigmoid(mapF.thetaTx(i));
            if(mapF.yData[i] == 1) {
                cost += Math.log(hypothesis);
            }
            else {
                cost += Math.log(1 - hypothesis);
            }
        }
        
        return -(cost/mapF.yData.length);
    }

    public double costValue(double[] weights) {
        double cost = 0;
        double hypothesis = 0;
        for(int i = 0; i < mapF.yData.length; i++) {
            hypothesis = MyMathApi.sigmoid(mapF.thetaTx(i));
            if(mapF.yData[i] == 1) {
                cost += weights[i] * Math.log(hypothesis);
            }
            else {
                cost += weights[i] * Math.log(1 - hypothesis);
            }
        }
        
        return -cost;
    }
    
    public double calcErrM(double[] weights) {
        double errM = 0;
        for(int i = 0; i < mapF.xData.length; i++) {
            if(predictClassify(mapF.xData[i]) != mapF.yData[i]) {
                errM += weights[i];
            }
        }
        
        return errM;
    }
}

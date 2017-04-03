import java.util.ArrayList;

/**
 * AdaBoost algorithm procedure.
 */

/**
 * @author Yang Peng
 *
 */
public class AdaBoostModel {
    LogiRegModel untrainedBaseLR = null;
    int maxBmNum; // maximum number of base models.
    int realBmNum; // really number of base models, for break if error rate > 0.5
    double[] alphaM; // Gm(X)'s coefficient. Gm(X) is a base classifier.
    ArrayList<LogiRegModel> baseModelSet = new ArrayList<LogiRegModel>();
    
    public AdaBoostModel(int maxBaseModelNum, LogiRegModel untrainedLrModel) {
        untrainedBaseLR = untrainedLrModel;
        maxBmNum = maxBaseModelNum;
        alphaM = new double[maxBaseModelNum];
    }
    
    public void startBoost() {
        System.out.println("start Ada Boost...");
        double[] weights = new double[untrainedBaseLR.mapF.yData.length]; //weight to every x_i
        for(int i = 0; i < untrainedBaseLR.mapF.yData.length; i++) {
            weights[i] = 1.0/untrainedBaseLR.mapF.yData.length;
        }

        int opriIterTimes = 100; //maybe only need a weak classifier.
        double alpha = 1.0; //without w[i] alpha is 0.001, then maybe 1 with w[i]
        for(int mIdx = 0; mIdx < maxBmNum; mIdx++) {
            int maxTryTrainBaseNum = 3; //Max-times try to get a base with err < 0.45
            double errM  = 0.0; //error rate with w[mIdx]
            
            //Get one base classifier with w[mIdx]
            LogiRegModel baseLogiRegModel = new LogiRegModel(untrainedBaseLR);
            baseLogiRegModel.startOpti(alpha, opriIterTimes, weights);
            
            //Calculate the error rate with w[mIdx]
            for(int i = 1; i < maxTryTrainBaseNum; i++) {
                errM = baseLogiRegModel.calcErrM(weights);
                System.out.println(mIdx + "th Base Model's errM = " + errM);
                if(errM > 0.45) {
                    System.out.println("Need startOpti again with IterTimes=" + opriIterTimes * (int)Math.pow(2, i));
                    baseLogiRegModel.startOpti(alpha, opriIterTimes * (int)Math.pow(2, i), weights);
                }
                else {
                    break;
                }
                assert(errM > 0) : "Unbelievable you just got a 'perfect' model.";
            }
            
            if(errM > 0.5) {
                break;
            }
            
            //Get the model[mIdx]'s coefficient.
            alphaM[mIdx] = 0.5 * Math.log((1-errM) / errM);
            System.out.println(mIdx + "th Base Model's alphaM = " + alphaM[mIdx]);
            baseModelSet.add(baseLogiRegModel);
            
            // Update the w[mIdx + 1]
            double zM = 0.0; //standardization factor, make next w[] be a distribution.
            for(int i = 0; i < untrainedBaseLR.mapF.yData.length; i++) {
                int signY = untrainedBaseLR.mapF.yData[i] == 0 ? -1 : 1;
                int signG = baseLogiRegModel.predictClassify(untrainedBaseLR.mapF.xData[i]) == 0 ? -1 : 1;
                double tmpExp = Math.exp(-alphaM[mIdx] * signY * signG);
                zM += weights[i] * tmpExp;
                
                weights[i] *= tmpExp;
            }
            System.out.println(mIdx+1 + "th Weights[]:");
            for(int i = 0; i < untrainedBaseLR.mapF.yData.length; i++) {
                weights[i] = weights[i]/zM;
                //System.out.println(weights[i]);
            }
            System.out.println("~~~Get base model " + mIdx);
        }
        
        realBmNum = baseModelSet.size();
        
        if(DebugConfig.PRINT_ADABOOST_PARA) {
            System.out.println("Complete the boost, with real num = " + realBmNum);
            for(int i = 0; i < realBmNum; i++) {
                System.out.printf("alpha[%d] = %f\n", i, alphaM[i]);
                for(int j = 0; j < baseModelSet.get(i).mapF.theta.length; j++) {
                    System.out.println(baseModelSet.get(i).mapF.theta[j]);
                }
                System.out.println("---");
            }
        }
    }
    
    /**
     * Return {0, 1} classify result.
     * @param xIn
     * @return 0 or 1
     */
    public int predictClassify(double[] xIn) {
        assert(xIn.length == untrainedBaseLR.mapF.xData[0].length) : "In size=" + xIn.length + ", X[].len="+untrainedBaseLR.mapF.xData[0].length;
        double totalPredict = 0;
        for(int i = 0; i < realBmNum; i++) {
            int tempSignG = baseModelSet.get(i).predictClassify(xIn) == 0 ? -1 : 1;
            totalPredict += alphaM[i] * tempSignG;
        }
        return (totalPredict > 0) ? 1 : 0;
    }
}

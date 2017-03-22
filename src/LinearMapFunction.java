/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class LinearMapFunction extends MapFunction{

    /**
     * 
     */
    public LinearMapFunction(FileHelper inFhData, String strXselct) {
        // TODO Auto-generated constructor stub
        super(inFhData, strXselct, 1);
    }

    @Override
    double thetaTx(int xIdx) {
        
        double result = theta[0];
        
        //in linear thetaLen = xNum+1
        for(int i = 1; i < thetaLen; i++) {
            result += theta[i] * xData[xIdx][xFeaIdx[i-1]];
        }
        return result;
    }

}

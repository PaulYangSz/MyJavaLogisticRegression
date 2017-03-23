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
        
        double result = 0;
        
        //in linear thetaLen = xNum+1
        for(int i = 0; i < thetaLen; i++) {
            result += theta[i] * facList.get(i).calcXj(xIdx);
        }
        return result;
    }

}

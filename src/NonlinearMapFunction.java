/**
 * 
 */

/**
 * @author Yang
 *
 */
public class NonlinearMapFunction extends MapFunction {

    /**
     * 
     */
    public NonlinearMapFunction(FileHelper inFhData, String strXselct, int degree) {
        // TODO Auto-generated constructor stub
        super(inFhData, strXselct, degree);
    }

    public NonlinearMapFunction(MapFunction origMapF) {
        // TODO Auto-generated constructor stub
        super(origMapF);
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

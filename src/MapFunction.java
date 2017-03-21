/**
 * This class provide a map function model which can be extended to linear or nonlinear.
 */

/**
 * @author Administrator
 * Define the x number and x index which selected as key features
 * Define the degree of x
 * Define the xData[][] and yData[]
 * Means have training data and function but need theta[]
 */
public abstract class MapFunction {
    int xNum = 0;
    int[] xFeaIdx;
    int degree = 0;
    double[][] xData;
    int[] yData;
    
    public MapFunction(FileHelper inFhData, String strXselct, int degree) {
        assert(degree > 0);
        this.degree = degree;
        
        if(strXselct.equals("") == true) {
            xNum = inFhData.columNum - 1;
            xFeaIdx = new int[xNum];
            for(int i=0; i< xNum; i++) {
                xFeaIdx[i] = i;
            }
        }
        else{
            String[] strIdx = strXselct.split(",");
            xNum = strIdx.length;
            xFeaIdx = new int[xNum];
            for(int i=0; i < xNum; i++) {
                xFeaIdx[i] = Integer.parseInt(strIdx[i]);
            }
        }
        
        xData = new double[inFhData.rowNum][inFhData.columNum - 1];
        yData = new int[inFhData.rowNum];
        for(int i=0; i < inFhData.rowNum; i++) {
            for(int j=0; j < inFhData.columNum - 1; j++) {
                xData[i][j] = inFhData.data.get(i)[j];
            }
            yData[i] = (int) inFhData.data.get(i)[inFhData.columNum-1];
        }
    }
    
    /**
     * This method will implements in linear and nonlinear map-functions
     * Input theta and Xi, calculate the dot-product.
     */
    abstract double thetaTx();
}

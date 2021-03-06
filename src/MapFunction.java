import java.util.ArrayList;

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
    int xFeaNum = 0; //number of x which selected be feature.
    int[] xFeaIdx; //feature x's index array
    int degree = 0; //max power of x, 1 means linear
    double[][] xData; //All input X data
    int[] yData; //label value of x_i
    double[] theta; //coefficient of function factors
    int thetaLen = 0; //number of function factors
    ArrayList<XjFatorOfTheta> facList = new ArrayList<XjFatorOfTheta>(); //factors
    
    public MapFunction(FileHelper inFhData, String strXselct, int degree) {
        assert(degree > 0);
        this.degree = degree;
        
        if(strXselct.equals("") == true) {
            xFeaNum = inFhData.columNum - 1;
            xFeaIdx = new int[xFeaNum];
            for(int i=0; i< xFeaNum; i++) {
                xFeaIdx[i] = i;
            }
        }
        else{
            String[] strIdx = strXselct.split(",");
            xFeaNum = strIdx.length;
            xFeaIdx = new int[xFeaNum];
            for(int i=0; i < xFeaNum; i++) {
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
        
        genTheta();
        for(int d = 0; d <= degree; d++)
        {
            int[] xPweV = new int[xFeaNum];
            genXjOfTheta(xPweV, d, 0); // G2J !!!!!!!
        }
        assert(facList.size() == thetaLen) : "size="+facList.size() + ", len="+thetaLen;
        if(DebugConfig.PRINT_THETA_INFO) System.out.println("Theta's factors: " + facList);
    }
    

    public MapFunction(MapFunction origMf) {
        degree = origMf.degree;
        
        xFeaNum = origMf.xFeaNum;
        xFeaIdx = new int[xFeaNum];
        /**
         * Do not make a mistake: arraycopy has a reverse src and dest order compared by memcpy!!!
         */
        System.arraycopy(origMf.xFeaIdx, 0, xFeaIdx, 0, xFeaNum); //Full of tears.... src ahead, des behind
        
        xData = new double[origMf.xData.length][origMf.xData[0].length];
        yData = new int[origMf.yData.length];
        for(int i=0; i < origMf.yData.length; i++) {
            System.arraycopy(origMf.xData[i], 0, xData[i], 0, origMf.xData[0].length);
        }
        System.arraycopy(origMf.yData, 0, yData, 0, origMf.yData.length);
        
        thetaLen = origMf.thetaLen;
        theta = new double[thetaLen]; //Get initial theta[]
        facList.addAll(origMf.facList); //Not need different, so not copy.
        assert(facList.size() == thetaLen) : "size="+facList.size() + ", len="+thetaLen;
        //if(DebugConfig.PRINT_THETA_INFO) System.out.println("Theta's factors: " + facList);
    }
    
    /**
     * This method will implements in linear and nonlinear map-functions
     * Input Xi, calculate the x[Xi] and Theta[]'s dot-product.
     * @param xIdx: use which xi to dot-product with theta
     * But now needn't override....
     */
    abstract double thetaTx(int xIdx);
    
    double thetaTx(double[] testData){
        
        double result = 0;
        
        //in linear thetaLen = xNum+1
        for(int i = 0; i < thetaLen; i++) {
            result += theta[i] * facList.get(i).calcXj(testData);
        }
        return result;
    }
    
    /**
     * Generate the theta array, and initial the value to 0
     */
    private void genTheta() {
        for(int i = 0; i <= degree; i++){
            /**
             * numI same Balls put in numN diff Boxes = C(numI+numN-1, numN-1)
             */
            thetaLen += MyMathApi.Combi(xFeaNum+i-1, xFeaNum-1);
        }
        theta = new double[thetaLen]; //Get initial theta[]
    }
    
    /**
     * This method is to generate the Theta_j's x_j factor
     */
    private void genXjOfTheta(int[] xiPwerV, int curDegree, int curXIdx) {
        //Generate the theta[]'s factor list.
        assert(MyMathApi.sumInt(xiPwerV) <= curDegree);
        
        if(MyMathApi.sumInt(xiPwerV) == curDegree){
            facList.add(new XjFatorOfTheta(xiPwerV));
            return;
        } else if(curXIdx == xFeaNum) {
            return;
        }
        else {
            for(int d = curDegree - MyMathApi.sumInt(xiPwerV); d >= 0; d--) {
                xiPwerV[curXIdx] = d; //traversal the x[i]'s power
                if(curXIdx < xFeaNum) {
                    genXjOfTheta(xiPwerV, curDegree, curXIdx+1);
                }
            }
        }
    }
    
    /**
     * 
     * @author Administrator
     * This class record Theta_j's factor, and can provide calculate the value by the given x_i 
     */
    class XjFatorOfTheta {
        int[] xVecPwer;
        
        public XjFatorOfTheta(int[] xiPwerV) {
            xVecPwer = new int[xFeaNum];
            for(int i = 0; i < xFeaNum; i++){
                xVecPwer[i] = xiPwerV[i];
            }
        }
        
        /**
         * Calculate the Theta[j]'s factor(X[i])
         * @param idx = i
         * @return value of Theta_j's factor(X[i])
         */
        public double calcXj(int idx) {
            double result = 1.0;
            for(int i=0; i < xFeaNum; i++) {
                result *= Math.pow(xData[idx][xFeaIdx[i]], xVecPwer[i]);
            }
            return result;
        }
        
        /**
         * Calculate the Theta[j]'s factor(testData[])
         * @param testData = testData[]
         * @return value of Theta_j's factor(testData[])
         */
        public double calcXj(double[] testData) {
            double result = 1.0;
            for(int i=0; i < xFeaNum; i++) {
                result *= Math.pow(testData[xFeaIdx[i]], xVecPwer[i]);
            }
            return result;
        }
        
        public String toString() {
            StringBuffer strB = new StringBuffer("(");
            for(int i = 0; i < xFeaNum - 1; i++) {
                strB.append(xVecPwer[i]+ ", ");
            }
            strB.append(xVecPwer[xFeaNum - 1]);
            strB.append(")");
            
            return strB.toString();
        }
    }
}

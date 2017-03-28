/**
 * This class is used to input the map-function type and training data
 * to get a trained model than output the predict result.
 */

/**
 * @author Paul.Yang
 * @Email  shohokuooo@gmail.com
 * @Github https://github.com/PaulYangSz
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    
        FileHelper readData = null;
        LinearMapFunction aLinearFunc = null;
        NonlinearMapFunction aNonlinearFunc = null;
        LogiRegModel   commGdModel = null;
        
		/** 
		 * Fist: save the data read from a file.
		 */
	    if(DebugConfig.RUN_LINEAR_CASE) {
	        readData = new FileHelper("./data/HxyLinearData1.txt");
	    }
	    else {
	        readData = new FileHelper("./data/HxyNonlinearData2.txt");
	    }
	    
	    /**
	     * Second: put the data into a Map-Function
	     */
	    if(DebugConfig.RUN_LINEAR_CASE) {
	        aLinearFunc = new LinearMapFunction(readData, "");
	    }
	    else {
	        aNonlinearFunc = new NonlinearMapFunction(readData, "", 6);
	    }
	    
	    /**
	     * Third: Use a optimization method to train this Map-Function
	     *        and get the Theta of Map-Function
	     */
	    GradientDecent useGdOpti = new GradientDecent();
        if(DebugConfig.RUN_LINEAR_CASE) {
            commGdModel = new LogiRegModel(aLinearFunc, useGdOpti);
        }
        else{
            commGdModel = new LogiRegModel(aNonlinearFunc, useGdOpti);
        }
	    int opriIterTimes = 10000;
	    double alpha = 0.001;
        System.out.printf("Begin optimizing with alpha=%f, iter_times=%d\n", alpha, opriIterTimes);
	    commGdModel.startOpti(alpha, opriIterTimes);
	    System.out.printf("After optimization, Theta[%d]:\n", commGdModel.mapF.theta.length);
	    for(int j = 0; j < commGdModel.mapF.theta.length; j++) {
	        System.out.println(commGdModel.mapF.theta[j]);
	    }
	    System.out.println("============");
	    
	    /**
	     * Fourth: Use this trained model to predict a test data set.
	     */
	    int sumPrediCorr = 0;
	    for(int i = 0; i < commGdModel.mapF.xData.length; i++) {
	        if(commGdModel.predictClass(commGdModel.mapF.xData[i]) == commGdModel.mapF.yData[i]) {
	            sumPrediCorr++;
	        }
	        else
	        {
	            System.out.println("i= "+ i +",Predi = "+commGdModel.predictClass(commGdModel.mapF.xData[i])+ ", Y= "+commGdModel.mapF.yData[i]);
	        }
	    }
	    double prediRate = (double)sumPrediCorr / commGdModel.mapF.xData.length;
	    System.out.println("Finally our model predict correct rate is " + prediRate + ",num = " + sumPrediCorr);
	}

}

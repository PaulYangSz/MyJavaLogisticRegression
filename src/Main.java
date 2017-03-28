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
		/** 
		 * Fist: save the data read from a file.
		 */
	    FileHelper readData = new FileHelper("./data/HxyLinearData1.txt");
	    
	    /**
	     * Second: put the data into a Map-Function
	     */
	    LinearMapFunction aLinearFunc = new LinearMapFunction(readData, "");
	    
	    /**
	     * Third: Use a optimization method to train this Map-Function
	     *        and get the Theta of Map-Function
	     */
	    GradientDecent useGdOpti = new GradientDecent();
	    LogiRegModel   linearGdModel = new LogiRegModel(aLinearFunc, useGdOpti);
	    linearGdModel.startOpti(0.001, 100000);
	    System.out.printf("After (alpha=%f,inter=%d)optimization, Theta[%d]:\n", 0.001,100000,linearGdModel.mapF.theta.length);
	    for(int j = 0; j < linearGdModel.mapF.theta.length; j++) {
	        System.out.println(linearGdModel.mapF.theta[j]);
	    }
	    System.out.println("============");
	    
	    /**
	     * Fourth: Use this trained model to predict a test data set.
	     */
	    int sumPrediCorr = 0;
	    for(int i = 0; i < linearGdModel.mapF.xData.length; i++) {
	        if(linearGdModel.predictClass(linearGdModel.mapF.xData[i]) == linearGdModel.mapF.yData[i]) {
	            sumPrediCorr++;
	        }
	        else
	        {
	            System.out.println("i= "+ i +",Predi = "+linearGdModel.predictClass(aLinearFunc.xData[i])+ ", Y= "+aLinearFunc.yData[i]);
	        }
	    }
	    double prediRate = (double)sumPrediCorr / linearGdModel.mapF.xData.length;
	    System.out.println("Finally our model predict correct rate is " + prediRate + ",num = " + sumPrediCorr);
	}

}

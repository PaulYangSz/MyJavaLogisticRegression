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
	     * and get the Theta of Map-Function
	     */
	    GradientDecent useGdOpti = new GradientDecent();
	    LogiRegModel   linearGdModel = new LogiRegModel(aLinearFunc, useGdOpti);
	    linearGdModel.startOpti(0.001, 500);
	    System.out.printf("After optimization, Theta[%d]:\n", linearGdModel.mapF.theta.length);
	    for(int j = 0; j < linearGdModel.mapF.theta.length; j++) {
	        System.out.println(linearGdModel.mapF.theta[j]);
	    }
	    System.out.println("============");
	    
	    /**
	     * Fourth: Use this trained model to predict a test data set.
	     */
	}

}

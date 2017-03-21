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
		// Fist save the data read from a file.
	    FileHelper readData = new FileHelper("data1.txt");
	    
	    LinearMapFunction aLinearFunc = new LinearMapFunction(readData, "");
	}

}

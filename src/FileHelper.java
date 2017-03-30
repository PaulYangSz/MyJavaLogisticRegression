import java.io.*;
import java.util.ArrayList;

/**
 * This class is used to read data from files.
 * and output result to a .csv file which can meet the Kaggle required format.
 */

/**
 * @author Administrator
 *
 */
public class FileHelper {
    public static final String ENCODE = "UTF-8";  
    private FileInputStream fis = null;  
    private InputStreamReader isw = null;  
    private BufferedReader br = null;  
    
	int columNum = 0;
	int rowNum = 0;
	public ArrayList<double []> data = new ArrayList<double []>();
	
	private void readFromFile(String fileName) {
        String line = null;
	    try {
	        fis = new FileInputStream(fileName);  
	        isw = new InputStreamReader(fis, ENCODE);  
	        br  = new BufferedReader(isw);
	        
	        System.out.println("Start read file: "+fileName+" and pass the first line.");
	        line = br.readLine(); //Pass the first line.
            String fitstItem[] = line.split(",");
            columNum = fitstItem.length;
	        while( (line = br.readLine() )!=null ){ 
	            double[] oneLineArry = new double[columNum];
	            String item[] = line.split(",");
	            for(int i=0; i < columNum; i++) {
	                oneLineArry[i] = Double.parseDouble(item[i]);
	            }

	            if(DebugConfig.PRINT_READ_DATA) {
                    System.out.print(rowNum+1 + "\t");
    	            for(int i = 0; i < columNum; i++) {
    	                System.out.print(oneLineArry[i]+"\t");
    	            }
    	            System.out.println();
	            }
	            
                data.add(oneLineArry);
	            rowNum++;
	        } 
	        fis.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}
	
	public FileHelper (String fileName) {
	    readFromFile(fileName);
	}
	
	/**
	 * Write result to a csv format file.
	 * @param firstLine: In Titanic means "PassengerId,Survived"
	 * @param elementID: first column's value
	 * @param predict: sencond column's value
	 * @param fileName: output file name.
	 */
	static void writeToFile(String firstLine, int[] elementID, int[] predict, String fileName) {
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		    out.write(firstLine);
		    out.newLine();
		    for(int i = 0; i < elementID.length; i++) {
		        out.write(elementID[i] + "," + predict[i]);
		        out.newLine();
		    }
		    out.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}

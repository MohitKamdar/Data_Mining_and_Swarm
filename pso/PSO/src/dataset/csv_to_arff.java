package dataset;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
 
import java.io.File;

/*
 * THIS IS FILE CONVERT ARFF FILE 
 *      ==== CAUSTION ===== 
 * BY DEFAULT
 * CONSIDERING NUMERIC ATTRIBUTE AS EACH ATTRIBUTE 
 * (FOR E.G. NOT CONSIDER NONCOUNTIOUS AS SEX{MALE, FEMALE} OR BINARY {0,1} )
*/
public class csv_to_arff {
	String arff_file_name ; 
	
	public csv_to_arff(String csv_file_name , String arff_file_name)
	{		
		try{
		   // load CSV
	    CSVLoader loader = new CSVLoader();
	    loader.setSource(new File(csv_file_name));
	    Instances data = loader.getDataSet();
	    
	    // save ARFF
	    ArffSaver saver = new ArffSaver();
	    saver.setInstances(data);
	    saver.setFile(new File(arff_file_name));
	    saver.setDestination(new File(arff_file_name));
	    saver.writeBatch();
	    this.arff_file_name = arff_file_name ; 
		}
		catch(Exception e)
		{
			System.out.println("Error : Convert file csv to arff fail");
		}
	}
	
	public void delete_arff_file()
	{
			try{
				 
	    		File file = new File(arff_file_name);
	 
	    		if(!file.delete()){
	    			System.out.println("Error : Delete operation is failed.");
	    		}
	    	}catch(Exception e){
	    		System.out.println("Error : arff file delete operation fail.");;
	    	}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// test 
		// csv_to_arff ca = new csv_to_arff("bk.csv", "bk.arff");
		// ca.delete_arff_file() ; 
	}

}

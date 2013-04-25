import java.io.*;
import java.util.*;

/**
*
* @author Ke Chen
*/

public class Dictionary {
	// FILEDS
	ArrayList<double[]> Dic = null; // Dictionary of atoms trained with natural images
	int dicSize; // number of atoms in the given Dictionary
	int Dim; // length of each atom
	
	// METHODS
	/**
	 * Constructor - read given dictionary data from a file
	 */	
	public void readDic(String fileName) throws Exception{
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		Dic = new ArrayList<double[]>();
		String line = br.readLine();
		
		int count = 0;
		while(line!=null){
			
			String [] tmp = line.split("\\t"); 
			
			double[] dbtmp = new double[tmp.length];
			for(int i = 0; i < tmp.length; i++)
				dbtmp[i] = Double.parseDouble(tmp[i]);
			
			Dic.add(dbtmp);
			line = br.readLine();
			count++;
		}
		
		br.close();
		dicSize = count;
		
		Dim = Dic.get(0).length;
	}
}

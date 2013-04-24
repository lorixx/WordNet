import java.io.*;
import java.util.*;

public class Dictionary {
	final int MAX_SIZE=512;
	ArrayList<double[]> Dic = null;
	int dicSize; // number of atoms in the given Dictionary
	int Dim; // length of each atom

	public void readDic(String fileName) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		Dic = new ArrayList<double[]>();
		String line = br.readLine();
		int count = 0;
		while(line!=null){
			String [] tmp = line.split("\\t"); 
			double[] dbtmp = new double[tmp.length];
			for(int i = 0; i<tmp.length; i++)
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

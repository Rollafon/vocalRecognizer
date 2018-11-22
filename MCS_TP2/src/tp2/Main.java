package tp2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static List<String> getFilepathsFromDir(String dirpath) {
		File dir = new File(dirpath);
		File[] files = dir.listFiles();
		
		List<String> filepaths = new ArrayList<>(files.length);
		for (File f : files) {
			filepaths.add(dir.getPath() + "/" + f.getName());
		}
		return filepaths;
	}
	
	public static void main(String[] args) {
		String dirpath = "./resources/database1";
		List<String> dataBaseFilepaths = getFilepathsFromDir(dirpath);
		IDataBase dataBase = new DataBase(dataBaseFilepaths);
		System.out.println("Database = (" + dataBase.getNbFiles() + "," + dataBase.getMFCCSize() + ")");		
		
		
		//Ikppv kppv = new Kppv(dataBase); // TODO
		
		String testpath = "./resources/tests1";
		List<String> testsFilepaths = getFilepathsFromDir(testpath);
		
	}
}

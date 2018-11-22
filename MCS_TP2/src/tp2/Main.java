package tp2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tp1.myMFCCdistance;

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
		String dirpath = "./resources/refs1";
		List<String> refsFilepaths = getFilepathsFromDir(dirpath);
		IDataBase references = new DataBase(refsFilepaths);	
		
		String testpath = "./resources/tests1";
		List<String> testsFilepaths = getFilepathsFromDir(testpath);
		IDataBase tests = new DataBase(testsFilepaths);
		
		IRecognizer dtwRecognizer = new DTW(references, new myMFCCdistance());
		IConfusionMatrix matrix = new ConfusionMatrix(references, tests, dtwRecognizer);
		System.out.println(matrix);
	}
}

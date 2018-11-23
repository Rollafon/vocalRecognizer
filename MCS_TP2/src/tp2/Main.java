package tp2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tp1.MFCCDistance;
import tp2.recognizers.DTW;
import tp2.recognizers.IRecognizer;
import tp2.recognizers.KPPV;

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

		/*
		String testpath = "./resources/tests1";
		List<String> testsFilepaths = getFilepathsFromDir(testpath);
		IDataBase tests = new DataBase(testsFilepaths);
		
		IRecognizer dtwRecognizer = new DTW(references, new MFCCDistance());
		IConfusionMatrix matrix1 = new ConfusionMatrix(references, tests, dtwRecognizer);
		System.out.println(matrix1);
		
		IRecognizer kppvRecognizer = new KPPV(references, Command.All);
		IConfusionMatrix matrix2 = new ConfusionMatrix(references, tests, kppvRecognizer);
		System.out.println(matrix2);
		//*/
		
		// TMP : TEST KPPV
		IRecognizer recognizer = new KPPV(references);
		IRecord record = new Record("./resources/tests1/M02_arretetoi.csv");
		System.out.println("Commande du nom du fichier = " + record.getCommand());
		ICommand command = recognizer.searchCommand(record, 3);
		System.out.println("Commande reconnue = " + command);
	}
}

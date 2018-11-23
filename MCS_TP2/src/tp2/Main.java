package tp2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tp1.MFCCDistance;
import tp2.acp.ACP;
import tp2.acp.ACPIdentity;
import tp2.recognizers.DTW;
import tp2.recognizers.IRecognizer;
import tp2.recognizers.KPPV;

public class Main {
	public static List<String> getFilepathsFromDir(String dirpath, boolean recOnDir) {
		if (!(new File(dirpath).exists())) {
			throw new IllegalArgumentException("Directory \"" + dirpath + "\" does not exists.");
		}
		File dir = new File(dirpath);
		File[] files = dir.listFiles();
		
		List<String> filepaths = new ArrayList<>(files.length);
		for (File f : files) {
			if (f.isFile()) {
				filepaths.add(dir.getPath() + "/" + f.getName());
			} else if (recOnDir){
				filepaths.addAll(getFilepathsFromDir(dir.getPath() + "/" + f.getName(), true));
			}
		}
		return filepaths;
	}

	public static void main(String[] args) {
		String dirpath = "./resources/refs2_partial";
		List<String> refsFilepaths = getFilepathsFromDir(dirpath, true);
		IDataBase references = new DataBase(refsFilepaths, StorageType.StoreBoth);	

		String testpath = "./resources/tests2";
		List<String> testsFilepaths = getFilepathsFromDir(testpath, true);
		IDataBase tests = new DataBase(testsFilepaths, StorageType.StoreBoth);
		
		System.out.println("Nb fichiers de references = " + references.getNbFiles() + "");
		System.out.println("Nb fichiers de tests = " + tests.getNbFiles() + "\n");
		
		IRecognizer dtwRecognizer = new DTW(references, new MFCCDistance());
		IConfusionMatrix matrix1 = new ConfusionMatrix(references, tests, dtwRecognizer);
		System.out.println(matrix1);
		
		IRecognizer kppvRecognizer = new KPPV(references, new ACPIdentity(references));
		IConfusionMatrix matrix2 = new ConfusionMatrix(references, tests, kppvRecognizer);
		System.out.println(matrix2);
		
		IRecognizer kppvRecognizerACP = new KPPV(references, new ACP(references));
		IConfusionMatrix matrix3 = new ConfusionMatrix(references, tests, kppvRecognizerACP);
		System.out.println(matrix3);
	}
}

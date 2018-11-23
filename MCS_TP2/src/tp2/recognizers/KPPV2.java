package tp2.recognizers;

import java.util.List;
import java.util.PriorityQueue;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import tp1.MFCCDistance;
import tp2.ConfusionMatrix;
import tp2.DataBase;
import tp2.ICommand;
import tp2.IConfusionMatrix;
import tp2.IDataBase;
import tp2.IRecord;
import tp2.Main;
import tp2.StorageType;
import tp2.acp.ACP;
import tp2.acp.ACPIdentity;
import tp2.acp.IACP;

public class KPPV2 implements IRecognizer {
	private IDataBase reference; // Database
	private int k; // Number of neighbors to consider
	private MFCCDistance distCalc = new MFCCDistance();
	private IACP acp;

	private class Pair {
		Pair(double value, int index) {
			this.value = value;
			this.index = index;
		}
		public double value;
		public int index;
	}

	public KPPV2(IDataBase reference, IACP acp) {
		this.reference = reference;
		this.acp = acp;
		
		reference.multiplyData(acp.getNewBase());
	}
	public ICommand searchCommand(IRecord record, int k) {
		this.k = k;
		double[] mfccMean = record.getMfccMean();
		double[][] mfccMean2 = new double[1][mfccMean.length];
		mfccMean2[0] = mfccMean;
		RealMatrix vector = new Array2DRowRealMatrix(mfccMean2);
		vector.multiply(acp.getNewBase());
		
		double[] test = vector.getRow(0);

		PriorityQueue<Pair> queue = new PriorityQueue<>(
			(p1, p2) -> p1.value == p2.value ? 0 : (p1.value < p2.value ? 1 : -1)
		);
		System.out.println("Nb fichs = " + reference.getNbFiles());
		for (int i = 0 ; i < reference.getNbFiles() ; ++i) {
			double[] x = reference.getMfccMean(i);
			
			float dist = distCalc.distance(test, x);
			if (queue.size() <= k || queue.peek().value > dist) {
				queue.poll();
				queue.add(new Pair(dist,i));
			}
		}
		System.out.println(queue);
		
		return null;
	}
	
	public static void main(String[] args) {
		String dirpath = "./resources/refs2_partial";
		List<String> refsFilepaths = Main.getFilepathsFromDir(dirpath, true);
		IDataBase references = new DataBase(refsFilepaths, StorageType.StoreBoth);	

		String testpath = "./resources/tests2";
		List<String> testsFilepaths = Main.getFilepathsFromDir(testpath, true);
		IDataBase tests = new DataBase(testsFilepaths, StorageType.StoreBoth);
		
		System.out.println("Nb fichiers de references = " + references.getNbFiles() + "");
		System.out.println("Nb fichiers de tests = " + tests.getNbFiles() + "\n");
		
		IRecognizer kppvRecognizerACP = new KPPV2(references, new ACP(references));
		IConfusionMatrix matrix3 = new ConfusionMatrix(references, tests, kppvRecognizerACP);
		System.out.println(matrix3);
	}

}

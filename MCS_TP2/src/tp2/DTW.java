package tp2;

import tp1.myMFCCdistance;

public class DTW implements IRecognizer {
	private IDataBase reference;
	private myMFCCdistance mfccDist;
	private float w0;
	private float w1;
	private float w2;
	
	private float min3(float a, float b, float c) {
		return a > b ? (a > c ? a : c) : (b > c ? b : c);
	}
	
	public DTW(IDataBase reference, myMFCCdistance mfccDist) {
		this.reference = reference;
		this.mfccDist = mfccDist;
		this.w0 = 1;
		this.w1 = 2;
		this.w2 = 1;
	}
	
	public ICommand searchCommand(double[] test, int k) {
		
		return null;
	}

}

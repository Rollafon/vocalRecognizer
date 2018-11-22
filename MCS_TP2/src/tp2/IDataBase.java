package tp2;

import org.apache.commons.math3.linear.RealMatrix;

public interface IDataBase {
	public void multiplyData(RealMatrix newBase);
	
	public Command getCommand(int i);
	
	public double getValue(int i, int j);
	public double[] getMfccMean(int i);
	public RealMatrix getData();
	
	public int getNbFiles();
	
	public int getMFCCSize();
}

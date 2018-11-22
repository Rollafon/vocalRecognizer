package tp2;

import org.apache.commons.math3.linear.RealMatrix;

public interface IDataBase {
	public void multiplyBase(RealMatrix newBase);
	
	public Command getCommand(int i);
	
	public RealMatrix getBase();
	
	public int getNbFiles();
	
	public int getMFCCSize();
}

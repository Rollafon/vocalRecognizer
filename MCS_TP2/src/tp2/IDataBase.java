package tp2;

import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;

public interface IDataBase {
	public void multiplyData(RealMatrix newBase);
	
	// GETTERS 
	public ICommand getCommand(int i);
	
	public List<ICommand> getCommands();
	
	public IRecord getRecord(int i);
	
	public double getValue(int i, int j);
	
	public double[] getMfccMean(int i);
	
	public RealMatrix getBase();
	
	public int getNbFiles();
	
	public int getMFCCSize();
}

package tp2;

import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;

public interface IDataBase {
	public void multiplyData(RealMatrix newBase);
	
	// GETTERS	
	public ICommand getCommand(int numFichier);
	
	public List<ICommand> getCommands();
	
	public IRecord getRecord(int numFichier);
	
	public double getValue(int numFichier, int numCoef);
	
	public double[] getMfccMean(int numFichier);
	
	public RealMatrix getBase();
	
	public int getNbFiles();
}

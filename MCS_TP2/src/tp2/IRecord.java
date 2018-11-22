package tp2;

import org.apache.commons.math3.linear.RealMatrix;

public interface IRecord {
	public static final int MFCCLength = 13;
	
	public String getPath();
	public Command getCommand();
	public boolean isLoaded();
	public double[] getMfccMean();
}

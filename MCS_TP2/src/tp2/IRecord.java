package tp2;

public interface IRecord {
	public static final int MFCCLength = 13;
	
	public String getPath();
	public Command getCommand();
	public boolean isLoaded();
	public double[] getMfccMean();
}

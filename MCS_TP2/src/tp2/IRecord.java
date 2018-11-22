package tp2;

public interface IRecord {
	public static final int MFCCLength = 13;
	
	public String getPath();
	public ICommand getCommand();
	public boolean isLoaded();
	public double[] getMfccMean();
}

package tp2;

import fr.enseeiht.danck.voice_analyzer.Field;

public interface IRecord {
	public static final int MFCCLength = 13;
	
	public String getPath();
	public ICommand getCommand();
	public boolean isLoaded();
	public double[] getMfccMean();
	public Field getField();
}

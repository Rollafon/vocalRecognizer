package tp2;

import fr.enseeiht.danck.voice_analyzer.MFCC;

public interface IRecord {
	public static final int MFCCLength = 13;
	
	public String getPath();
	public Command getCommand();
	public boolean isLoaded();
	public MFCC getMfccMean();
}

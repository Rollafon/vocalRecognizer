package tp2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.enseeiht.danck.voice_analyzer.Extractor;
import fr.enseeiht.danck.voice_analyzer.MFCC;
import fr.enseeiht.danck.voice_analyzer.WindowMaker;
import tp1.MultipleFileWindowMaker;

/**
 * Class Record
 * @author PC3
 */
public class Record implements IRecord {	
	private String path;
	private Command command;
	private boolean loaded;
	private MFCC mfccMean;
	
	/**
	 * Constructor
	 * @param path
	 */
	public Record(String path) {
		this.path = path;
		this.mfccMean = null;
		this.loaded = false;
		this.command = pathToCommand(path);		
		loadMFCC();
	}
	
	private Command pathToCommand(String path) {
		String commandName = new File(path).getName();
		int indexOfPt = commandName.lastIndexOf(".");
		if (indexOfPt != -1) {
			commandName = commandName.substring(0, indexOfPt);
		}
		return new Command(commandName);
	}
	
	private void loadMFCC() {
	    List<String> files = new ArrayList<>();
	    files.add(path);
	    WindowMaker windowMaker;
		Extractor extractor = Extractor.getExtractor();
		MFCC[] mfccs = new MFCC[IRecord.MFCCLength];
	    
		try {
			windowMaker = new MultipleFileWindowMaker(files);
		    // Extract MFCC from file
	        for (int i = 0; i < mfccs.length; i++) {
	            mfccs[i] = extractor.nextMFCC(windowMaker);
	        }
			// Calc mean mfcc
			float[] coefs = new float[mfccs[0].getLength()];
			float[] signals = new float[mfccs[0].getSignal().length];
			for (int i = 0 ; i < coefs.length ; ++i) {
				coefs[i] = 0;
				for (int j = 0 ; j < mfccs.length ; ++j) {
					coefs[i] += mfccs[j].getCoef(i);
				}
				coefs[i] /= (double) mfccs.length;
			}
			for (int i = 0 ; i < signals.length ; ++i) {
				signals[i] = 0;
				for (int j = 0 ; j < mfccs.length ; ++j) {
					signals[i] += mfccs[j].getSignal()[j];
				}
				signals[i] /= (double) mfccs.length;
			}
			
			mfccMean = new MFCC(coefs, signals);
			loaded = true;
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// GETTERS
	public String getPath() {
		return path;
	}
	public String getCommandName() {
		return command.toString();
	}
	public boolean isLoaded() {
		return loaded;
	}
	public MFCC getMfccMean() {
		return mfccMean;
	}
}

package tp2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

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
		if (!(new File(path).exists())) {
			throw new IllegalArgumentException("File \"" + path + "\" does not exists.");
		}
		this.mfccMean = null;
		this.loaded = false;
		this.command = pathToCommand(path);		
		loadMFCC();
	}
	
	private Command pathToCommand(String path) {
		String commandName = new File(path).getName();
		int indexOfPt = commandName.lastIndexOf(".") + 1;
		if (indexOfPt != -1) {
			commandName = commandName.substring(0, indexOfPt);
		}
		int indexOfUnd = commandName.lastIndexOf("_") + 1;
		if (indexOfUnd != -1) {
			commandName = commandName.substring(indexOfUnd, commandName.length()-1);
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
	public Command getCommand() {
		return command;
	}
	public boolean isLoaded() {
		return loaded;
	}
	public MFCC getMfccMean() {
		return mfccMean;
	}
	public RealMatrix getMfccMeanAsMatrix() {
		double[] coefs = new double[mfccMean.getLength()];
		for (int i = 0 ; i < coefs.length ; ++i) {
			coefs[i] = mfccMean.getCoef(i);
		}
		return new Array2DRowRealMatrix(coefs);
	}
}

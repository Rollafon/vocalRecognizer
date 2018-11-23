package tp2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import fr.enseeiht.danck.voice_analyzer.Extractor;
import fr.enseeiht.danck.voice_analyzer.Field;
import fr.enseeiht.danck.voice_analyzer.MFCC;
import fr.enseeiht.danck.voice_analyzer.WindowMaker;

/**
 * Class Record
 * @author PC3
 */
public class Record implements IRecord {
	private static final int DEFAULT_NB_MFCC_FOR_FILE = 100;
	private String path;
	private ICommand command;
	private boolean loaded;
	private Field field;
	private MFCC mfccMean;
	private StorageType storageType;

	/**
	 * Code importé de moodle. Calcule la taille d'un field d'un fichier pour savoir combien de MFCC il contiendra.
	 * @param fileName est le chemin du fichier dont on veut le nombre de MFCC.
	 * @return la taille du Field pour ce fichier.
	 */
	@SuppressWarnings("unused")
	private int getFieldLength(String fileName) {
		int result;
		try {
			int counter= 0;
			File file = new File(System.getProperty("user.dir") + fileName);
	        for (String line : Files.readAllLines(file.toPath(), Charset.defaultCharset())) {
	        	counter++;
	        }
	        result = 2*Math.floorDiv(counter, 512);
		} catch (IOException except) {
			result = DEFAULT_NB_MFCC_FOR_FILE;
		}
        return result;
	}
	
	private boolean isStoringField() {
		return storageType == StorageType.StoreBoth || storageType == StorageType.StoreField;
	}
	private boolean isStoringMfccMean() {
		return storageType == StorageType.StoreBoth || storageType == StorageType.StoreMfccMean;
	}
	
	/**
	 * Récupère la commande associée au chemin du fichier qui est de la forme "<chemin>/<prefix>_<commande>.<extension>"
	 * @param path est le chemin du fichier dont on veut récupérer la commande.
	 * @return la commande associée au fichier.
	 */
	private ICommand pathToCommand(String path) {
		String commandName = new File(path).getName();
		// Extract file extension after '.'
		int indexOfPt = commandName.lastIndexOf(".") + 1;
		if (indexOfPt != -1) {
			commandName = commandName.substring(0, indexOfPt);
		}
		// Extract prefix before '_'
		int indexOfUnd = commandName.lastIndexOf("_") + 1;
		if (indexOfUnd != -1) {
			commandName = commandName.substring(indexOfUnd, commandName.length()-1);
		}
		return new Command(commandName);
	}
	
	/**
	 * Lit le fichier path et enregistre le MFCC moyen et/ou le Field associé.
	 */
	private void loadMFCC() {
	    List<String> files = new ArrayList<>();
	    files.add(path);
	    WindowMaker windowMaker;
		Extractor extractor = Extractor.getExtractor();
		MFCC[] mfccs = new MFCC[getFieldLength(path)];
	    
		try {
			windowMaker = new MultipleFileWindowMaker(files);
		    // Extract MFCC from file
	        for (int i = 0 ; i < mfccs.length ; i++) {
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
			
			if (isStoringField()) {
				field = new Field(mfccs);
			} else {
				field = null;
			}
			if (isStoringMfccMean()) {
				mfccMean = new MFCC(coefs, signals);
			} else {
				mfccMean = null;
			}
			
			loaded = true;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Record(String path, StorageType storageType) {
		this.path = path;
		if (!(new File(path).exists())) {
			throw new IllegalArgumentException("File \"" + path + "\" does not exists.");
		}
		this.mfccMean = null;
		this.loaded = false;
		this.command = pathToCommand(path);
		this.storageType = storageType;
		loadMFCC();
	}
	
	// GETTERS
	public String getPath() {
		return path;
	}
	public ICommand getCommand() {
		return command;
	}
	public boolean isLoaded() {
		return loaded;
	}
	public double[] getMfccMean() {
		if (!isStoringMfccMean()) {
			throw new IllegalStateException("Cannot getMfccMean() with this record.");
		}
		double[] coefs = new double[mfccMean.getLength()];
		for (int i = 0 ; i < coefs.length ; ++i) {
			coefs[i] = mfccMean.getCoef(i);
		}
		return coefs;
	}
	public Field getField() {
		if (!isStoringField()) {
			throw new IllegalStateException("Cannot getField() with this record.");
		}
		return field;
	}
}

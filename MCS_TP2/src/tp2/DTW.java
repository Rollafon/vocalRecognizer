package tp2;

import fr.enseeiht.danck.voice_analyzer.DTWHelper;
import fr.enseeiht.danck.voice_analyzer.Field;
import tp1.myMFCCdistance;

public class DTW extends DTWHelper implements IRecognizer {
	private IDataBase reference;
	private myMFCCdistance mfccDist;
	private float w0;
	private float w1;
	private float w2;
	
	private float min3(float a, float b, float c) {
		return a > b ? (a > c ? a : c) : (b > c ? b : c);
	}
	
	public float DTWDistance(Field unknown, Field known) {
		// Methode qui calcule le score de la DTW 
		// entre 2 ensembles de MFCC
		
		float g[][] = new float[unknown.getLength() + 1][known.getLength() + 1];
		float dist = 0;

		g[0][0] = 0;
		for (int j = 1; j < g[0].length; ++j) {
			g[0][j] = Float.POSITIVE_INFINITY;
		}

		for (int i = 1; i < g.length; ++i) {
			g[i][0] = Float.POSITIVE_INFINITY;
			for (int j = 1; j < g[0].length; ++j) {
				dist = mfccDist.distance(unknown.getMFCC(i - 1), known.getMFCC(j - 1));
				g[i][j] = min3(
					g[i - 1][j] + w0 * dist, 
					g[i - 1][j - 1] + w1 * dist,
					g[i][j - 1] + w2 * dist
				);
			}
		}
		return g[unknown.getLength()][known.getLength()] / ((float)(unknown.getLength() + known.getLength()));
	}
		
	public DTW(IDataBase reference, myMFCCdistance mfccDist) {
		this.reference = reference;
		this.mfccDist = mfccDist;
		this.w0 = 1;
		this.w1 = 2;
		this.w2 = 1;
	}

	public ICommand searchCommand(IRecord record, int k) {
		if (reference.getNbFiles() == 0) {
			throw new IllegalStateException("Cannot use reference database without any files.");
		}
		
		float minDist = DTWDistance(record.getField(), reference.getRecord(0).getField());
		int iMin = 0;
		for (int i = 1 ; i < reference.getNbFiles() ; ++i) {
			float dist = DTWDistance(record.getField(), reference.getRecord(i).getField());
			if (dist < minDist) {
				minDist = dist;
				iMin = i;
			}
		}
		return reference.getCommand(iMin);
	}

}
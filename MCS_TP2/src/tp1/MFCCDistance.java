package tp1;

import fr.enseeiht.danck.voice_analyzer.MFCC;
import fr.enseeiht.danck.voice_analyzer.MFCCHelper;

public class MFCCDistance extends MFCCHelper {

	@Override
	public float distance(MFCC mfcc1, MFCC mfcc2) {
		float sqSum = 0;
		for(int i = 0 ; i < mfcc1.getLength() ; ++i) {
			sqSum += (mfcc1.getCoef(i) - mfcc2.getCoef(i)) * (mfcc1.getCoef(i) - mfcc2.getCoef(i));
		}
		return (float) Math.sqrt(sqSum);
	}
	
	public float distance(double[] base, double[] test) {
		float sqSum = 0;
		for(int i = 0 ; i < base.length ; ++i) {
			sqSum += (base[i] - test[i]) * (base[i] - test[i]);
		}
		return (float) Math.sqrt(sqSum);
	}

	@Override
	public float norm(MFCC mfcc) {
		// retourne la valeur de mesure de la MFCC (coef d'indice 0 dans la MFCC) 
		// cette mesure permet de determiner s'il s'agit d'un mot ou d'un silence
		
		return 0;
	}

	@Override
	public MFCC unnoise(MFCC mfcc, MFCC noise) {
		// supprime le bruit de la MFCC passee en parametre
		// soustrait chaque coef du bruit a chaque coef du la MFCC 
		// passee en parametre
		
		return null;
	}

}

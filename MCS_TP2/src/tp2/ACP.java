package tp2;

import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.correlation.Covariance;

import fr.enseeiht.danck.voice_analyzer.MFCC;

/**
 * Class ACP
 * @author PC3
 */
public class ACP implements IACP {
	
	private RealMatrix loadBase(List<String> paths) {
		if (paths.isEmpty()) {
			throw new IllegalArgumentException("Cannot calculate base without files.");
		}
		
		RealMatrix base = new Array2DRowRealMatrix(paths.size(), IRecord.MFCCLength);

		for (int i = 0 ; i < base.getRowDimension() ; ++i) {
			String path = paths.get(i);
			IRecord record = new Record(path);
			MFCC mfccMean = record.getMfccMean();
			
			for (int j = 0 ; j < base.getColumnDimension() ; ++j) {
				double entry = mfccMean.getCoef(j);
				base.setEntry(i, j, entry);
			}
		}
		return base;
	}
	
	private int[] getIndexesOfMaxValues(double[] eigenValues, int k) {
		int a = 0;
		
		return null; // TODO
	}
	
	public RealMatrix calcNewBase(List<String> paths) {
		RealMatrix base = loadBase(paths);
		RealMatrix cov = new Covariance(base).getCovarianceMatrix();
		
		EigenDecomposition eigenDecomposition = new EigenDecomposition(cov);
		RealMatrix matEigenValues = eigenDecomposition.getD();
		RealMatrix matEigenVectors = eigenDecomposition.getV();
		
		double[] eigenValues = new double[cov.getColumnDimension()];
		for (int i = 0 ; i < cov.getColumnDimension() ; ++i) {
			eigenValues[i] = matEigenValues.getEntry(i, i);
		}
		
		int k = 1;
		int[] indexesMaxValues = getIndexesOfMaxValues(eigenValues, k);
		
		RealVector[] eigenVectors = new RealVector[k];
		for (int i = 0 ; i < k ; ++i) {
			int index = indexesMaxValues[i];
			eigenVectors[i] = matEigenVectors.getColumnVector(index);
		}
		
		
		return null; // TODO
	}
}

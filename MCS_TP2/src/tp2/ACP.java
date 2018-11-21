package tp2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import fr.enseeiht.danck.voice_analyzer.MFCC;

/**
 * Class ACP
 * @author PC3
 */
public class ACP implements IACP {
	private class Pair {
		Pair(double value, int index) {
			this.value = value;
			this.index = index;
		}
		public double value;
		public int index;
	}
	private List<Integer> getIndexesOfMaxValues(List<Double> eigenValues, int k) {
		List<Pair> pairs = new ArrayList<>(eigenValues.size());
		for (int i = 0 ; i < eigenValues.size() ; ++i) {
			pairs.add(new Pair(eigenValues.get(i), i));
		}
		Collections.sort(pairs, (p1, p2) -> p1.value == p2.value ? 0 : (p1.value > p2.value ? 1 : -1));
		List<Integer> indexes = new ArrayList<>(k);
		for (int i = 0 ; i < k ; ++i) {
			indexes.add(pairs.get(i).index);
		}
		return indexes;
	}

	public RealMatrix loadBase(List<String> paths) {
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
	
	public RealMatrix calcNewBase(RealMatrix base) {
		RealMatrix cov = new Covariance(base).getCovarianceMatrix();
		
		EigenDecomposition eigenDecomposition = new EigenDecomposition(cov);
		RealMatrix matEigenValues = eigenDecomposition.getD();
		RealMatrix matEigenVectors = eigenDecomposition.getV();

		System.out.println("DIMS = " + cov.getColumnDimension() + " - " + cov.getRowDimension());
		System.out.println("DIMS = " + matEigenValues.getColumnDimension() + " - " + matEigenValues.getRowDimension());
		System.out.println("DIMS = " + matEigenVectors.getColumnDimension() + " - " + matEigenVectors.getRowDimension());
		//System.out.println(matEigenValues);
		//System.out.println(matEigenVectors);
		
		List<Double> eigenValues = new ArrayList<>(cov.getColumnDimension());
		for (int i = 0 ; i < cov.getColumnDimension() ; ++i) {
			eigenValues.add(matEigenValues.getEntry(i, i));
		}
		
		final int k = 3;
		List<Integer> indexesMaxValues = getIndexesOfMaxValues(eigenValues, k);
		
		double[][] eigenVectors = new double[cov.getColumnDimension()][k];
		for (int i = 0 ; i < k ; ++i) {
			int index = indexesMaxValues.get(i);
			double[] eigenVector = matEigenVectors.getColumnVector(index).toArray();
			for (int j = 0 ; j < cov.getColumnDimension() ; ++j) {
				eigenVectors[j][i] = eigenVector[j];
			}
		}
		
		RealMatrix vects = new Array2DRowRealMatrix(eigenVectors);
		System.out.println(base);
		System.out.println(vects);
		RealMatrix newBase = base.multiply(vects);
		return newBase;
	}
	
	public static void main(String args[]) {
		IACP acp = new ACP();
		RealMatrix base = new Array2DRowRealMatrix(2, 13);
		for (int i = 0 ; i < base.getRowDimension() ; ++i) {
			for (int j = 0 ; j < base.getColumnDimension() ; ++j) {
				base.setEntry(i, j, 0);
			}
		}
		
		RealMatrix newBase = acp.calcNewBase(base);
		System.out.println(base);
		System.out.println(newBase);
	}
}

package tp2.acp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import tp2.IDataBase;

/**
 * Class ACP
 * @author PC3
 */
public class ACP implements IACP {
	// ATTRIBUTES
	private int k;
	private RealMatrix newBase;
	
	// PRIVATE METHODS
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
	
	/**
	 * Calcule la nouvelle base associ� � la matrice data.
	 * @param data est la matrice dont on veut la nouvelle base.
	 * @return la nouvelle base pour compresser la matrice data.
	 */
	private RealMatrix calcNewBase(RealMatrix data) {
		RealMatrix cov = new Covariance(data).getCovarianceMatrix();
		
		EigenDecomposition eigenDecomposition = new EigenDecomposition(cov);
		RealMatrix matEigenValues = eigenDecomposition.getD();
		RealMatrix matEigenVectors = eigenDecomposition.getV();
		
		List<Double> eigenValues = new ArrayList<>(cov.getColumnDimension());
		for (int i = 0 ; i < cov.getColumnDimension() ; ++i) {
			eigenValues.add(matEigenValues.getEntry(i, i));
		}
		
		List<Integer> indexesMaxValues = getIndexesOfMaxValues(eigenValues, k);
		
		double[][] eigenVectors = new double[cov.getColumnDimension()][k];
		for (int i = 0 ; i < k ; ++i) {
			int index = indexesMaxValues.get(i);
			double[] eigenVector = matEigenVectors.getColumnVector(index).toArray();
			for (int j = 0 ; j < cov.getColumnDimension() ; ++j) {
				eigenVectors[j][i] = eigenVector[j];
			}
		}
		
		return new Array2DRowRealMatrix(eigenVectors);
	}
	
	private class Pair {
		Pair(double value, int index) {
			this.value = value;
			this.index = index;
		}
		public double value;
		public int index;
	}

	// METHODS
	/**
	 * Constructor
	 * @param dataBase
	 */
	public ACP(IDataBase dataBase) {
		this.k = 3;
		this.newBase = calcNewBase(dataBase.getData());
	}
	
	public RealMatrix getNewBase() {
		return newBase;
	}
}

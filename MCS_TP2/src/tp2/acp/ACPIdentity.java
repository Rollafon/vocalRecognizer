package tp2.acp;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import tp2.IDataBase;

public class ACPIdentity implements IACP {
	private RealMatrix newBase;
	
	/**
	 * Crée une matrice identité pour simplifier le test de KPPV.
	 * @param dataBase est la base dont on veut la matrice identité.
	 */
	public ACPIdentity(IDataBase dataBase) {
		RealMatrix base = dataBase.getData();
		this.newBase = new Array2DRowRealMatrix(base.getColumnDimension(), base.getColumnDimension());
		
		for (int i = 0 ; i < base.getColumnDimension() ; ++i) {
			for (int j = 0 ; j < base.getColumnDimension() ; ++j) {
				if (i == j) {
					newBase.setEntry(i, j, 1);
				} else {
					newBase.setEntry(i, j, 0);
				}
			}
		}
	}

	public RealMatrix getNewBase() {
		return newBase;
	}
}

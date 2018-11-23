package tp2.acp;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import tp2.IDataBase;

public class ACPIdentity implements IACP {
	private RealMatrix newBase;
	
	public ACPIdentity(IDataBase dataBase) {
		RealMatrix base = dataBase.getBase();
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

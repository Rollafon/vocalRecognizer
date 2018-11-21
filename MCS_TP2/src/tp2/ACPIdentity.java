package tp2;

import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class ACPIdentity implements IACP {
	private RealMatrix newBase;
	
	public ACPIdentity(IDataBase dataBase) {
		RealMatrix base = dataBase.getBase();
		this.newBase = new Array2DRowRealMatrix(base.getRowDimension(), base.getRowDimension());
		
		for (int i = 0 ; i < base.getRowDimension() ; ++i) {
			for (int j = 0 ; j < base.getRowDimension() ; ++j) {
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

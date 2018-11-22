package tp2;

import org.apache.commons.math3.linear.RealMatrix;

import tp1.myMFCCdistance;

public class Kppv implements Ikppv {
	private IDataBase datas; // Database
	private String[] label;	// Labels of each record of the database (same sequence)
	
	private IDataBase tests; // TODO : type of expressions
	private int k;
	
	myMFCCdistance distCalc = new myMFCCdistance();

	public Kppv(IDataBase datas, String[] label) {
		this.datas = datas;
		this.label = label;
	}
	
	/**
	 * Evaluate the distances between each point of the data base and the given test
	 * @return the array of distances
	 */
	private RealMatrix evalDistances() {
		// TODO
		double[][] res = new double[datas.getBase().getRowDimension()][tests.getBase().getRowDimension()];
		for (int j = 0; j < res.length; ++j) {
			for (int i = 0; i < res.length; ++i) {
				res[i][j] = distCalc.distance(datas.getBase().getRow(i), datas.getBase().getRow(j));
			}
		}
		
		return res;
	}
	
	public String searchLabel(IDataBase tests, int k) {
		
	}
}

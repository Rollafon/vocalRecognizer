package tp2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import tp1.myMFCCdistance;

public class Kppv implements Ikppv {
	private IDataBase datas; // Database
	private String[] label;	// Labels of each record of the database (same sequence)
	private int k;	// Number of neighbors to consider
	
	myMFCCdistance distCalc = new myMFCCdistance();

	/**
	 * Constructor
	 * @param datas: the database to consider to recognize commands
	 * @param label: the array of labels corresponding to the values of database
	 */
	public Kppv(IDataBase datas, String[] label) {
		this.datas = datas;
		this.label = label;
	}
	
	/**
	 * Evaluate the distances between each point of the data base and the given test
	 * @param test: the data test to be compare
	 * @return the array of distances (lines represent the tests datas, and columns the database)
	 */
	private double[] evalDistances(double[] test) {
		double[] res = new double[datas.getBase().getRowDimension()];
		
		for (int i = 0; i < datas.getBase().getRowDimension(); ++i)
			res[i] = distCalc.distance(datas.getBase().getRow(i), test);
		
		return res;
	}

	@Override
	public String searchLabel(double[] test, int k) {
		double[] distances = evalDistances(test);
		
		return null;
	}
}

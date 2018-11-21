package tp2;

public class Kppv implements Ikppv {
	private IRecord[] data; // Database
	private String[] label;	// Labels of each record of the database (same sequence)
	
	private int[] test; // TODO : type of expressions
	private int k;

	public Kppv(IRecord[] data, String[] label) {
		this.data = data;
		this.label = label;
	}
	
	/**
	 * Evaluate the distances between each point of the data base and the given test
	 * @return
	 */
	private double[] evalDistances() {
		double[] res = new double[data.length];
		
		for (int i = 0; i < data.length; ++i) {
			
		}
		
		return res;
	}
	
	public String searchLabel(int k) {
		
	}
}

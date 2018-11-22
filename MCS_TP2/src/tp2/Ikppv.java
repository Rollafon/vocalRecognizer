package tp2;

public interface Ikppv {
	
	/**
	 * Search the nearest label
	 * @param IRecord: the record to be tested
	 * @param k: the number of neighbors to compare the test
	 * @return the corresponding label
	 */
	public String searchLabel(IRecord test, int k);
	
}

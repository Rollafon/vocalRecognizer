import org.apache.commons.math3.linear.RealMatrix;

import tp2.Ikppv;

public class Kppv implements Ikppv {
	private RealMatrix data;
	private RealMatrix label;
	
	public Kppv(RealMatrix data, RealMatrix label) {
		this.data = data;
		this.label = label;
	}
}

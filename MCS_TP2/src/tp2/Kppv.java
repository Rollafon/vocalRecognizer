package tp2;
import org.apache.commons.math3.linear.RealMatrix;

public class Kppv implements Ikppv {
	private RealMatrix data;
	private RealMatrix label;
	int i;
	public Kppv(RealMatrix data, RealMatrix label) {
		this.data = data;
		this.label = label;
	}
}

package tp2;

import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;

public interface IACP {
	public RealMatrix loadBase(List<String> paths);
	public RealMatrix calcNewBase(RealMatrix base);
}

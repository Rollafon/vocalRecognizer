package tp2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import fr.enseeiht.danck.voice_analyzer.MFCC;

public class DataBase implements IDataBase {
	private List<Command> commands;
	private RealMatrix base;
	
	public DataBase(List<String> paths) {
		if (paths.isEmpty()) {
			throw new IllegalArgumentException("Cannot calculate base without files.");
		}
		
		this.base = new Array2DRowRealMatrix(paths.size(), IRecord.MFCCLength);
		this.commands = new ArrayList<>(paths.size());
		
		for (int i = 0 ; i < base.getRowDimension() ; ++i) {
			String path = paths.get(i);
			IRecord record = new Record(path);
			commands.add(record.getCommand());
			MFCC mfccMean = record.getMfccMean();
			
			for (int j = 0 ; j < base.getColumnDimension() ; ++j) {
				double entry = mfccMean.getCoef(j);
				base.setEntry(i, j, entry);
			}
		}
	}
	
	public void multiplyBase(RealMatrix newBase) {
		base = base.multiply(newBase);
	}
	
	public Command getCommand(int i) {
		return commands.get(i);
	}
	
	public RealMatrix getBase() {
		return base;
	}
}

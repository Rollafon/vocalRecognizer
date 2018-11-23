package tp2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class DataBase implements IDataBase {
	private StorageType storageType;
	private RealMatrix data;
	private List<IRecord> records;
	private List<ICommand> commands;
	
	private void loadData(List<String> paths) {
		this.data = new Array2DRowRealMatrix(paths.size(), IRecord.MFCCLength);
		this.records = new ArrayList<>(paths.size());
		this.commands = new ArrayList<>(paths.size());
		
		// Note: getRowDimension() == paths.size()
		for (int i = 0 ; i < data.getRowDimension() ; ++i) {
			String path = paths.get(i);
			IRecord record = new Record(path, storageType);
			records.add(record);
			commands.add(record.getCommand());
			double[] mfccMean = record.getMfccMean();
			
			for (int j = 0 ; j < data.getColumnDimension() ; ++j) {
				double entry = mfccMean[j];
				data.setEntry(i, j, entry);
			}
		}	
	}
	
	public DataBase(List<String> paths, StorageType storageType) {
		if (paths.isEmpty()) {
			throw new IllegalArgumentException("Cannot create base without files.");
		}
		this.storageType = storageType;
		loadData(paths);
	}
	
	public void multiplyData(RealMatrix newBase) {
		data = data.multiply(newBase);
	}
	
	public ICommand getCommand(int i) {
		return commands.get(i);
	}
	public List<ICommand> getCommands() {
		return commands;
	}
	public IRecord getRecord(int i) {
		return records.get(i);
	}
	
	public double getValue(int i, int j) {
		return data.getEntry(i,j);
	}
	
	public double[] getMfccMean(int i) {
		return data.getRow(i);
	}
	
	public RealMatrix getBase() {
		return data;
	}
	
	public int getNbFiles() {
		return data.getRowDimension();
	}
	
	public int getMFCCSize() {
		return data.getColumnDimension();
	}
}

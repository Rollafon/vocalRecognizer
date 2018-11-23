package tp2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class DataBase implements IDataBase {
	private StorageType storageType;
	private List<String> paths;
	private RealMatrix data;
	private List<IRecord> records;
	private List<ICommand> commands;
	private boolean hasChanged;
	
	private boolean isStoringMatrix() {
		return storageType == StorageType.StoreBoth || storageType == StorageType.StoreMfccMean;
	}
	
	private void load() {
		if (isStoringMatrix()) {
			this.data = new Array2DRowRealMatrix(paths.size(), IRecord.MFCCLength);
		} else {
			this.data = null;
		}
		this.records = new ArrayList<>(paths.size());
		this.commands = new ArrayList<>(paths.size());
		this.hasChanged = false;
		
		for (int i = 0 ; i < paths.size() ; ++i) {
			String path = paths.get(i);
			IRecord record = new Record(path, storageType);
			records.add(record);
			commands.add(record.getCommand());
			
			if (isStoringMatrix()) {
				double[] mfccMean = record.getMfccMean();
				
				for (int j = 0 ; j < data.getColumnDimension() ; ++j) {
					double entry = mfccMean[j];
					data.setEntry(i, j, entry);
				}
			}
		}
	}
	
	public DataBase(List<String> paths, StorageType storageType) {
		if (paths.isEmpty()) {
			throw new IllegalArgumentException("Cannot create base without files.");
		}
		this.storageType = storageType;
		this.paths = paths;
		load();
	}
	
	/**
	 * Recharge la base de données selon storageType et paths.
	 */
	public void reload() {
		if (hasChanged) {
			load();
		}
	}
	
	/**
	 * Multiplie la matrice de la base de données par une nouvelle base pour l'ACP.
	 */
	public void multiplyData(RealMatrix newBase) {
		if (!isStoringMatrix()) {
			throw new IllegalStateException("Cannot multiply base without store Mfcc means.");
		}
		RealMatrix tmp = data.multiply(newBase);
		if (!data.equals(tmp)) {
			data = tmp;
			hasChanged = true;
		}
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
		return getData().getEntry(i,j);
	}
	
	public double[] getMfccMean(int i) {
		return getData().getRow(i);
	}
	
	public RealMatrix getData() {
		if (!isStoringMatrix()) {
			throw new IllegalStateException("Cannot multiply base without store Mfcc means.");
		}
		return data;
	}
	
	public int getNbFiles() {
		return commands.size();
	}
}

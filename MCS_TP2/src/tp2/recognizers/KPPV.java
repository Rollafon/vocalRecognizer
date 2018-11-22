package tp2.recognizers;

import java.util.HashMap;
import java.util.Map;

import tp1.MFCCDistance;
import tp2.Command;
import tp2.ICommand;
import tp2.IDataBase;
import tp2.IRecord;

public class KPPV implements IRecognizer {
	private IDataBase datas; // Database
	private String[] label; // Labels of each record of the database (same sequence)
	private int k; // Number of neighbors to consider
	private MFCCDistance distCalc = new MFCCDistance();

	/* Represent a number and a string, used as a distance and a label */
	private class DistToLab {
		private double dist;
		private String label;

		public DistToLab(double dist, String label) {
			super();
			this.dist = dist;
			this.label = label;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param datas:
	 *            the database to consider to recognize commands
	 * @param label:
	 *            the array of labels corresponding to the values of database
	 */
	public KPPV(IDataBase datas, String[] label) {
		this.datas = datas;
		this.label = label;
	}

	/**
	 * Evaluate the distances between each point of the data base and the given test
	 * 
	 * @param test:
	 *            the data test to be compare
	 * @return the array of distances related to their corresponding label
	 */
	private DistToLab[] evalDistances(double[] test) {
		DistToLab[] res = new DistToLab[datas.getBase().getRowDimension()];

		for (int i = 0; i < datas.getBase().getRowDimension(); ++i)
			res[i] = new DistToLab(distCalc.distance(datas.getBase().getRow(i), test), label[i]);

		return res;
	}

	/**
	 * Sort the array and returns only the k nearest values (according to given
	 * distances) Use an incomplete bubble sort
	 * 
	 * @param test:
	 *            the data to be sorted
	 * @return a k size array of DistToLab sorted (lower distance first)
	 */
	private DistToLab[] kNearest(DistToLab[] test) {
		DistToLab[] nearest = new DistToLab[k];

		for (int i = 0; i < k; ++i) {
			for (int j = test.length - 1; j < 0; --j) {
				if (test[j].dist < test[j - 1].dist) {
					DistToLab tmp = test[j - 1];
					test[j - 1] = test[j];
					test[j] = tmp;
				}
			}
		}
		for (int i = 0; i < k; ++i)
			nearest[i] = test[i];
		return nearest;
	}

	@Override
	public ICommand searchCommand(IRecord record, int k) {
		double[] test = record.getMfccMean();
		DistToLab[] distances = evalDistances(test);
		DistToLab[] kNearest = kNearest(distances);
		
		Map<String, Integer> classCounter = new HashMap<>();
		Integer max = new Integer(0);
		String recognizedLabel = "";
		for (DistToLab d: kNearest) {
			if (classCounter.containsKey(d.label)) {
				Integer i = classCounter.get(d.label) + 1;
				classCounter.put(d.label, i);
				if (max < i) {
					max = i;
					recognizedLabel = d.label;
				}
			} else {
				classCounter.put(d.label, new Integer(1));
				if (max < 1) {
					max = new Integer(1);
					recognizedLabel = d.label;
				}
			}
		}
		
		return new Command(recognizedLabel);
	}

}
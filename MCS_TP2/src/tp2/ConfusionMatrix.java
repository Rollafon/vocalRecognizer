package tp2;

import tp2.recognizers.IRecognizer;

public class ConfusionMatrix implements IConfusionMatrix {
	private int[][] matrix;
	private float errorRate;
	
	/**
	 * Constructor of ConfusionMatrix
	 * @param reference
	 * @param tests
	 * @param recognizer
	 */
	public ConfusionMatrix(IDataBase reference, IDataBase tests, IRecognizer recognizer) {
		this.matrix = new int[Command.All.length][Command.All.length];
		for (int i = 0 ; i < matrix.length ; ++i) {
			for (int j = 0 ; j < matrix[0].length ; ++j) {
				matrix[i][j] = 0;
			}
		}
		
		this.errorRate = 0.f;
		for (int i = 0 ; i < tests.getNbFiles() ; ++i) {
			ICommand commandFound = recognizer.searchCommand(tests.getRecord(i));
			ICommand commandFile = tests.getCommand(i);
			matrix[commandFound.getIndex()][commandFile.getIndex()]++;
			
			if (commandFound.getIndex() != commandFile.getIndex()) {
				errorRate += 1.f;
			}
		}
		errorRate /= (float)(tests.getNbFiles());
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		for (int i = 0 ; i < matrix.length ; ++i) {
			for (int j = 0 ; j < matrix[i].length ; ++j) {
				build.append(matrix[i][j] + " ");
			}
			build.append("\n");
		}
		build.append("Error rate = " + (errorRate*100) + " % \n");
		return build.toString();
	}
}

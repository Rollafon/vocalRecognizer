package tp2;

public class ConfusionMatrix {
	private int[][] matrix;
	private float errorRate;
	
	public ConfusionMatrix(IDataBase reference, IDataBase tests, IRecognizer recognizer) {
		this.matrix = new int[Command.All.length][Command.All.length];
		for (int i = 0 ; i < matrix.length ; ++i) {
			for (int j = 0 ; j < matrix[0].length ; ++j) {
				matrix[i][j] = 0;
			}
		}
		
		this.errorRate = 0.f;
		final int k = 1;
		for (int i = 0 ; i < tests.getNbFiles() ; ++i) {
			ICommand commandFound = recognizer.searchCommand(tests.getRecord(i), k);
			ICommand commandFile = tests.getCommand(i);			
			matrix[commandFound.getIndex()][commandFile.getIndex()]++;
			
			if (commandFound.getIndex() != commandFile.getIndex()) {
				errorRate += 1.f;
			}
		}
		errorRate /= (float)(tests.getNbFiles());
	}
	
	public float getErrorRate() {
		return errorRate;
	}
}

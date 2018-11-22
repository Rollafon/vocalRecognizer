package tp2;

public class ConfusionMatrix {
	private int[][] matrix;
	private IDataBase reference;
	
	public ConfusionMatrix(IDataBase reference, IDataBase tests, IRecognizer recognizer) {
		this.matrix = new int[Command.All.length][Command.All.length];
		for (int i = 0 ; i < matrix.length ; ++i) {
			for (int j = 0 ; j < matrix[0].length ; ++j) {
				matrix[i][j] = 0;
			}
		}
		this.reference = reference;
		// TODO : implem une interface qui réunit DTW et IKPPV
		
	}
	
}

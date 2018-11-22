package tp2;

public class Command implements ICommand {
	public static final String[] All = {
		"arretetoi", 
		"atterrissage",
		"avance", 
		"decollage", 
		"droite", 
		"etatdurgence", 
		"faisunflip", 
		"gauche", 
		"plusbas", 
		"plushaut", 
		"recule",
		"tournedroite",
		"tournegauche"
	};
	private int indCommand;
	
	public Command(String command) {
		boolean found = false;
		int i = 0;
		for ( ; i < All.length && !found ; ++i) {
			found = (All[i].equals(command));
		}
		if (!found) {
			throw new IllegalArgumentException("Unknown command \"" + command + "\"");
		}
		this.indCommand = i - 1;
	}
	
	public Command(int indCommand) {
		if (indCommand < 0 || indCommand >= All.length) {
			throw new IllegalArgumentException("Command index not in range [0," + All.length + "[");
		}
		this.indCommand = indCommand;
	}
	
	public String toString() {
		return All[indCommand];
	}
	public int getIndex() {
		return indCommand;
	}
}

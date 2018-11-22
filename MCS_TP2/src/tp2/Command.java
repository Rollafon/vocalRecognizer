package tp2;

public class Command {
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
		this.indCommand = i;
	}
	
	public String toString() {
		return All[indCommand];
	}
	public int getIndex() {
		return indCommand;
	}
}

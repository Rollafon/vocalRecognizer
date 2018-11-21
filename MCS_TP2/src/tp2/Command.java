package tp2;

public class Command {
	private static final String commands[] = {
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
		for ( ; i < commands.length && !found ; ++i) {
			found = (commands[i].equals(command));
		}
		if (!found) {
			throw new IllegalArgumentException("Unknown command \"" + command + "\"");
		}
		this.indCommand = i;
	}
	
	public String toString() {
		return commands[indCommand];
	}
}

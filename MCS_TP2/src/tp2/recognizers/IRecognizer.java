package tp2.recognizers;

import tp2.ICommand;
import tp2.IRecord;

public interface IRecognizer {
	public ICommand searchCommand(IRecord record);
}

package dd.Exceptions;

public class InvalidMoviment extends Error{

	private static final long serialVersionUID = 1396789178300143120L;

	public InvalidMoviment() {
		super();
	}
	
	public InvalidMoviment(String msg) {
		super(msg);
	}
	
}

package edu.umich.soar.modules;

public class ModuleException extends RuntimeException {

	private static final long serialVersionUID = -8473381301736835438L;

	public ModuleException() {
		super();
	}

	public ModuleException(String message) {
		super(message);
	}

	public ModuleException(Throwable cause) {
		super(cause);
	}

	public ModuleException(String message, Throwable cause) {
		super(message, cause);
	}

}

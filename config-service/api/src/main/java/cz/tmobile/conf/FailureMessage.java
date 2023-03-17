package cz.tmobile.conf;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FailureMessage {

	private final String messageCode;
	private final List<Object> args;

	public FailureMessage(String messageCode, List<Object> args) {
		super();
		this.messageCode = messageCode;
		this.args = args;
	}

	public FailureMessage(String messageCode, Object... args) {
		super();
		this.messageCode = messageCode;
		if (args != null) {
			this.args = Arrays.asList(args);
		} else {
			this.args = Collections.emptyList();
		}
	}

	public String getMessageCode() {
		return messageCode;
	}

	public List<Object> getArgs() {
		return args;
	}

	@Override
	public String toString() {
		return "FailureMessage [messageCode=" + messageCode + ", args=" + args + "]";
	}

}

package cz.tmobile.conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FailureResponse {

	private final List<FailureMessage> messages = new ArrayList<>();

	public FailureResponse() {
		super();
	}
	
	public FailureResponse(String messageCode, Object... args) {
		this();
		this.messages.add(new FailureMessage(messageCode, Arrays.asList(args)));
	}

	public List<FailureMessage> getMessages() {
		return messages;
	}

	@Override
	public String toString() {
		return "FailureResponse [messages=" + messages + "]";
	}

}

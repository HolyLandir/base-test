package cz.tmobile.conf.json;

import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
@Component
public class JacksonConfig implements ContextResolver<ObjectMapper> {

	public static final String TIME_FORMAT = "dd.MM.yyyy_HH:mm:ss";
	private final ObjectMapper objectMapper;

	public JacksonConfig() {
		this.objectMapper = new ObjectMapper();
		SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
		dateFormat.setLenient(false);
		this.objectMapper.setDateFormat(dateFormat);
		this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		this.objectMapper.registerModule(new JavaTimeModule());
	}

	public ObjectMapper getContext(Class<?> objectType) {
		return objectMapper;
	}
}

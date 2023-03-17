package cz.tmobile.conf.openapi;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import cz.tmobile.conf.FailureResponse;

@Provider
@Component
public class UncaughtException extends Throwable implements ExceptionMapper<Throwable> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(UncaughtException.class);

	@Override
	public Response toResponse(Throwable exception) {
		LOG.error("Executing rest service failed with {} - {}", exception.getClass(), exception.getMessage());
		LOG.debug("Exception", exception);
		if (exception instanceof WebApplicationException) {
			WebApplicationException appException = (WebApplicationException) exception;
			return appException.getResponse();
		}
//		if (exception instanceof AccessDeniedException) {
//			return Response.status(Response.Status.UNAUTHORIZED).entity(new FailureResponse("insufficient.rights")).build();
//		}
		if (exception instanceof MyBatisSystemException) {
			for (Throwable causedException = exception.getCause(); causedException != null; causedException = causedException.getCause()) {
				if (causedException instanceof CannotGetJdbcConnectionException) {
					return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new FailureResponse("unavailable.database", causedException.getMessage()))
							.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();
				}
			}
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new FailureResponse("internal.server.error"))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();
	}
}
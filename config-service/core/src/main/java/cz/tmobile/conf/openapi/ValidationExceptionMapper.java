package cz.tmobile.conf.openapi;

import java.util.List;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintDefinitionException;
import javax.validation.GroupDefinitionException;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.collections4.CollectionUtils;
import org.jboss.resteasy.api.validation.ResteasyViolationException;
import org.jboss.resteasy.api.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cz.tmobile.conf.FailureMessage;
import cz.tmobile.conf.FailureResponse;

@Provider
@Component
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
	private static final Logger LOG = LoggerFactory.getLogger(ValidationExceptionMapper.class);

	@Override
	public Response toResponse(ValidationException exception) {
		if (exception instanceof ConstraintDefinitionException) {
			return buildResponse(unwrapException(exception), MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
		}
		if (exception instanceof ConstraintDeclarationException) {
			return buildResponse(unwrapException(exception), MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
		}
		if (exception instanceof GroupDefinitionException) {
			return buildResponse(unwrapException(exception), MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
		}
		if (exception instanceof ResteasyViolationException) {
			var resteasyViolationException = (ResteasyViolationException) exception;
			Exception e = resteasyViolationException.getException();
			if (e != null) {
				return buildResponse(unwrapException(e), MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
			}
			if (resteasyViolationException.getReturnValueViolations().isEmpty()) {
				return buildViolationReportResponse(resteasyViolationException, Status.BAD_REQUEST);
			}
			return buildViolationReportResponse(resteasyViolationException, Status.INTERNAL_SERVER_ERROR);
		}
		return buildResponse(unwrapException(exception), MediaType.TEXT_PLAIN, Status.INTERNAL_SERVER_ERROR);
	}

	protected Response buildResponse(Object entity, String mediaType, Status status) {
		ResponseBuilder builder = Response.status(status).entity(entity);
		builder.type(MediaType.TEXT_PLAIN);
		builder.header(Validation.VALIDATION_HEADER, "true");
		return builder.build();
	}

	protected Response buildViolationReportResponse(ResteasyViolationException exception, Status status) {
		ResponseBuilder builder = Response.status(status);
		builder.header(Validation.VALIDATION_HEADER, "true");

		// Check standard media types.
		MediaType mediaType = getAcceptMediaType(exception.getAccept());
		if (mediaType != null) {
			builder.type(mediaType);
			final FailureResponse result = new FailureResponse();
			if (CollectionUtils.isNotEmpty(exception.getViolations())) {
				exception.getViolations().parallelStream().map(v -> new FailureMessage(v.getMessage(), v.getValue())).forEach(m -> result.getMessages().add(m));
			}
			builder.entity(result);
			LOG.trace("Execution failed with {} errors", result.getMessages().size());
			return builder.build();
		}

		LOG.warn("No media type for validation response");
		// Default media type.
		builder.type(MediaType.TEXT_PLAIN);
		builder.entity(exception.toString());
		return builder.build();
	}

	protected String unwrapException(Throwable t) {
		StringBuffer sb = new StringBuffer();
		doUnwrapException(sb, t);
		return sb.toString();
	}

	private void doUnwrapException(StringBuffer sb, Throwable t) {
		if (t == null) {
			return;
		}
		sb.append(t.toString());
		if (t.getCause() != null && t != t.getCause()) {
			sb.append('[');
			doUnwrapException(sb, t.getCause());
			sb.append(']');
		}
	}

	private MediaType getAcceptMediaType(List<MediaType> accept) {
		for (MediaType mt : accept) {
			if (MediaType.APPLICATION_XML_TYPE.getType().equals(mt.getType()) && MediaType.APPLICATION_XML_TYPE.getSubtype().equals(mt.getSubtype())) {
				return MediaType.APPLICATION_XML_TYPE;
			}
			if (MediaType.APPLICATION_JSON_TYPE.getType().equals(mt.getType()) && MediaType.APPLICATION_JSON_TYPE.getSubtype().equals(mt.getSubtype())) {
				return MediaType.APPLICATION_JSON_TYPE;
			}
		}
		return null;
	}

}
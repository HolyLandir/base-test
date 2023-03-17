package cz.tmobile.conf.api;

import java.net.HttpURLConnection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cz.tmobile.conf.FailureResponse;
import cz.tmobile.conf.api.data.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Path("/user")
public interface IUserService {

	// @formatter:off
    @GET
	@Path("/")
    @Produces({MediaType.APPLICATION_JSON})
	@Operation(
		description = "List users"
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "" + HttpURLConnection.HTTP_OK,
			description = "successful",
			content = @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = User.class)))),
		@ApiResponse(
			responseCode = "" + HttpURLConnection.HTTP_INTERNAL_ERROR,
			description = "Server failed",
			content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FailureResponse.class)))
	})
	// @formatter:on
	public Response listValues();


    //TODO implement full crud with validation
}

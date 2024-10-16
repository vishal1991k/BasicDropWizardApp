package com.basic.resource;

import com.basic.model.ApiResponse;
import com.basic.model.StoredDemoRow;
import com.basic.service.DemoService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/demo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Demo Api", description = "Demo Api")
public class DemoResource {
    private final DemoService demoService;

    @Inject
    private DemoResource(final DemoService demoService) {
        this.demoService = demoService;
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Returns a row from DB")
    public Response get(
            @ApiParam(value = "ID to fetch record", required = true) @PathParam("id") String id) {
        StoredDemoRow row =  demoService.getRow(id);
        if (row != null) {
            return Response.ok(new ApiResponse<>(row)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiResponse<>("User not found."))
                    .build();
        }
    }

    @POST
    @Path("/{id}")
    @Operation(summary = "Save a row")
    public Response save(
            @ApiParam(value = "ID", required = true) @PathParam("id") String id,
            @ApiParam(value = "Row to store ", required = true) StoredDemoRow row) {
        boolean sucess = demoService.saveRow(row);
        if(sucess){
            return Response.ok(new ApiResponse<>(sucess)).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>("Couldn't save Row " + row.toString()))
                    .build();
        }
    }
}

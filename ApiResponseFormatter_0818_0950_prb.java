// 代码生成时间: 2025-08-18 09:50:26
package com.example.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;

// ApiResponseFormatter class provides a utility to format API responses
public class ApiResponseFormatter {

    // Private constructor to prevent instantiation
    private ApiResponseFormatter() {
    }

    // Method to create a successful API response
    public static Response createSuccessResponse(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("status", "success");

        return Response.status(Status.OK).entity(response).type(MediaType.APPLICATION_JSON).build();
    }

    // Method to create an error API response
    public static Response createErrorResponse(String errorMessage) {
        Map<String, String> response = new HashMap<>();
        response.put("error", errorMessage);
        response.put("status", "error");

        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).type(MediaType.APPLICATION_JSON).build();
    }

    // Method to create a bad request error API response
    public static Response createBadRequestResponse(String field, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Bad Request");
        response.put("status", "error");
        Map<String, String> details = new HashMap<>();
        details.put(field, message);
        response.put("details", details);

        return Response.status(Status.BAD_REQUEST).entity(response).type(MediaType.APPLICATION_JSON).build();
    }
}
// 代码生成时间: 2025-08-06 09:06:46
package com.example.quarkusdemo;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.Map;

public class ApiResponseFormatter {

    /*
     * Formats the given data into a JSON response.
# 扩展功能模块
     *
     * @param data Data to be formatted into a response.
     * @param status HTTP status code for the response.
# 改进用户体验
     * @return A Response object with the formatted JSON data.
     */
    public static Response formatResponse(Map<String, Object> data, int status) {
        // Create a JSON object from the data map
        JsonObject jsonData = Json.createObjectBuilder()
                .add("data", data)
                .build();

        // Create a Response object with the JSON data and the specified status code
# TODO: 优化性能
        return Response.status(status).entity(jsonData.toString())
                .type("application/json").build();
    }

    /*
# 添加错误处理
     * Formats an error message into a JSON response.
     *
     * @param errorMessage The error message to be formatted.
     * @param statusCode The HTTP status code for the error response.
     * @return A Response object with the error message.
# 扩展功能模块
     */
    public static Response formatErrorResponse(String errorMessage, int statusCode) {
        // Create a JSON object with the error message
        JsonObject errorData = Json.createObjectBuilder()
                .add("error", errorMessage)
                .build();

        // Create a Response object with the error message and the specified status code
# 优化算法效率
        return Response.status(statusCode).entity(errorData.toString())
                .type("application/json\).build();
    }
# 添加错误处理

    // Additional methods can be added here for more complex response formatting
}

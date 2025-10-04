// 代码生成时间: 2025-10-04 20:01:46
package com.example.apitest;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

// 定义API测试工具的REST客户端接口
@Path("/apitest")
@RegisterRestClient
public interface ApiTestClient {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response testApi(String payload);
}

// 主类，包含应用程序的入口点
@QuarkusMain
public class ApiTestTool {

    // 应用程序的入口点
    public static void main(String... args) {
        Quarkus.run(ApiTestTool.class, args);
    }

    // 测试API的方法
    @Path("/test")
    public static class TestApiResource {

        private final ApiTestClient apiTestClient;

        // 使用构造函数注入API测试客户端
        public TestApiResource(ApiTestClient apiTestClient) {
            this.apiTestClient = apiTestClient;
        }

        // POST方法，用于发送测试请求
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response sendTestRequest(String payload) {
            try {
                // 使用API测试客户端发送请求
                Response response = apiTestClient.testApi(payload);
                // 返回响应
                return Response.status(response.getStatus()).entity(response.readEntity(String.class)).build();
            } catch (Exception e) {
                // 错误处理
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("An error occurred: " + e.getMessage())
                        .build();
            }
        }
    }
}

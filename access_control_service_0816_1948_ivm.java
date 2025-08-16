// 代码生成时间: 2025-08-16 19:48:09
package com.example.accesscontrol;

import io.quarkus.security.Authenticated;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

// 定义访问控制服务
@Path("/access")
public class AccessControlService {

    // 未授权用户访问的公共资源
    @GET
    @Path("/public")
    public Response publicResource() {
        return Response.ok("Public resource accessible to everyone").build();
    }

    // 只有经过身份验证的用户才能访问的资源
    @Authenticated
    @GET
    @Path("/private")
    public Response privateResource() {
        return Response.ok("Private resource accessible to authenticated users").build();
    }

    // 只有具有管理员角色的用户才能访问的资源
    @RolesAllowed("admin")
    @GET
    @Path("/admin")
    public Response adminResource() {
        return Response.ok("Admin resource accessible to users with admin role").build();
    }

    // 错误处理方法
    @GET
    @Path("/error")
    public Response errorResource() {
        // 模拟一个错误情况
        throw new RuntimeException("Access Denied");
    }
}

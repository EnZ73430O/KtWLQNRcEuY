// 代码生成时间: 2025-09-15 04:06:43
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import javax.ws.rs.core.Response;

/**
 * 集成测试工具，用于测试RESTful服务。
 */
@QuarkusTest
public class IntegrationTestTool {

    /**
     * 测试GET请求。
     */
    @Test
    public void testGetRequest() {
        // 设置测试的基础URL
        RestAssured.baseURI = "http";
        RestAssured.port = 8081;

        // 进行GET请求并验证响应状态码和内容
        Response response = given()
                .when()
                .get("/test");

        response.then()
                .statusCode(200)
                .body("testing", equalTo("test response"));
    }

    /**
     * 测试POST请求。
     */
    @Test
    public void testPostRequest() {
        // 设置测试的基础URL
        RestAssured.baseURI = "http";
        RestAssured.port = 8081;

        // 构造请求体
        String requestBody = "{"name":"John","age":30}";

        // 进行POST请求并验证响应状态码和内容
        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/create");

        response.then()
                .statusCode(201)
                .body("name", equalTo("John"))
                .body("age", equalTo(30));
    }

    /**
     * 测试PUT请求。
     */
    @Test
    public void testPutRequest() {
        // 设置测试的基础URL
        RestAssured.baseURI = "http";
        RestAssured.port = 8081;

        // 构造请求体
        String requestBody = "{"name":"Jane","age":25}";

        // 进行PUT请求并验证响应状态码和内容
        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/update/1");

        response.then()
                .statusCode(200)
                .body("name", equalTo("Jane"))
                .body("age", equalTo(25));
    }

    /**
     * 测试DELETE请求。
     */
    @Test
    public void testDeleteRequest() {
        // 设置测试的基础URL
        RestAssured.baseURI = "http";
        RestAssured.port = 8081;

        // 进行DELETE请求并验证响应状态码
        given()
                .when()
                .delete("/delete/1")
                .then()
                .statusCode(204);
    }
}

// 代码生成时间: 2025-09-04 16:13:40
package mathtools;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Path("/math")
@RegisterForReflection
public class MathToolsService {

    private static final Map<String, BiFunction<Double, Double, Double>> OPERATIONS = new HashMap<>();
    static {
        OPERATIONS.put("add", (a, b) -> a + b);
        OPERATIONS.put("subtract", (a, b) -> a - b);
        OPERATIONS.put("multiply", (a, b) -> a * b);
        OPERATIONS.put("divide", (a, b) -> {
            if (b == 0) {
                throw new IllegalArgumentException("Cannot divide by zero");
            }
            return a / b;
        });
    }

    /**
     * Perform a mathematical operation on two numbers.
     * @param operation The operation to perform (add, subtract, multiply, divide).
     * @param a The first number.
     * @param b The second number.
     * @return The result of the operation.
     */
    @GET
    @Path("/perform")
    public Response performOperation(
            @QueryParam("operation") String operation,
            @QueryParam("a") Double a,
            @QueryParam("b") Double b) {

        BiFunction<Double, Double, Double> func = OPERATIONS.get(operation.toLowerCase());
        if (func == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid operation").build();
        }

        try {
            double result = func.apply(a, b);
            return Response.ok().entity(String.valueOf(result)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
}

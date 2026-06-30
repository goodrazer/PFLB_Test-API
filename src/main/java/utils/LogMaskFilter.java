package utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class LogMaskFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        String body = requestSpec.getBody();
        if (body != null) {
            String maskedBody = body
                    .replaceAll("(\"username\"\\s*:\\s* \")[^\"]+(\")", "$1*******$2")
                    .replaceAll("(\"password\"\\s*:\\s* \")[^\"]+(\")", "$1*******$2");
            requestSpec.body(maskedBody);
        }
        return ctx.next(requestSpec, responseSpec);
    }
}
package com.thecatapi.api.utils.loggers;

import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CustomResponseLoggingFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           io.restassured.filter.FilterContext context) {
        var response = context.next(requestSpec, responseSpec);

        var responseOutputStream = new ByteArrayOutputStream();
        var responsePrintStream = new PrintStream(responseOutputStream);

        responsePrintStream.println("Response Status Code: " + response.getStatusCode());
        responsePrintStream.println("Response Body: " + response.getBody().asString());

        Allure.addAttachment("Response Logs", "text/plain", responseOutputStream.toString());

        return response;
    }
}
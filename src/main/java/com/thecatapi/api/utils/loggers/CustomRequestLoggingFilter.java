package com.thecatapi.api.utils.loggers;

import io.restassured.filter.Filter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.thecatapi.api.utils.AllureUtils.allureAttachText;

public class CustomRequestLoggingFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           io.restassured.filter.FilterContext context) {
        var requestOutputStream = new ByteArrayOutputStream();
        var requestPrintStream = new PrintStream(requestOutputStream);

        requestPrintStream.println("Request URI: " + requestSpec.getURI());
        requestPrintStream.println("Request Method: " + requestSpec.getMethod());
        requestPrintStream.println("Request Headers: " + requestSpec.getHeaders());
        requestPrintStream.println("Request Body: " + requestSpec.getBody());

        allureAttachText("Request Logs", requestOutputStream.toString());

        return context.next(requestSpec, responseSpec);
    }
}
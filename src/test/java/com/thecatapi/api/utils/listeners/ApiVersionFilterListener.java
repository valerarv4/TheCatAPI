package com.thecatapi.api.utils.listeners;

import com.thecatapi.api.config.EnvironmentConfig;
import com.thecatapi.api.utils.annotations.ApiVersion;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

public class ApiVersionFilterListener implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        return methods
                .stream()
                .filter(method -> {
                    var realMethod = method
                            .getMethod()
                            .getConstructorOrMethod()
                            .getMethod();
                    var annotation = realMethod.getAnnotation(ApiVersion.class);
                    if (isNull(annotation)) {
                        return true;
                    }

                    return Arrays.stream(annotation.value())
                            .anyMatch(version -> version == EnvironmentConfig.getInstance().getAppVersion());
                })
                .toList();
    }
}

package com.example.openfeignurlencodingissue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.resetAllRequests;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

@SpringBootTest
@AutoConfigureWireMock
class ExampleClientTest {

    @Autowired
    private ExampleClient client;

    @BeforeEach
    void setUp() {
        resetAllRequests();
        stubFor(get(urlPathMatching("/.*")).willReturn(ok("ok")));
    }

    @Test
    void pathVariableWithForwardSlash() {
        client.getString("path/variable");

        // actual: /something/path/variable
        verify(getRequestedFor(urlEqualTo("/something/path%2Fvariable")));
    }

    @Test
    void pathVariableWithColonAndForwardSlash() {
        client.getString("a:path/variable");

        // actual: /something/a%3Apath/variable
        verify(getRequestedFor(urlEqualTo("/something/a:path%2Fvariable")));
    }

    @Test
    void pathVariableWithColon() {
        client.getString("path:variable");

        // actual: /something/path%3Avariable
        verify(getRequestedFor(urlEqualTo("/something/path:variable")));
    }
}
package com.example.openfeignurlencodingissue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.resetAllRequests;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
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
    void queryParam() {
        client.search("something");

        // No special characters, this works as expected
        verify(getRequestedFor(urlPathEqualTo("/search"))
          .withQueryParam("text", equalTo("something")));
    }

    @Test
    void queryParamWithWordWildcard() {
        // Find entries that have the word 'something' anywhere in them
        client.search("%something%");

        // This works fine, because nothing in the string appears to be pre-encoded
        // (i.e. '%so' is not a valid hex value)
        verify(getRequestedFor(urlPathEqualTo("/search"))
          .withQueryParam("text", equalTo("%something%")));
    }

    @Test
    void queryParamWithAddressWildcard() {
        // Find entries that have the word 'address' anywhere in them
        client.search("%address%");

        // This fails because the text appears to have an encoded hex value in it
        // (i.e. '%ad' looks like it might be pre-encoded)

        // Actual request is:
        // GET /search?text=%address%25

        // Note how the first '%' is not encoded as expected
        verify(getRequestedFor(urlPathEqualTo("/search"))
          .withQueryParam("text", equalTo("%address%")));
    }

}

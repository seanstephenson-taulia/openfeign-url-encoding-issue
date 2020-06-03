package com.example.openfeignurlencodingissue;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "example", url = "http://localhost:8080")
public interface ExampleClient {

    /**
     * Imaginary search endpoint that supports wildcard matching similar to a database `LIKE` expression,
     * with `%` matching some sequence of characters.
     */
    @GetMapping(value = "/search")
    String search(@RequestParam("text") String text);

}

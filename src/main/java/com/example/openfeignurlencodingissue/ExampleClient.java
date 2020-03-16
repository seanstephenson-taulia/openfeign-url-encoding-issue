package com.example.openfeignurlencodingissue;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "example", url = "http://localhost:8080")
public interface ExampleClient {

    @GetMapping(value = "/something/{pathVariable}")
    String getString(@PathVariable("pathVariable") String pathVariable);
}

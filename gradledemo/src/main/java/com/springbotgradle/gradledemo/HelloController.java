package com.springbotgradle.gradledemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/hello/client")
public class HelloController {

	@GetMapping
    public String hello() {
        
        return "HI Gradle";
        
            
        
    }
}


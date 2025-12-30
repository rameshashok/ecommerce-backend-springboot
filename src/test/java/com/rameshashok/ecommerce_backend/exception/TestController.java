package com.rameshashok.ecommerce_backend.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/not-found")
    public String throwResourceNotFoundException() {
        throw new ResourceNotFoundException("Resource not found");
    }

    @GetMapping("/business-error")
    public String throwBusinessException() {
        throw new BusinessException("Business error");
    }

    @GetMapping("/generic-error")
    public String throwGenericException() {
        throw new RuntimeException("Generic error");
    }
}
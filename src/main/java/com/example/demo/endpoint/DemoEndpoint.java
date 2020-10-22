package com.example.demo.endpoint;

import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DemoEndpoint.BASE_URI)
public class DemoEndpoint {

  protected final static String BASE_URI = "/api";

  @GetMapping
  public void getAPIRoot() throws DemoException {
    throw new NotFoundException("API root path has no content");
  }

  @GetMapping("/**/")
  public void getUnknownAPISubPath() throws DemoException {
    throw new NotFoundException("API sub-path has no content");
  }
}

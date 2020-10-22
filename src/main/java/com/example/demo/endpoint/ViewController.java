package com.example.demo.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

  @RequestMapping({"/**/{[path:[^\\\\.]*}"})
  public String index() {
    return "forward:/index.html";
  }
}

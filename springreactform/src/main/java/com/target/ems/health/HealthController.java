package com.target.ems.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  @GetMapping("health")
  String health() {
      return "Ok";
  }
}

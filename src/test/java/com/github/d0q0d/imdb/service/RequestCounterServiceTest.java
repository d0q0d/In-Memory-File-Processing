package com.github.d0q0d.imdb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestCounterServiceTest {

  private RequestCounterService requestCounterService;

  @BeforeEach
  void setUp() {
    requestCounterService = new RequestCounterService();
  }

  @Test
  void shouldStartWithZero() {
    assertEquals(0, requestCounterService.getCount());
  }

  @Test
  void shouldIncrementCount() {
    requestCounterService.increment();
    assertEquals(1, requestCounterService.getCount());
  }

  @Test
  void shouldIncrementMultipleTimes() {
    requestCounterService.increment();
    requestCounterService.increment();
    requestCounterService.increment();
    assertEquals(3, requestCounterService.getCount());
  }
}

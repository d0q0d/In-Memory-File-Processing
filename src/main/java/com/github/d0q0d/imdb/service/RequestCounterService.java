package com.github.d0q0d.imdb.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class RequestCounterService {

  private final AtomicInteger requestCount = new AtomicInteger(0);

  public void increment() {
    requestCount.incrementAndGet();
  }

  public int getCount() {
    return requestCount.get();
  }
}

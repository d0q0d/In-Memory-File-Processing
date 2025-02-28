package com.github.d0q0d.imdb.api.adapter;

import com.github.d0q0d.imdb.service.RequestCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestCounterAdapter {

  private final RequestCounterService service;

  public int getCount() {
    return service.getCount();
  }
}

package com.github.d0q0d.imdb.api.controller;

import com.github.d0q0d.imdb.api.adapter.RequestCounterAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/request-counter")
@RequiredArgsConstructor
public class RequestCounterController {

  private final RequestCounterAdapter adapter;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public int getRequestCount() {
    return adapter.getCount();
  }
}

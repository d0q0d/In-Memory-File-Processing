package com.github.d0q0d.imdb.service.config;

import com.github.d0q0d.imdb.service.RequestCounterService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
@WebFilter("/*")
public class RequestCountingFilter implements Filter {

  private final RequestCounterService requestCounter;

  public RequestCountingFilter(RequestCounterService requestCounter) {
    this.requestCounter = requestCounter;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    requestCounter.increment();
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {}
}

package com.niek125.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.niek125.gateway.token.TokenVerifier;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JWTFilter extends ZuulFilter {
    private final TokenVerifier tokenVerifier;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return !RequestContext.getCurrentContext().getRequest().getServletPath().matches("/api/token/new");
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        if (!tokenVerifier.verify(context.getRequest().getHeader("Authorization"))) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}

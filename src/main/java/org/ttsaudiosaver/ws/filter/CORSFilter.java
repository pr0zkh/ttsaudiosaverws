package org.ttsaudiosaver.ws.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Accept-Ranges", "bytes");
            response.addHeader("Access-Control-Allow-Headers", "range, accept-encoding");
            response.addHeader("Accept-Ranges", "bytes");
            response.addHeader("Content-Range", "bytes");
            if (request.getHeader("Access-Control-Request-Method") != null
                    && "OPTIONS".equals(request.getMethod())) {
                // CORS "pre-flight" request
                response.addHeader("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE");
                response.addHeader("Access-Control-Allow-Headers",
                        "X-Requested-With,Origin,Content-Type, Accept");
            }
            filterChain.doFilter(request, response);
    }

}
package com.docpet.animalhospital.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;

public class CustomHttpFirewall implements HttpFirewall {

    private final DefaultHttpFirewall delegate;

    public CustomHttpFirewall() {
        this.delegate = new DefaultHttpFirewall();
    }

    @Override
    public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
        // Loại bỏ %0A từ request URI trước khi kiểm tra
        HttpServletRequest cleanedRequest = new HttpServletRequestWrapper(request) {
            @Override
            public String getRequestURI() {
                String uri = super.getRequestURI();
                if (uri != null) {
                    uri = uri.replace("%0A", "").replace("%0a", "").replace("\n", "").replace("\r", "");
                }
                return uri;
            }

            @Override
            public StringBuffer getRequestURL() {
                StringBuffer url = super.getRequestURL();
                if (url != null) {
                    String urlStr = url.toString();
                    urlStr = urlStr.replace("%0A", "").replace("%0a", "").replace("\n", "").replace("\r", "");
                    return new StringBuffer(urlStr);
                }
                return url;
            }

            @Override
            public String getQueryString() {
                String query = super.getQueryString();
                if (query != null) {
                    query = query.replace("%0A", "").replace("%0a", "").replace("\n", "").replace("\r", "");
                }
                return query;
            }
        };

        return delegate.getFirewalledRequest(cleanedRequest);
    }

    @Override
    public HttpServletResponse getFirewalledResponse(HttpServletResponse response) {
        return delegate.getFirewalledResponse(response);
    }
}


// 代码生成时间: 2025-08-03 09:44:35
package com.example.web;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.http.HttpClient;
import io.smallrye.mutiny.http.HttpRequest;
import io.smallrye.mutiny.http.HttpResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/web")
public class WebContentExtractor {

    private final HttpClient client;

    public WebContentExtractor() {
        this.client = HttpClient.create();
    }

    @GET
    @Path("/extract")
    public Uni<String> extractContent(@QueryParam("url") String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        return client
                .send(HttpRequest.create(HttpRequest.Method.GET, url))
                .onFailure().transformToUni(throwable -> Uni.createFrom().failure(throwable))
                .onItem().transform(response -> {
                    if (response.getStatus() == HttpResponse.Status.OK.code()) {
                        return response.getBody().prefetch().subscribeAsText();
                    } else {
                        throw new RuntimeException("Failed to fetch content from URL: " + url);
                    }
                })
                .onItem().transform(text -> text.toString());
    }

    // Additional methods can be added here for more functionality
}

// Note: This is a basic implementation and may require additional error handling and logging for a production environment.
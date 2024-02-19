package nethttp;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Ref:
 * https://www.baeldung.com/java-httpclient-post
 * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-httpclient/src/main/java/com/baeldung/httpclient/HttpClientPost.java
 */
public class NetHttp {
    public static void main(String[] args)
            throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        get();
        postSync();
        postAsync();
        postAsyncConcurrent();
        authenticate();
        postWithBody();
    }

    private static void get() throws IOException, InterruptedException, URISyntaxException {
        System.out.println("\n----------GET request-----------\n");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://postman-echo.com/get"))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\n--------Headers--------\n " + response.headers());
        System.out.println("\n--------Body--------\n " + response.body());

    }

    private static void postSync() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("\n----------POST request (sync)-----------\n");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://postman-echo.com/post"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\n--------Headers--------\n " + response.headers());
        System.out.println("\n--------Body--------\n " + response.body());
    }

    private static void postAsync() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        System.out.println("\n----------POST request (Async)-----------\n");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://postman-echo.com/post"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        CompletableFuture<HttpResponse<String>> response = HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\n--------Headers--------\n " + response.get().headers());
        System.out.println("\n--------Body--------\n " + response.get().body());
    }

    /**
     * We can combine Streams with CompletableFutures to issue several
     * requests concurrently and await their responses.
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void postAsyncConcurrent() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        System.out.println("\n----------POST request (Async + Concurrent)-----------\n");

        List<String> urls = List.of(
                "https://postman-echo.com/post",
                "https://postman-echo.com/post",
                "https://postman-echo.com/post");

        List<CompletableFuture<HttpResponse<String>>> completableFutures = urls.stream()
                .map(URI::create)
                .map(HttpRequest::newBuilder)
                .map(builder -> builder.POST(HttpRequest.BodyPublishers.noBody()))
                .map(HttpRequest.Builder::build)
                .map(request -> HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString()))
                .toList();

        completableFutures.stream().map(response -> {
            try {
                return response.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).forEach(result -> {
            System.out.println("\n--------Headers--------\n " + result.headers());
            System.out.println("\n--------Body--------\n " + result.body());
        });

        /*
            We can wait for all the requests to be complete so that we
            can process their responses all at once.
            We can also combine all the responses using the allOf and join methods,
            we can get a new CompletableFuture that holds our responses
        */
        System.out.println("\n----------POST request (Async + Concurrent) Combined all Features-----------\n");
        CompletableFuture<List<HttpResponse<String>>> combinedFutures =
                CompletableFuture
                        .allOf(completableFutures.toArray(new CompletableFuture[0]))
                        .thenApply(future ->
                                completableFutures.stream()
                                        .map(CompletableFuture::join)
                                        .toList()
                        );

        combinedFutures.get()
                .forEach(response -> {
                    System.out.println("\n--------Headers--------\n " + response.headers());
                    System.out.println("\n--------Body--------\n " + response.body());
                });
    }

    private static void authenticate()  throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        System.out.println("\n----------POST request (Authenticate)-----------\n");
        HttpClient client = HttpClient.newBuilder().authenticator(
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("postman", "postman".toCharArray());
                    }
                }).build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://postman-echo.com/basic-auth"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .header("Authorization",
                        "Basic " + Base64.getEncoder()
                                .encodeToString(("cG9zdG1hbjpwYXNzd29yZA==").getBytes())
                )
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\n--------Headers--------\n " + response.headers());
        System.out.println("\n--------Body--------\n " + response.body());

    }

    private static void postWithBody() throws IOException, InterruptedException {
        System.out.println("\n----------POST with Request Body-----------\n");
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://postman-echo.com/post"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"action\":\"hello\"}"))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n--------Headers--------\n " + response.headers());
        System.out.println("\n--------Body--------\n " + response.body());
    }
}

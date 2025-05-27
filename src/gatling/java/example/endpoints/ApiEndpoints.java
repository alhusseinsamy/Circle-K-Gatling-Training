package example.endpoints;

import static io.gatling.javaapi.core.CoreDsl.jmesPath;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.http.HttpRequestActionBuilder;

public class ApiEndpoints {

  public static final HttpRequestActionBuilder session = http("Session").get("/session").check(status().is(200));

  public static final HttpRequestActionBuilder products = http("Products")
      .get("/products")
      .queryParam("page", "#{pageNumber}")
      .queryParam("search", "#{searchKey}")
      .check(status().is(200));

  public static final HttpRequestActionBuilder login = http("Login")
      .post("/login")
      .formParam("username", "#{username}")
      .formParam("password", "#{password}")
      .check(status().is(200))
      .check(jmesPath("accessToken").saveAs("AccessToken"));

}

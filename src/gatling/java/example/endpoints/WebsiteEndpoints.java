package example.endpoints;

import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.http.HttpRequestActionBuilder;

public class WebsiteEndpoints {

  public static final HttpRequestActionBuilder homepage = http("Homepage").get("https://ecomm.gatling.io")
      .check(status().is(200));

  public static final HttpRequestActionBuilder loginPage = http("LoginPage").get("https://ecomm.gatling.io/login")
      .check(status().is(200));

}

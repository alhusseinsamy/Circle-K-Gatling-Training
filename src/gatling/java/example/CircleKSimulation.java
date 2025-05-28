package example;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

import java.util.List;

import static example.groups.ScenarioGroups.*;

public class CircleKSimulation extends Simulation {

  // Load VU count from system properties
  // Reference: https://docs.gatling.io/guides/passing-parameters/
  private static final int vu = Integer.getInteger("vu", 1);

  private static final String testType = System.getProperty("testType", "smoke");
  private static final int duration = Integer.getInteger("duration", 10);

  // Define HTTP configuration
  // Reference: https://docs.gatling.io/reference/script/protocols/http/protocol/
  private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://api-ecomm.gatling.io")
      .acceptHeader("application/json")
      .userAgentHeader(
          "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");

  // Define scenario
  // Reference: https://docs.gatling.io/reference/script/core/scenario/
  private static final ScenarioBuilder scenario = scenario("Scenario").exec(
      homeAnonymous,
      // pause(5, 15),
      authenticate,
      browseAndAddToCart,
      // pause(5, 15),
      buy);

  static final List<Assertion> assertions = List.of(
      // global().responseTime().percentile(90.0).lt(500),
      global().failedRequests().percent().lt(5.0));

  static final PopulationBuilder injectionProfile(ScenarioBuilder scn) {
    return switch (testType) {
      case "stress" -> scn.injectOpen(stressPeakUsers(vu).during(duration));
      case "smoke" -> scn.injectOpen(atOnceUsers(1));
      default -> scn.injectOpen(atOnceUsers(vu));
    };
  }

  // Define injection profile and execute the test
  // Reference: https://docs.gatling.io/reference/script/core/injection/
  {
    setUp(injectionProfile(scenario)).assertions(assertions).protocols(httpProtocol);
  }
}

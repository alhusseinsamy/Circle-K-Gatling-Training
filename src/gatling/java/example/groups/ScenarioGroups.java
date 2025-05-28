package example.groups;

import static io.gatling.javaapi.core.CoreDsl.*;
import io.gatling.javaapi.core.*;

import static example.endpoints.ApiEndpoints.*;
import static example.endpoints.WebsiteEndpoints.*;
import static example.actions.Actions.*;

public class ScenarioGroups {

  private static final FeederBuilder<Object> usersFeeder = jsonFile("data/users_dev.json").circular();

  public static final ChainBuilder homeAnonymous = group("homeAnonymous").on(
      homepage,
      session,
      setPageNumber,
      setSearchKey,
      products);

  public static final ChainBuilder authenticate = group("authenticate").on(
      loginPage,
      feed(usersFeeder),
      // pause(5, 15),
      login);

  public static final ChainBuilder browseAndAddToCart = group("addToCartGroup").on(
      products,
      // pause(5, 15),
      createAddToCartBody,
      addToCart);

  public static final ChainBuilder buy = group("buy").on(
      checkout);

}

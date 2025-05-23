package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StoreSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
        .baseUrl("https://fakestoreapi.com")
        .acceptHeader("application/json");

    Iterator<Map<String, Object>> productIdFeeder =
    Stream.generate(() -> {
        Map<String, Object> data = new HashMap<>();
        data.put("productId", ThreadLocalRandom.current().nextInt(1, 21));
        return data;
    }).iterator();

    ScenarioBuilder scn = scenario("Store User Flow")
        .feed(productIdFeeder)
        .exec(http("List products").get("/products"))
        .pause(1)
        .exec(http("View product detail").get("/products/#{productId}"))
        .pause(1)
        .exec(http("Simulate order")
            .post("/carts")
            .body(StringBody("{\"userId\": 1, \"date\": \"2020-03-02\", \"products\": [{\"productId\": #{productId}, \"quantity\": 1}]}"))
            .asJson())
        .pause(1)
        .exec(http("Broken endpoint").get("/nonexistent"));

    {
        setUp(
            scn.injectOpen(
                rampUsers(100).during(30),
                constantUsersPerSec(20).during(60)
            )
        ).protocols(httpProtocol);
    }
}
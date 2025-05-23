# Load Testing the Fake Store API

This project demonstrates load testing of a REST API using two industry tools:

- [Locust](https://locust.io/) – Python-based, fast to write, interactive UI
- [Gatling](https://gatling.io/) – Java-based, HTML reporting, strong scenario DSL

We tested the [Fake Store API](https://fakestoreapi.com), simulating user behavior with:
- product browsing
- product detail views
- cart submission
- requests to invalid endpoints (to measure failures)

## Project Structure
loadtest-fakestore/
├── locust/
│ ├── locustfile.py
│ ├── analyze_results.py
│ ├── results/
│ └── requirements.txt
├── gatling/
│ ├── pom.xml
│ └── src/test/java/simulations/StoreSimulation.java


---

## Locust

### Run it:

```bash
cd locust
locust -f locustfile.py --headless -u 500 -r 50 --run-time 2m --host=https://fakestoreapi.com --csv=results/test_run

## Gatling

### Run it:

```bash
cd gatling
mvn gatling:test
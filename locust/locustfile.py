from locust import HttpUser, task, between
import random

class WebsiteUser(HttpUser):
    wait_time = between(1, 3)

    @task(3)
    def browse_products(self):
        self.client.get("/products")

    @task(2)
    def view_product_detail(self):
        product_id = random.randint(1, 20)
        self.client.get(f"/products/{product_id}")

    @task(1)
    def simulate_order(self):
        self.client.post("/carts", json={
            "userId": 1,
            "date": "2020-03-02",
            "products": [{"productId": random.randint(1, 20), "quantity": 1}]
        })

    @task(1)
    def broken_endpoint(self):
        self.client.get("/nonexistent", name="/nonexistent (404)")

    @task(1)
    def slow_request(self):
        try:
            self.client.get("/products", timeout=0.001, name="/products (timeout)")
        except Exception as e:
            pass
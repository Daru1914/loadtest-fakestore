import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("locust/results/test_run_stats.csv")

df = df[~df['Name'].isin(['Aggregated', 'Total'])]

plt.figure(figsize=(10, 6))
plt.bar(df['Name'], df['Average Response Time'])
plt.title("Average Response Time per Endpoint")
plt.ylabel("ms")
plt.xticks(rotation=45, ha='right')
plt.tight_layout()
plt.savefig("locust/results/avg_response_time.png")
plt.show()
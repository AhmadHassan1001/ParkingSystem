from scipy.stats import gamma
import numpy as np
import matplotlib.pyplot as plt

class ProbabilityModel:
  def __init__(self, invariation, peak):
    self.invariation = invariation
    self.peak = peak
    self.scale = peak / (invariation - 1)

  def get_next_event_time(self):
    return gamma.rvs(self.invariation, scale=self.scale) # in hours

if __name__ == "__main__":
  model = ProbabilityModel(10, 5)
  event_times = [model.get_next_event_time() for _ in range(1000)]
  plt.hist(event_times, bins=50, density=True)
  plt.show()

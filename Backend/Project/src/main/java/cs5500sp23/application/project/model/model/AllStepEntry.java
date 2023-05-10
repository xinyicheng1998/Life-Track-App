package cs5500sp23.application.project.model.model;

import java.util.List;

/**
 * Return type for get-all-steps endpoint
 */
public class AllStepEntry {
  private List<MostStepEntry> result;

  public AllStepEntry(List<MostStepEntry> result) {
    this.result = result;
  }

  public List<MostStepEntry> getResult() {
    return result;
  }
}

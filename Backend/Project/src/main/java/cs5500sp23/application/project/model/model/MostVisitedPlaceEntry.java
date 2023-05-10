package cs5500sp23.application.project.model.model;

import java.util.Map;

public class MostVisitedPlaceEntry {
  private Map<String, Integer> visitedPlaces;
  private String mostVisitedPlace;

  public MostVisitedPlaceEntry(Map<String, Integer> visitedPlaces, String mostVisitedPlace) {
    this.visitedPlaces = visitedPlaces;
    this.mostVisitedPlace = mostVisitedPlace;
  }

  public Map<String, Integer> getVisitedPlaces() {
    return visitedPlaces;
  }

  public String getMostVisitedPlace() {
    return mostVisitedPlace;
  }
}

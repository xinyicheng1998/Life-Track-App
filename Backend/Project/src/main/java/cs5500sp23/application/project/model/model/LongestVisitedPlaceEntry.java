package cs5500sp23.application.project.model.model;

import java.util.Map;

public class LongestVisitedPlaceEntry {
  private Map<String, Integer> visitedPlaces;
  private String longestVisitedPlace;

  public LongestVisitedPlaceEntry(Map<String, Integer> visitedPlaces, String longestVisitedPlace) {
    this.visitedPlaces = visitedPlaces;
    this.longestVisitedPlace = longestVisitedPlace;
  }

  public Map<String, Integer> getVisitedPlaces() {
    return visitedPlaces;
  }

  public String getLongestVisitedPlace() {
    return longestVisitedPlace;
  }

}

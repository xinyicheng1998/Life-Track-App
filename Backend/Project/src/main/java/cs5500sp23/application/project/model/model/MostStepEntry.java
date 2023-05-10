package cs5500sp23.application.project.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MostStepEntry {
  private LocalDate date;
  private Integer totalSteps;
  private Double calories;

  private List<ActivitySummary> summaryActivities;
  public MostStepEntry(List<ActivitySummary> summaryActivities, LocalDate date, Integer totalSteps,
      Double calories) {
    this.summaryActivities = summaryActivities;
    this.date = date;
    this.totalSteps = totalSteps;
    this.calories = calories;
  }

  public List<ActivitySummary> getSummaryActivities() {
    return summaryActivities;
  }

  public String getDate() {
    return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }

  @JsonIgnore
  public LocalDate getRealDate(){
    return date;
  }

  public Integer getTotalSteps() {
    return totalSteps;
  }

  public Double getCalories() {
    return calories;
  }
}

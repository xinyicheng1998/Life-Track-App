package cs5500sp23.application.project.model.model;

/**
 * Represent one activity entry inside summary
 */
public class ActivitySummary {
  private ActivityType activityType;
  private String group;
  private Double duration;
  private Double distance;
  private Integer steps;
  private Double calories;

  public ActivitySummary(ActivityType activityType, String group, Double duration, Double distance,
      Integer steps, Double calories) {
    this.activityType = activityType;
    this.group = group;
    this.duration = duration;
    this.distance = distance;
    this.steps = steps;
    this.calories = calories;
  }

  public ActivitySummary(ActivityType activityType, String group, Double duration, Double distance) {
    this.activityType = activityType;
    this.group = group;
    this.duration = duration;
    this.distance = distance;
  }

  public String getActivityType() {
    return activityType.getType();
  }

  public String getGroup() {
    return group;
  }

  public Double getDuration() {
    return duration;
  }

  public Double getDistance() {
    return distance;
  }

  public Integer getSteps() {
    return steps;
  }

  public Double getCalories() {
    return calories;
  }

  public void setActivityType(ActivityType activityType) {
    this.activityType = activityType;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public void setDuration(Double duration) {
    this.duration = duration;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }

  public void setSteps(Integer steps) {
    this.steps = steps;
  }

  public void setCalories(Double calories) {
    this.calories = calories;
  }
}

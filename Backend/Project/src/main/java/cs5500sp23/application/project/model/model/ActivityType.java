package cs5500sp23.application.project.model.model;

public enum ActivityType {
  WALKING("walking"),
  TRANSPORT("transport"),
  CYCLING("cycling"),
  RUNNING("running");
  private final String type;
  ActivityType(String type) {
    this.type = type;
  }
  public String getType() {
    return type;
  }

  public static ActivityType buildType(String type) {
    for (ActivityType at : ActivityType.values()) {
      if (at.getType().equals(type)) {
        return at;
      }
    }
    return null;
  }
}
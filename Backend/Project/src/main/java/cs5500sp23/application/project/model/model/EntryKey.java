package cs5500sp23.application.project.model.model;

public enum EntryKey {
  SUMMARY("summary"),
  LAST_UPDATE("lastUpdate"),
  DATE_ID("dateID"),
  PERSON_ID("personID"),
  CAL_IDLE("caloriesIdle"),
  SEGMENTS("segments");

  private final String key;
  EntryKey(String key) {
    this.key = key;
  }
  public String getKey() {
    return key;
  }
}

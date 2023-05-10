package cs5500sp23.application.project.model.model;

public class CreateUserEntry {
  private boolean success;

  public CreateUserEntry(boolean success) {
    this.success = success;
  }

  public boolean getSuccess() {
    return this.success;
  }
}

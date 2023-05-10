package cs5500sp23.application.project.model.model;

/**
 * Return type for update-user endpoint
 */
public class UpdateUserEntry {

    private boolean success;

    public UpdateUserEntry(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return this.success;
    }

}

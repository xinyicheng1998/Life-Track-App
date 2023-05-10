package cs5500sp23.application.project.model.model;

/**
 * Return type for delete-user endpoint
 */
public class DeleteUserEntry {
    private boolean success;

    public DeleteUserEntry(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return this.success;
    }

}

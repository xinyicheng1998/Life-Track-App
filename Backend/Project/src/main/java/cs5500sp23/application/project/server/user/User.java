package cs5500sp23.application.project.server.user;

import cs5500sp23.application.project.model.DAO.UserDAO;
import cs5500sp23.application.project.model.model.CreateUserEntry;
import cs5500sp23.application.project.model.model.DeleteUserEntry;
import cs5500sp23.application.project.model.model.UpdateUserEntry;

public class User {

    private UserDAO userDAO;

    public User() {
        this.userDAO = UserDAO.getInstance();
    }

    public CreateUserEntry createUser(String firstName, String lastName, String email)
        throws IllegalArgumentException {
        CreateUserEntry result = null;
        try {
            result = userDAO.createUser(firstName, lastName, email);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return result;
    }

    public UpdateUserEntry updateUser(String newFirstName, String newLastName, String email)
        throws IllegalArgumentException {
        UpdateUserEntry result = null;
        try {
            result = userDAO.updateUser(newFirstName, newLastName, email);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return result;
    }

    public DeleteUserEntry deleteUser(String email)
        throws IllegalArgumentException {
        DeleteUserEntry result = null;
        try {
            result = userDAO.deleteUser(email);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return result;
    }


}

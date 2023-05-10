package cs5500sp23.application.project.server.user;

import cs5500sp23.application.project.model.model.CreateUserEntry;
import cs5500sp23.application.project.model.model.DeleteUserEntry;
import cs5500sp23.application.project.model.model.UpdateUserEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping(path = "/create-user", produces = "application/json")
    public ResponseEntity<?> createUser(
        @RequestParam("firstName") String firstName,
        @RequestParam("lastName") String lastName,
        @RequestParam("email") String email) {
        User user = new User();
        CreateUserEntry result;
        try {
            result = user.createUser(firstName, lastName, email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/update-user", produces = "application/json")
    public ResponseEntity<?> updateUser(@RequestParam("firstName") String newFirstName,
        @RequestParam("lastName") String newLastName,
        @RequestParam("email") String email) {
        User user = new User();
        UpdateUserEntry result;
        try {
            result = user.updateUser(newFirstName, newLastName, email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/delete-user", produces = "application/json")
    public ResponseEntity<?> deleteUser(@RequestParam("email") String email) {
        User user = new User();
        DeleteUserEntry result;
        try {
            result = user.deleteUser(email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

}

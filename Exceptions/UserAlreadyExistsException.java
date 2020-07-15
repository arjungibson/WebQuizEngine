package engine.Exceptions;


public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("A user with the given email already exists");
    }
}

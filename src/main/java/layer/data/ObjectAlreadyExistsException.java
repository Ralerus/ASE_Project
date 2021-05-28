package layer.data;

public class ObjectAlreadyExistsException extends Exception{
    public ObjectAlreadyExistsException() {
    }

    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}

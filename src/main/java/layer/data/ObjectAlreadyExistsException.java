package layer.data;

public class ObjectAlreadyExistsException extends Exception{
    public ObjectAlreadyExistsException() {
        super();
    }

    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}

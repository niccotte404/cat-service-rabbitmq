package itmo.dev.owner_microservice.exceptions;

public class OwnerNotFoundException extends RuntimeException{

    public OwnerNotFoundException(String message) {
        super(message);
    }
}

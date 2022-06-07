package vn.techmaster.demobank.exception;

// lỗi 500
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}

package vn.edu.iuh.fit.userservice.exception.errors;

public class NotFoundException extends  RuntimeException{
    public  NotFoundException (String message){
        super(message);
    }
}

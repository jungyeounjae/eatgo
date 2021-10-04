package kr.co.fastcampus.eatgo.application;

public class PasswordWrongException extends RuntimeException{

    private String message;

    public PasswordWrongException(String email) {
        this.message = email;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

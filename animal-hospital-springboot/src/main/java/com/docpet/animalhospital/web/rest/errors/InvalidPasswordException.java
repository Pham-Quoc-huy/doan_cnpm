package com.docpet.animalhospital.web.rest.errors;

public class InvalidPasswordException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super("Incorrect password", "userManagement", "incorrectpassword");
    }
}


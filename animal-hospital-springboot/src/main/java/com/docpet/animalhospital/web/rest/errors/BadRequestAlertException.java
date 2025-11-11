package com.docpet.animalhospital.web.rest.errors;

import java.net.URI;

public class BadRequestAlertException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String entityName;
    private final String errorKey;

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        this(defaultMessage, entityName, errorKey, null);
    }

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey, URI type) {
        super(defaultMessage);
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }
}


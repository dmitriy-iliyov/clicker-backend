package com.clicker.exception;

import com.clicker.contracts.exceptions.models.BaseInternalServerException;

public class NullScriptException extends BaseInternalServerException {

    public NullScriptException() {
    }

    public NullScriptException(String message) {
        super(message);
    }
}

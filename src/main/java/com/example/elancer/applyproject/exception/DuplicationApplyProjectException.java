package com.example.elancer.applyproject.exception;

public class DuplicationApplyProjectException extends RuntimeException {

    private static final String MESSAGE = "중복된 지원 프로젝트가 확인됩니다. 이미 해당 프로젝트에 지원했습니다.";

    public DuplicationApplyProjectException() {
        super(MESSAGE);
    }
}

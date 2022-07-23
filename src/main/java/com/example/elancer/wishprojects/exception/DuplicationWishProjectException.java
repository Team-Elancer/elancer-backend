package com.example.elancer.wishprojects.exception;

public class DuplicationWishProjectException extends RuntimeException {

    private static final String MESSAGE = "중복된 찜 프로젝트가 확인됩니다. 이미 해당 프로젝트를 찜했습니다.";

    public DuplicationWishProjectException() {
        super(MESSAGE);
    }
}

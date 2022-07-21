package com.example.elancer.freelancer.exception;

public class NotExistFreelancerThumbnailException extends RuntimeException {

    private static final String MESSAGE = "섬네일이 존재하지 않습니다. 잘못된 요청입니다.";

    public NotExistFreelancerThumbnailException() {
        super(MESSAGE);
    }
}

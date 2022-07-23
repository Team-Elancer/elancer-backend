package com.example.elancer.applyproject.controller;

import com.example.elancer.applyproject.dto.ApplyProjectCreateRequest;
import com.example.elancer.applyproject.exception.DuplicationApplyProjectException;
import com.example.elancer.applyproject.service.ApplyProjectService;
import com.example.elancer.common.exception.ExceptionMessage;
import com.example.elancer.common.exception.WrongRequestException;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishprojects.exception.NotExistProjectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApplyProjectController {

    private final ApplyProjectService applyProjectService;

    @PostMapping(ApplyProjectControllerPath.CREATE_APPLY_PROJECT)
    public ResponseEntity<Void> createApplyProject(
            @Validated @RequestBody ApplyProjectCreateRequest applyProjectCreateRequest,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        applyProjectService.createApplyProject(applyProjectCreateRequest, memberDetails);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DuplicationApplyProjectException.class})
    public ResponseEntity<ExceptionMessage> handleDuplicationApplyProjectException(DuplicationApplyProjectException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotExistProjectException.class})
    public ResponseEntity<ExceptionMessage> handleNotExistProjectException(NotExistProjectException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

package com.tripsync.trip_management.advice;

import com.tripsync.trip_management.dto.response.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class TripExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TripExceptionHandler.class);

    private ResponseEntity<ApiErrorResponse> buildResponseEntity(Exception exception, HttpStatus httpStatus, String errorMessage) {
        LOGGER.error(exception.getLocalizedMessage(), exception);

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setStatusName(httpStatus);
        apiErrorResponse.setStatus(httpStatus.value());
        apiErrorResponse.setMessage(exception.getMessage());
        apiErrorResponse.setErrors(List.of(errorMessage));

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }
}

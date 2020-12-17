package com.thienhoa.blog.controller;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.thienhoa.blog.payload.ApiResponse;
import com.thienhoa.blog.service.GaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/ga/")
@AllArgsConstructor
public class GaController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private GaService gaService;

    @GetMapping("/getga")
    public ResponseEntity<?> getDataGa() {
        try {
            logger.info("************** Get data Google Analytics START ********************");
            try {
                AnalyticsReporting service = gaService.initializeAnalyticsReporting();

                GetReportsResponse response = gaService.getReport(service);
                gaService.printResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity(new ApiResponse(true, "Get data Google Analytics successfully!", 123),
                    HttpStatus.OK);
        }
        finally {
            logger.info("************** Get data Google Analytics stop ********************");
        }
    }
}

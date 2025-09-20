package com.viv.loans.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viv.loans.constants.LoansConstants;
import com.viv.loans.dto.ErrorResponseDto;
import com.viv.loans.dto.LoansContactInfoDto;
import com.viv.loans.dto.LoansDto;
import com.viv.loans.dto.ResponseDto;
import com.viv.loans.service.ILoansService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Loan", description = "CRUD REST APIs for Loans microservices")
@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@RequiredArgsConstructor
@Validated
public class LoansController {

        @Value("${build.version}")
        private String buildVersion;

        @Autowired
        private Environment environment;

        @Autowired
        private LoansContactInfoDto loansContactInfoDto;

        private final ILoansService iLoansService;

        @Operation(summary = "Create Loan REST API", description = "REST API to create new loan")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PostMapping("/create")
        public ResponseEntity<ResponseDto> createLoan(
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
                iLoansService.createLoan(mobileNumber);
                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
        }

        @Operation(summary = "Fetch Loan Details REST API", description = "REST API to fetch loan details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/fetch")
        public ResponseEntity<LoansDto> fetchLoanDetails(
                        @RequestHeader("viv-correlation-id") String correlationId,
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
                LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);
                log.info("Fetching Loan Details for correlationId: {} ", correlationId);
                return ResponseEntity.status(HttpStatus.OK).body(loansDto);
        }

        @Operation(summary = "Update Loan Details REST API", description = "REST API to update loan details based on a loan number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PutMapping("/update")
        public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
                boolean isUpdated = iLoansService.updateLoan(loansDto);
                if (isUpdated) {
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
                } else {
                        return ResponseEntity
                                        .status(HttpStatus.EXPECTATION_FAILED)
                                        .body(new ResponseDto(LoansConstants.STATUS_417,
                                                        LoansConstants.MESSAGE_417_UPDATE));
                }
        }

        @Operation(summary = "Delete Loan Details REST API", description = "REST API to delete Loan details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @DeleteMapping("/delete")
        public ResponseEntity<ResponseDto> deleteLoanDetails(
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
                boolean isDeleted = iLoansService.deleteLoan(mobileNumber);
                if (isDeleted) {
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
                } else {
                        return ResponseEntity
                                        .status(HttpStatus.EXPECTATION_FAILED)
                                        .body(new ResponseDto(LoansConstants.STATUS_417,
                                                        LoansConstants.MESSAGE_417_DELETE));
                }
        }

        @GetMapping("/build-info")
        public ResponseEntity<String> getBuildInfo() {
                return ResponseEntity.ok(buildVersion);
        }

        @Operation(summary = "Get Java Version REST API", description = "REST API to get the java version of the service")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/java-version")
        public ResponseEntity<String> getJavaVersion() {
                return ResponseEntity.ok(environment.getProperty("JAVA_HOME"));
        }

        @Operation(summary = "Get Contact Details REST API", description = "REST API to get the contact details of the service")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/contact-info")
        public ResponseEntity<LoansContactInfoDto> getContactnfo() {

                return ResponseEntity.ok(loansContactInfoDto);

        }

}

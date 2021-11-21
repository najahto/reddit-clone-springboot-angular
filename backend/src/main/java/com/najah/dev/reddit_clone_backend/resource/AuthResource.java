package com.najah.dev.reddit_clone_backend.resource;

import com.najah.dev.reddit_clone_backend.dto.AuthenticationResponse;
import com.najah.dev.reddit_clone_backend.dto.LoginRequest;
import com.najah.dev.reddit_clone_backend.dto.RefreshTokenRequest;
import com.najah.dev.reddit_clone_backend.dto.RegisterRequest;
import com.najah.dev.reddit_clone_backend.service.implementation.AuthServiceImpl;
import com.najah.dev.reddit_clone_backend.service.implementation.RefreshTokenServiceImpl;
import com.najah.dev.reddit_clone_backend.utility.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthResource {

    private final AuthServiceImpl authService;
    private final RefreshTokenServiceImpl refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<Response> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("User registered successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<Response> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("User account activated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("logout")
    public ResponseEntity<Response> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("User logout successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}

package vn.edu.iuh.fit.userservice.controllers;

import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.userservice.dtos.requests.*;
import vn.edu.iuh.fit.userservice.dtos.responses.AuthenticationResponse;
import vn.edu.iuh.fit.userservice.dtos.responses.IntrospectResponse;
import vn.edu.iuh.fit.userservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;
import vn.edu.iuh.fit.userservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.userservice.services.AuthenticationService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String githubSecret;

    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}")
    private String githubRedirectUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUrl;


    @PostMapping("/token")
    ResponseEntity<MessageResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return SuccessEntityResponse.OkResponse("Đăng nhập thành công", result);
    }

    @PostMapping("/introspect")
    ResponseEntity<MessageResponse<IntrospectResponse>> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return SuccessEntityResponse.OkResponse("Kiểm tra token thành công", result);
    }

    @PostMapping("/logout")
    ResponseEntity<MessageResponse<Void>> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return SuccessEntityResponse.OkResponse("Đăng xuất thành công", null);
    }

    @PostMapping("/refresh")
    ResponseEntity<MessageResponse<AuthenticationResponse>> refresh(@RequestBody @Valid RefreshRequest request) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return SuccessEntityResponse.OkResponse("Làm mới token thành công", result);
    }

    @PostMapping("/forgot-password")
    public MessageResponse<Void> sendOtp(@RequestParam String email) {
        authenticationService.processForgotPassword(email);
        return MessageResponse.<Void>builder()
                .statusCode(200)
                .success(true)
                .message("Đã gửi OTP về email của bạn, xin vui lòng kiểm tra.")
                .build();
    }

    @PostMapping("/reset-password")
    public MessageResponse<Void> verifyOtp(@RequestBody ResetPasswordRequest otpRequest) {
        authenticationService.resetPassword(otpRequest);
        return MessageResponse.<Void>builder()
                .statusCode(200)
                .success(true)
                .message("Đặt lại mật khẩu thành công.")
                .build();
    }

    @GetMapping("/github/login")
    public void redirectToGitHub(HttpServletResponse response) throws IOException {
        String redirectUri = URLEncoder.encode(githubRedirectUrl, StandardCharsets.UTF_8);
        String state = UUID.randomUUID().toString(); // dùng để CSRF protection

        String githubAuthUrl = "https://github.com/login/oauth/authorize"
                + "?client_id=" + githubClientId
                + "&redirect_uri=" + redirectUri
                + "&scope=read:user"
                + "&state=" + state;
        response.sendRedirect(githubAuthUrl);
    }

    @GetMapping("/github/callback")
    public ResponseEntity<?> githubCallback(@RequestParam String code, @RequestParam String state) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", githubClientId);
        params.add("client_secret", githubSecret);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://github.com/login/oauth/access_token", request, Map.class);

        String accessToken = (String) response.getBody().get("access_token");

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);

        ResponseEntity<Map> userResponse = restTemplate.exchange(
                "https://api.github.com/user", HttpMethod.GET, userRequest, Map.class);

        Map<String, Object> userInfo = userResponse.getBody();
        GitHubInforRequest githubrequest = new GitHubInforRequest();
        githubrequest.setLogin((String) userInfo.get("login"));
        githubrequest.setAvatar_url((String) userInfo.get("avatar_url"));
        githubrequest.setId(userInfo.get("id").toString());
        return ResponseEntity.ok( this.authenticationService.loginWithGitHub(githubrequest));
    }

    @GetMapping("/google/login")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        String redirectUri = URLEncoder.encode(googleRedirectUrl, StandardCharsets.UTF_8);
        String state = UUID.randomUUID().toString(); // CSRF protection

        // Google authorization URL
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + googleClientId
                + "&redirect_uri=" + googleRedirectUrl
                + "&scope=openid profile email"
                + "&response_type=code"
                + "&state=" + state;

        response.sendRedirect(googleAuthUrl);
    }

    @GetMapping("/google/callback")
    public ResponseEntity<?> googleCallback(@RequestParam String code, @RequestParam String state) {
        System.out.println("LOGIN SUCCESS");
        String clientId = googleClientId;
        String clientSecret = googleSecret;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", googleRedirectUrl);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token", request, Map.class);

        String accessToken = (String) response.getBody().get("access_token");

        // Lấy thông tin người dùng từ Google
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);

        ResponseEntity<Map> userResponse = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo", HttpMethod.GET, userRequest, Map.class);

        Map<String, Object> userInfo = userResponse.getBody();
        String email = (String) userInfo.get("email");

        String picture = (String ) userInfo.get("picture");
        String name = (String) userInfo.get("name");
        return ResponseEntity.ok(this.authenticationService.loginWithGoogle(GoogleInfoRequest.
                builder()
                .picture(picture)
                .email(email)
                .name(name).build()));
    }

    @GetMapping("/test")
    public  String testAuth(){
        return "Hello World";
    }



}

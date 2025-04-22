package vn.edu.iuh.fit.userservice.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.userservice.dtos.requests.*;
import vn.edu.iuh.fit.userservice.dtos.responses.AuthenticationResponse;
import vn.edu.iuh.fit.userservice.dtos.responses.IntrospectResponse;
import vn.edu.iuh.fit.userservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.userservice.entities.InvalidatedToken;
import vn.edu.iuh.fit.userservice.entities.User;
import vn.edu.iuh.fit.userservice.enums.Role;
import vn.edu.iuh.fit.userservice.exception.errors.*;
import vn.edu.iuh.fit.userservice.mapper.UserMapper;
import vn.edu.iuh.fit.userservice.repositories.InvalidatedTokenRepository;
import vn.edu.iuh.fit.userservice.repositories.UserRepository;
import vn.edu.iuh.fit.userservice.utils.OtpUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Autowired
    private EmailService emailService;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;
    @Autowired
    private UserMapper userMapper;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        log.info("Token: " + token);

        try {
            verifyToken(token, false);

        } catch (UnauthorizedException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .userId(isValid ? SignedJWT.parse(token).getJWTClaimsSet().getSubject() : null)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Sai tên đăng nhập hoặc mật khẩu"));
        if(!user.isActive()) {
            throw new UnauthorizedException("Tài khoản không còn hoạt động!");
        }

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) throw new UnauthorizedException("Sai tên đăng nhập hoặc mật khẩu");

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .user(userMapper.toUserResponse(user))
                .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

//            invalidatedTokenRepository.save(invalidatedToken);
            redisService.saveInvalidatedToken(jit, request.getToken());
        } catch (UnauthorizedException exception) {
            log.info("Token already expired");
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
//        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
//
//        InvalidatedToken invalidatedToken =
//                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

//        invalidatedTokenRepository.save(invalidatedToken);

        if (redisService.isTokenInvalidated(jit)) {
            throw new UnauthorizedException("Token đã bị vô hiệu hóa");
        }

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user =
                userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("Sai tên đăng nhập hoặc mật khẩu"));

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("zycute")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("userId", user.getUserId())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date())))
            throw new UnauthorizedException("Sai tên đăng nhập hoặc mật khẩu");

//        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
//            throw new UnauthorizedException("Sai tên đăng nhập hoặc mật khẩu");

        if (redisService.isTokenInvalidated(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new UnauthorizedException("Token đã bị vô hiệu hóa");

        return signedJWT;
    }

    private String buildScope(User user) {
        if (user.getRole() == null) {
            return "";
        }

        return "ROLE_" + user.getRole().name();
    }

    public void processForgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này!"));

        String otpCode = OtpUtils.generateOtp();

        redisService.saveOtp(email, otpCode);

        emailService.sendOtpEmail(email, otpCode);
    }

    public void resetPassword(ResetPasswordRequest otpRequest) {
        String otp = otpRequest.getOtp();
        String email = otpRequest.getEmail();
        String newPassword = otpRequest.getNewPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại."));

        String storedOtp = redisService.getOtp(email);
        if (storedOtp == null) {
            throw new NotFoundException("OTP đã hết hạn hoặc không tồn tại.");
        }

        if (!storedOtp.equals(otp)) {
            throw new BadRequestException("OTP không hợp lệ. Vui lòng thử lại.");
        }




        // Cập nhật mật khẩu mới
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        redisService.deleteOtp(email);

    }

    public AuthenticationResponse loginWithGitHub(GitHubInforRequest githubInfo){
            User foundUser = userRepository.findByEmail(githubInfo.getId()).orElse(null);
            if(foundUser == null) {
                foundUser = new User();
                foundUser.setEmail(githubInfo.getId());
                foundUser.setAvatarUrl(githubInfo.getAvatar_url());
                foundUser.setFullName(githubInfo.getLogin());
                foundUser.setRole(Role.USER);
                foundUser.setActive(true);
                foundUser = userRepository.save(foundUser);
            }

            var token = generateToken(foundUser);
            return AuthenticationResponse.builder().token(token).authenticated(true).user(userMapper.toUserResponse(foundUser)).build();
    }

    public AuthenticationResponse loginWithGoogle(GoogleInfoRequest googleInfoRequest){
            User foundUser = userRepository.findByEmail(googleInfoRequest.getEmail()).orElse(null);
            if(foundUser == null) {
                foundUser = new User();
                foundUser.setEmail(googleInfoRequest.getEmail());
                foundUser.setAvatarUrl(googleInfoRequest.getPicture());
                foundUser.setFullName(googleInfoRequest.getName());
                foundUser = userRepository.save(foundUser);
            }
            var token = generateToken(foundUser);

            return AuthenticationResponse.builder().token(token).authenticated(true).user(userMapper.toUserResponse(foundUser)).build();
    }
}

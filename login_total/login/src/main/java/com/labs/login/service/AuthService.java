package com.labs.login.service;

import com.labs.login.component.PasswordEncoding;
import com.labs.login.component.StateValueComponent;
import com.labs.login.component.Base64Encoding;
import com.labs.login.db.Auth;
import com.labs.login.db.User;
import com.labs.login.dto.auth.LoginRequestDto;
import com.labs.login.exception.BadRequestException;
import com.labs.login.exception.NotFoundException;
import com.labs.login.exception.UnAuthException;
import com.labs.login.repository.AuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger("AuthService");

    @Value("${token.string.length}")
    private int TOKEN_LENGTH;

    @Value("${token.number.bound}")
    private int TOKEN_NUMBER_BOUND;

    @Value("${token.expire.minute}")
    private long TOKEN_EXPIRE_MINUTE;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StateValueComponent stateValueComponent;

    @Autowired
    private PasswordEncoding passwordEncoding;

    @Autowired
    private Base64Encoding base64Encoding;

    private String tokenMaker() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(this.TOKEN_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        int generatedNumber = random.nextInt(this.TOKEN_NUMBER_BOUND);
        return generatedString + generatedNumber;
    }

    public Auth authByUserId(UUID userId) {
        Optional<Auth> auth = this.authRepository.findByUserId(userId);
        if (auth.isEmpty()) throw new NotFoundException("auth not found :" + userId.toString());
        return auth.get();
    }

    public void createAuth(UUID userId) {
        Date date = new Date();
        Auth auth = Auth.builder()
                .userId(userId)
                .token("")
                .lastLoginDate(date)
                .loginFailCount(0)
                .status(stateValueComponent.getAuthExpire())
                .build();
        Auth saveAuth = this.authRepository.save(auth);
        logger.info("save auth is success: " + saveAuth.toString());
    }

    public void deleteAuth(UUID userId) {
        Auth auth = this.authByUserId(userId);
        auth.setStatus(stateValueComponent.getAuthExpire());
        auth.setToken("");
        this.authRepository.save(auth);
    }

    public Auth login(LoginRequestDto loginRequestDto) {
        User user = this.userService.userByUserAccount(loginRequestDto.getAccount(), true);
        if (passwordEncoding.matches(loginRequestDto.getPassword(), user.getPassword())) {
            String token = this.tokenMaker();

            Auth auth = this.authByUserId(user.getUserId());
            auth.setToken(token);
            auth.setStatus(stateValueComponent.getAuthActive());
            auth.setLastLoginDate(new Date());
            Auth updateAuth = this.authRepository.save(auth);
            updateAuth.setAuthToken(base64Encoding.encrypt(updateAuth.getToken()));
            logger.info("login is success : " + updateAuth.toString());
            return updateAuth;
        } else {
            throw new UnAuthException("auth login fail :" + loginRequestDto.getAccount());
        }
    }

    public String logout(UUID userid) {
        Auth auth = this.authByUserId(userid);
        auth.setToken("");
        auth.setStatus(stateValueComponent.getAuthLogOut());
        auth.setLastLogOutDate(new Date());
        this.authRepository.save(auth);
        this.logger.info("logout is success : " + auth.getUserId().toString());
        return "logout is success";
    }

    public boolean tokenCheckInCookie(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            String userId = "";
            String authToken = "";
            for ( Cookie cookie : cookies) {
                if (cookie.getName().equals("auth_token")) {
                    authToken = cookie.getValue();
                }
                if (cookie.getName().equals("user_id")) {
                    userId = cookie.getValue();
                }
            }
            if (userId.isBlank() || authToken.isBlank()) throw new UnAuthException("cookie not have token or user id");
            return this.tokenCheck(UUID.fromString(userId), authToken);
        }
        else {
            throw new UnAuthException("no cookie");
        }
    }

    public boolean tokenCheck(UUID userId, String authToken) {
        Auth auth = this.authByUserId(userId);
        if (!auth.getToken().equals(base64Encoding.decrypt(authToken))) throw new UnAuthException("token is not match: " + auth.getUserId().toString());
        if (auth.getStatus() != stateValueComponent.getAuthActive()) throw new UnAuthException("token is expire: " + auth.getUserId().toString());
        long diffDate = (new Date().getTime() - auth.getLastLoginDate().getTime()) / 60000;
        if (diffDate >= this.TOKEN_EXPIRE_MINUTE) {
            auth.setStatus(stateValueComponent.getAuthExpire());
            auth.setToken("");
            this.authRepository.save(auth);
            throw new UnAuthException("token is expired: " + auth.getUserId().toString());
        }
        return true;
    }
}

package com.labs.login.service;

import com.labs.login.component.PasswordEncoding;
import com.labs.login.component.StateValueComponent;
import com.labs.login.db.Auth;
import com.labs.login.db.User;
import com.labs.login.exception.BadRequestException;
import com.labs.login.exception.NotFoundException;
import com.labs.login.exception.UnAuthException;
import com.labs.login.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger("UserService");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoding passwordEncoding;

    @Autowired
    private StateValueComponent stateValueComponent;

    public User userByStringUserId(String userId, boolean liveCheck) {
        if (liveCheck) {
            Optional<User> user = this.userRepository.findByUserIdAndUserState(UUID.fromString(userId), stateValueComponent.getUserLiveState());
            if (user.isEmpty()) throw new NotFoundException("alive user is not found by user id: " + userId);
            return user.get();
        } else {
            Optional<User> user = this.userRepository.findByUserId(UUID.fromString(userId));
            if (user.isEmpty()) throw new NotFoundException("user is not found by user id: " + userId);
            return user.get();
        }
    }

    public User userByUUIDUserId(UUID userId, boolean liveCheck) {
        if (liveCheck) {
            Optional<User> user = this.userRepository.findByUserIdAndUserState(userId, stateValueComponent.getUserLiveState());
            if (user.isEmpty()) throw new NotFoundException("alive user is not found by user id: " + userId.toString());
            return user.get();
        } else {
            Optional<User> user = this.userRepository.findByUserId(userId);
            if (user.isEmpty()) throw new NotFoundException("user is not found by user id: " + userId.toString());
            return user.get();
        }
    }

    public User userByUserAccount(String userAccount, boolean liveCheck) {
        if (liveCheck) {
            Optional<User> user = this.userRepository.findByUserAccount(userAccount);
            if (user.isEmpty()) throw new NotFoundException("alive user is not found bu user account :" + userAccount);
            return user.get();
        } else {
            Optional<User> user = this.userRepository.findByUserAccountAndUserState(userAccount, stateValueComponent.getUserLiveState());
            if (user.isEmpty()) throw new NotFoundException("user is not found by user account: " + userAccount);
            return user.get();
        }
    }

    public boolean overlapCheckUserAccount(String userAccount) {
        Optional<User> user = this.userRepository.findByUserAccount(userAccount);
        return user.isEmpty();
    }

    public boolean overlapCheckUserId(UUID userId) {
        Optional<User> user = this.userRepository.findByUserId(userId);
        return user.isEmpty();
    }

    public boolean checkRoleUserId(UUID userId, int role) {
        User user = this.userByUUIDUserId(userId, true);
        if (user.getUserRole() < role) throw new BadRequestException("user role is not match: " + user.getUserId().toString());
        return true;
    }

    public User signUp(User user) {
        logger.info("new user try sign in : " + user.getUserAccount());
        if (!user.getPassword().equals(user.getPasswordCheck())) throw new BadRequestException("password check is not match");
        if (!this.overlapCheckUserAccount(user.getUserAccount())) throw new BadRequestException("user account is overlap");
        if (!this.overlapCheckUserId(user.getUserId())) throw new BadRequestException("user id is overlap");
        User newUser = User.builder()
                .userAccount(user.getUserAccount())
                .userName(user.getUserName())
                .password(passwordEncoding.encode(user.getPassword()))
                .userState(stateValueComponent.getUserLiveState())
                .userRole(stateValueComponent.getUserRole())
                .createDate(new Date()).build();
        if (user.getUserEmail() != null && !user.getUserEmail().isBlank()) {
            newUser.setUserEmail(user.getUserEmail());
        }
        User saveUser = userRepository.save(newUser);
        authService.createAuth(saveUser.getUserId());
        logger.info("save user is success: " + saveUser.toString());
        return saveUser;
    }

    public User signOut(UUID userId) {
        User user = this.userByUUIDUserId(userId, true);
        user.setUserState(stateValueComponent.getUserDeleteState());
        user.setPassword("sign-out");
        user.setDeleteDate(new Date());
        logger.info("delete user is success: " + user.toString());
        this.authService.deleteAuth(userId);
        return this.userRepository.save(user);
    }
}

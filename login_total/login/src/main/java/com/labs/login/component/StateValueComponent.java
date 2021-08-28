package com.labs.login.component;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class StateValueComponent {
    private final int userLiveState = 1;
    private final int userDeleteState = 0;
    private final int authExpire = 0;
    private final int authActive = 1;
    private final int authLogOut = 2;
}

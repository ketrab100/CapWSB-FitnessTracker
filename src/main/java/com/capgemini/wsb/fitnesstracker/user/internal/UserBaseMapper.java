package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserBaseMapper {
    UserBaseDto toDto(User user) {
        return new UserBaseDto(user.getId(), user.getEmail());
    }
}

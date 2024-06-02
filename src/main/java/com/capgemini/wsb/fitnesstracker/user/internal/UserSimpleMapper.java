package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserSimpleMapper {

    UserSimpleDto toDto(User user) {
        return new UserSimpleDto(user.getFirstName(), user.getLastName());
    }
}

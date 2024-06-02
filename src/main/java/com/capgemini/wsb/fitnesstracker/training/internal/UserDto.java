package com.capgemini.wsb.fitnesstracker.training.internal;

import jakarta.annotation.Nullable;

record UserDto(@Nullable Long id, String firstName, String lastName,
               String email) {

}

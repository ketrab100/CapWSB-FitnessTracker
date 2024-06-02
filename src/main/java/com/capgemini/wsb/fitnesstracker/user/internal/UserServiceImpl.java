package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Creates new user
     * @param user
     * @return created user
     */

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Removes user
     * @param id
     */
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    /**
     * Gets user by id
     * @param userId id of the user to be searched
     * @return optional user
     */

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Gets users by email
     * @param email The email of the user to be searched
     * @return list of users
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Gets all users from DB
     * @return list of users
     */

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets users older than
     * @param time
     * @return list of users
     */
    @Override
    public List<User> findUsersOlderThan(LocalDate time) {
        return userRepository.findOlderThan(time);
    }

    /**
     * Gets users by email
     * @param email
     * @return list of users
     */
    @Override
    public List<User> findUsersByEmailLike(String email) {
        return userRepository.findByEmailLike(email);
    }

    /**
     * Updates user
     * @param id
     * @param updateUser
     * @return updated user
     */
    @Override
    public User updateUser(Long id, User updateUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (updateUser.getFirstName() != null) {
                user.setFirstName(updateUser.getFirstName());
            }
            if (updateUser.getLastName() != null) {
                user.setLastName(updateUser.getLastName());
            }
            if (updateUser.getEmail() != null) {
                user.setEmail(updateUser.getEmail());
            }
            if (updateUser.getBirthdate() != null) {
                user.setBirthdate(updateUser.getBirthdate());
            }
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException(id);
        }
    }
}
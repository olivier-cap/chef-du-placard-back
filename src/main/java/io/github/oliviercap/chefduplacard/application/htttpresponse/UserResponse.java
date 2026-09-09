package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.user.User;

public record UserResponse(
        Long id,
        String pseudonym,
        String email,
        boolean isAdmin
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUserId().id(),
                user.getPseudonym(),
                user.getEmail(),
                user.isAdmin()
        );
    }
}

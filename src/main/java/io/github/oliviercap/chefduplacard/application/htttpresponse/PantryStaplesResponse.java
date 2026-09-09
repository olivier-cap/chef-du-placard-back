package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaples;

import java.util.List;

public record PantryStaplesResponse(
        Long id,
        UserResponse userResponse,
        boolean is_default,
        List<PantryStaplesLineResponse> staplesLineResponseList
) {
    public static PantryStaplesResponse from(PantryStaples pantryStaples) {
        return new PantryStaplesResponse(
                pantryStaples.getId().id(),
                UserResponse.from(pantryStaples.getUser()),
                pantryStaples.isIs_default(),
                pantryStaples.getPantryStaplesLineMap().values().stream()
                        .map(PantryStaplesLineResponse::from)
                        .toList()
        );
    }
}

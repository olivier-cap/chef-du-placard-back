package io.github.oliviercap.chefduplacard.application.menu.savenewmenu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu.SaveNewMenuDTO;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import io.github.oliviercap.chefduplacard.application.menu.savenewmenu.port.ISaveNewMenuInputPort;
import io.github.oliviercap.chefduplacard.application.menu.savenewmenu.port.ISaveNewMenuOutputPort;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.util.List;
import java.util.Objects;

public class SaveNewMenuUseCase implements ISaveNewMenuInputPort {

    private final IMenuRepository menuRepository;
    private final ISaveNewMenuOutputPort outputPort;

    public SaveNewMenuUseCase(IMenuRepository menuRepository,
                              ISaveNewMenuOutputPort outputPort) {
        this.menuRepository = menuRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(SaveNewMenuRequestModel requestModel) {
        Objects.requireNonNull(requestModel, "requestModel must not be null");

        Long newMenuId = saveNewMenu(requestModel.lines(), requestModel.userId(), requestModel.menuName());
        outputPort.saved(new SaveNewMenuResponseModel(newMenuId));
    }

    private Long saveNewMenu(
            List<SaveNewMenuRequestModel.MenuLine> newMenuRecord,
            Long userId,
            String menuName
    ) {
        Long newMenuId;

        Objects.requireNonNull(
                newMenuRecord,
                "menu must not be null"
        );

        if (userId == null) {
            throw new DomainException("userId must not be null");
        }

        if (menuName == null || menuName.isBlank()) {
            throw new DomainException("menu name must not be blank");
        }

        SaveNewMenuDTO menuDTO = new SaveNewMenuDTO(
                menuName,
                newMenuRecord.stream()
                        .map(ml -> new SaveNewMenuDTO.SaveNewMenuLine(
                                ml.recipeId(),
                                ml.nbPerson()
                        ))
                        .toList(),
                userId
        );

        try {
            newMenuId = menuRepository.save(menuDTO);
        } catch (Exception e) {
            throw new DomainException(
                    "save of menu didn't work",
                    e
            );
        }

        return newMenuId;
    }
}

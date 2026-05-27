package io.github.oliviercap.chefduplacard.adapters.web.getaliments;

import java.util.List;

public record GetAlimentsViewModel(
    List<AlimentViewModel> alimentViewModelList
) {
    public record AlimentViewModel(
            String alimentName,
            String alimentDescription,
            boolean isActive
    ){}
}

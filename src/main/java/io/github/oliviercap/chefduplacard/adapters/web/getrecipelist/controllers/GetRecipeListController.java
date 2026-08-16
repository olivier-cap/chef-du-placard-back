package io.github.oliviercap.chefduplacard.adapters.web.getrecipelist.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.getrecipelist.GetRecipeListViewModel;
import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListRequestModel;
import io.github.oliviercap.chefduplacard.application.getrecipelist.ports.IGetRecipeListInputPort;
import io.github.oliviercap.chefduplacard.application.getrecipelist.ports.IGetRecipeListOutPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetRecipeListController {

    private final IGetRecipeListInputPort inputPort;
    private final IGetRecipeListOutPort outPort;

    public GetRecipeListController(IGetRecipeListInputPort inputPort,
                                   IGetRecipeListOutPort outPort) {
        this.inputPort = inputPort;
        this.outPort = outPort;
    }

    @GetMapping("/api/getRecipeList")
    GetRecipeListViewModel getRecipeList() {
        inputPort.execute(new GetRecipeListRequestModel());

        return outPort.getViewModel();
    }
}

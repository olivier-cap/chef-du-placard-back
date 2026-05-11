package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesRequestModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesResponseModel;


public interface IFindCookableRecipesInputPort {
    public FindCookableRecipesResponseModel execute(FindCookableRecipesRequestModel findCookableRecipesRequestModel);
}

package io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.controllers;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public record CookableMenusRequest(int nbPeople, int nbMeal, String stockName) {
}

package io.github.oliviercap.chefduplacard.domain.food;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AlimentTest {

    @Test
    void aliment_can_be_created_and_name_modified() {
        //GIVEN
        Aliment aliment = new Aliment("pomme", "fruit", true);

        //WHEN
        aliment.setName("pomme reinette");

        //THEN
        assertThat(aliment).isNotNull();
        assertThat(aliment.getName()).isEqualTo("pomme reinette");
    }
}

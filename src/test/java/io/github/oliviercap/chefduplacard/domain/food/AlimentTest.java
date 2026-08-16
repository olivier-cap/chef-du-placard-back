package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class AlimentTest {

    @Test
    void should_compute_same_identifier_for_same_name_and_description() {
        Aliment aliment1 = new Aliment(new AlimentId(Long.valueOf(1)), "apple", "fruit", true);
        Aliment aliment2 = new Aliment(new AlimentId(Long.valueOf(1)), "apple", "fruit", false);

        assertThat(aliment1.getIdentifier())
                .isEqualTo(aliment2.getIdentifier());
    }

    @Test
    void should_compute_same_identifier_with_normalized_values() {
        Aliment aliment1 = new Aliment(new AlimentId(Long.valueOf(1)), " Apple ", " Fruit ", true);
        Aliment aliment2 = new Aliment(new AlimentId(Long.valueOf(1)), "apple", "fruit", true);

        assertThat(aliment1.getIdentifier())
                .isEqualTo(aliment2.getIdentifier());
    }

    @Test
    void should_compute_different_identifier_for_different_description() {
        Aliment aliment1 = new Aliment(new AlimentId(Long.valueOf(1)), "apple", "fruit", true);
        Aliment aliment2 = new Aliment(new AlimentId(Long.valueOf(1)), "apple", "red fruit", true);

        assertThat(aliment1.getIdentifier())
                .isNotEqualTo(aliment2.getIdentifier());
    }

    @Test
    void should_reject_blank_name() {
        assertThatThrownBy(() -> new Aliment(new AlimentId(Long.valueOf(1))," ", "fruit", true))
                .isInstanceOf(DomainException.class);
    }


}

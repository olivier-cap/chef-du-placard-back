package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.application.getstock.GetStockUseCase;
import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.query.IGetStockViewQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetStockRoot {

    @Bean
    public GetStockUseCase GetStockRoot(
            IGetStockViewQuery getStockViewQuery,
            IGetStockOutputPort stockOutputPort
    ) {
        return new GetStockUseCase(
                getStockViewQuery,
                stockOutputPort
        );
    }
}

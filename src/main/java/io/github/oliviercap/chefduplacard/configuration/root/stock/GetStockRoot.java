package io.github.oliviercap.chefduplacard.configuration.root.stock;

import io.github.oliviercap.chefduplacard.application.stock.getstock.GetStockUseCase;
import io.github.oliviercap.chefduplacard.application.stock.getstock.ports.IGetStockOutputPort;
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

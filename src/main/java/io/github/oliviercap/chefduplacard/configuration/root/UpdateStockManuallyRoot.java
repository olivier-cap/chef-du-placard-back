package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.AlimentRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.StockRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.UnitRepository;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.UpdateStockManuallyUseCase;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.port.IUpdateStockManuallyOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateStockManuallyRoot {

    @Bean
    public UpdateStockManuallyUseCase UpdateStockManuallyRoot(StockRepository stockRepository,
                                                              AlimentRepository alimentRepository,
                                                              UnitRepository unitRepository,
                                                              IUpdateStockManuallyOutputPort iUpdateStockManuallyOutputPort)
    {
        return new UpdateStockManuallyUseCase(
                stockRepository,
                alimentRepository,
                unitRepository,
                iUpdateStockManuallyOutputPort
        );
    }
}

package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.query.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.application.getstock.GetStockQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IGetStockJpaQuery extends JpaRepository<StockJpa, Long> {

    @Query("""
            select new io.github.oliviercap.chefduplacard.application.getstock.GetStockQuery(
                        sl.quantity,
                        a.name,
                        u.symbol
                        )
            from StockJpa s
                left join s.stockLineJpa sl
                    left join sl.alimentJpa a
                    left join sl.unitJpa u
            where s.name = :stockName
            """)
    List<GetStockQuery> getMenuView(@Param("stockName") String stockName);
}

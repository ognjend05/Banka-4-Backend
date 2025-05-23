package rs.banka4.stock_service.repositories;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.banka4.stock_service.domain.options.db.Option;


@Repository
public interface OptionsRepository extends JpaRepository<Option, UUID> {
    @Query(
        value = "select o from options o where o.active and o.stock.id = :stockId and o.settlementDate between :startDate and :endDate"
    )
    List<Option> findAllOptionsForStockWithSettlementDate(
        UUID stockId,
        OffsetDateTime startDate,
        OffsetDateTime endDate
    );

    @Modifying
    @Transactional
    @Query(
        value = """
            DELETE FROM options o
            WHERE NOT EXISTS (
                SELECT 1 FROM orders ord WHERE ord.asset_id = o.id
            )
            AND NOT EXISTS (
                SELECT 1 FROM asset_ownership ao WHERE ao.id_asset_id = o.id
            )
            """,
        nativeQuery = true
    )
    void deleteOptionsWithoutOrders();

    @Modifying
    @Transactional
    @Query(
        value = "UPDATE options SET active = false",
        nativeQuery = true
    )
    void deactivateAll();
}

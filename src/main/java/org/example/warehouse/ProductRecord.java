package org.example.warehouse;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Angela Gustafsson, anggus-1
 */
public record ProductRecord(UUID uuid, BigDecimal price, Category category, String name, boolean changed) {
    public ProductRecord withUpdatedPrice(BigDecimal updatedPrice) {
        return new ProductRecord(uuid, updatedPrice, category, name, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRecord that = (ProductRecord) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

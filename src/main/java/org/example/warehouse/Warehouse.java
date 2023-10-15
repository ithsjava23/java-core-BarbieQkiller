package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Angela Gustafsson, anggus-1
 */
public class Warehouse {
    String name;
    List<ProductRecord> products = new ArrayList<>();

    private Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance() {
        return new Warehouse(null);
    }

    public static Warehouse getInstance(String myStore) {
        return new Warehouse(myStore);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public ProductRecord addProduct(UUID uuid, String productName, Category productCategory, BigDecimal price) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (productCategory == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (getProductById(uuid).isPresent()) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        if (price == null) {
            price = BigDecimal.ZERO;
        }
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        ProductRecord productRecord = new ProductRecord(uuid, price, productCategory, productName, false);
        products.add(productRecord);
        return productRecord;
    }

    public Optional<ProductRecord> getProductById(UUID uuid) {

        return products.stream()
                .filter((ProductRecord product) -> {
                    return product.uuid().equals(uuid);
                })
                .findAny();
    }

    public void updateProductPrice(UUID uuid, BigDecimal bigDecimal) {
        ProductRecord productRecord = getProductById(uuid).orElseThrow(() -> new IllegalArgumentException("Product with that id doesn't exist."));
        products.remove(productRecord);
        products.add(productRecord.withUpdatedPrice(bigDecimal));
    }

    public List<ProductRecord> getChangedProducts() {
        return products.stream()
                .filter((ProductRecord product) -> product.changed()).toList();
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.stream().collect(Collectors.groupingBy(ProductRecord::category, Collectors.toList()));
    }

    public List<ProductRecord> getProductsBy(Category meat) {
        return products.stream()
                .filter((ProductRecord product) -> {
                    return product.category().equals(meat);
                })
                .toList();

    }


}

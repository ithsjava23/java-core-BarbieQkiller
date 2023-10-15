package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Angela Gustafsson, anggus-1
 */
public class Category {
    String name;
    static Map <String, Category> categoryByName = new HashMap<>();
    private Category(String name){
    this.name = name;
    }
    public static Category of(String name) {
        if(name == null){
            throw new IllegalArgumentException("Category name can't be null");
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        categoryByName.putIfAbsent(name, new Category(name));
        return categoryByName.get(name);
    }

    public String getName() {
        return name;
    }
}

package com.archervanderwaal.litespring.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/11
 */
public class ConstructorArgument {

    private final List<ValueHolder> argumentValues = new ArrayList<>();

    public ConstructorArgument() {

    }

    public ConstructorArgument(Object value, String type) {
        this.argumentValues.add(new ValueHolder(value, type));
    }

    public ConstructorArgument(ValueHolder valueHolder) {
        this.argumentValues.add(valueHolder);
    }

    public List<ConstructorArgument.ValueHolder> getArgumentValues() {
        return Collections.unmodifiableList(this.argumentValues);
    }

    public int getArgumentCount() {
        return this.argumentValues.size();
    }

    public boolean isEmpty() {
        return this.argumentValues.isEmpty();
    }

    public void clear() {
        this.argumentValues.clear();
    }

    public void addArgumentValue(ValueHolder valueHolder) {
        this.argumentValues.add(valueHolder);
    }

    public void addArgumentValue(Object value) {
        this.argumentValues.add(new ValueHolder(value));
    }

    public static class ValueHolder {

        private Object value;

        private String type;

        private String name;

        public ValueHolder(Object value) {
            this.value = value;
        }

        public ValueHolder(Object value, String type) {
            this(value);
            this.type = type;
        }

        public ValueHolder(Object value, String type, String name) {
            this(value, type);
            this.name = name;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return this.value;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}

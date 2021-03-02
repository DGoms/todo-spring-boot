package com.davidgomes.todospringboot.utils.api_converter;

import com.davidgomes.todospringboot.utils.PersistableEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@SuppressWarnings("ALL")
@Component
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter(targetType);
    }

    private static class StringToEnumConverter<T extends Enum<T>> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        public T convert(String source) {
            if (PersistableEnum.class.isAssignableFrom(enumType)) {
                for (T e : enumType.getEnumConstants()) {
                    if (((PersistableEnum) e).getValue().toString().equals(source)) return e;
                }
            } else {
                try {
                    return (T) Enum.valueOf(this.enumType, source.trim());
                } catch (IllegalArgumentException e2) {
                    // ignore
                }
            }

            return null;


//            try {
//                Method getValue = this.enumType.getMethod("getValue");
//                for (T e : enumType.getEnumConstants()) {
//                    if (getValue.invoke(e).equals(Integer.parseInt(source))) return e;
//                }
//
//            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                try {
//                    return (T) Enum.valueOf(this.enumType, source.trim());
//                } catch (IllegalArgumentException e2) {
//                    // ignore
//                }
//            }
//
//            return null;
        }
    }
}
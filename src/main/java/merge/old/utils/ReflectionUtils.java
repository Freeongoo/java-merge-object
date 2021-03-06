package merge.old.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Tests: @see <a href="https://github.com/Freeongoo/reflection-utils">reflection-utils</a>
 */
public class ReflectionUtils {

    private static final String GETTER_PREFIX = "get";
    private static final String SETTER_PREFIX = "set";

    public static String getterByFieldName(String fieldName) {
        if (isStringNullOrEmpty(fieldName))
            return null;

        return convertFieldByAddingPrefix(fieldName, GETTER_PREFIX);
    }

    public static String setterByFieldName(String fieldName) {
        if (isStringNullOrEmpty(fieldName))
            return null;

        return convertFieldByAddingPrefix(fieldName, SETTER_PREFIX);
    }

    private static String convertFieldByAddingPrefix(String fieldName, String prefix) {
        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static Object getFieldContent(Object obj, String fieldName) {
        if (!isValidParams(obj, fieldName))
            return null;

        try {
            Field declaredField = getFieldAccessible(obj, fieldName);
            return declaredField.get(obj);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot get field content for field name: " + fieldName, e);
        }
    }

    public static void setFieldContent(Object obj, String fieldName, Object value) {
        if (!isValidParams(obj, fieldName))
            return;

        try {
            Field declaredField = getFieldAccessible(obj, fieldName);
            declaredField.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot set field content for field name: " + fieldName, e);
        }
    }

    private static Field getFieldAccessible(Object obj, String fieldName) {
        Optional<Field> optionalField = getField(obj, fieldName);
        return optionalField
                .map(el -> {
                    el.setAccessible(true);
                    return el;
                })
                .orElseThrow(() -> new IllegalArgumentException("Cannot find field name: " + fieldName));
    }

    public static Object callMethod(Object obj, String methodName) {
        if (!isValidParams(obj, methodName))
            return null;

        try {
            Method method = obj.getClass().getMethod(methodName);
            method.setAccessible(true);
            return method.invoke(obj);
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalArgumentException("Cannot invoke method name: " + methodName, e);
        }
    }

    public static Field[] getAllFields(Class<?> clazz) {
        if (clazz == null) return null;

        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            // danger! Recursion
            fields.addAll(Arrays.asList(getAllFields(clazz.getSuperclass())));
        }
        return fields.toArray(new Field[] {});
    }

    public static Optional<Field> getField(Object obj, String fieldName) {
        if (!isValidParams(obj, fieldName))
            return Optional.empty();

        Class<?> clazz = obj.getClass();
        return getField(clazz, fieldName);
    }

    public static Optional<Field> getField(Class<?> clazz, String fieldName) {
        if (!isValidParams(clazz, fieldName))
            return Optional.empty();

        Field[] fields = getAllFields(clazz);
        return Stream.of(fields)
                .filter(x -> x.getName().equals(fieldName))
                .findFirst();
    }

    private static boolean isValidParams(Object obj, String param) {
        return (obj != null && !isStringNullOrEmpty(param));
    }

    private static boolean isStringNullOrEmpty(String fieldName) {
        return fieldName == null || fieldName.trim().length() == 0;
    }
}

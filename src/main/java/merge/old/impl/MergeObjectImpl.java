package merge.old.impl;

import merge.old.MergeObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static merge.old.utils.ReflectionUtils.*;

public class MergeObjectImpl implements MergeObject {

    private Object objectTo;
    private Object objectFrom;

    @Override
    public <T> void sumNumberFields(T objectTo, T objectFrom, Set<String> fieldNames) {
        if (objectTo == null || objectFrom == null) return;

        setObjects(objectTo, objectFrom);
        validateObjectType();

        for (String fieldName: fieldNames) {
            sumNumberField(fieldName);
        }
    }

    @Override
    public <T> void sumNumberFields(T objectTo, T objectFrom) {
        Set<String> fields = getNotFinalAnStaticFieldNames(objectTo);
        sumNumberFields(objectTo, objectFrom, fields);
    }

    private void validateObjectType() {
        if (!objectFrom.getClass().equals(objectTo.getClass()))
            throw new InvalidParameterException("Transferred to different types of objects. Please use objects of the same class.");
    }

    private Set<String> getNotFinalAnStaticFieldNames(Object objectTo) {
        Field[] allFields = getAllFields(objectTo.getClass());
        return Arrays.stream(allFields)
                .filter(getNotStaticAndFinalPredicate())
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    private Predicate<Field> getNotStaticAndFinalPredicate() {
        return el -> !Modifier.isFinal(el.getModifiers()) && !Modifier.isStatic(el.getModifiers());
    }

    private void setObjects(Object objectTo, Object objectFrom) {
        this.objectTo = objectTo;
        this.objectFrom = objectFrom;
    }

    private void sumNumberField(String fieldName) {
        if (isFieldFinal(fieldName)) return;

        Object fieldContentTo = getFieldContent(objectTo, fieldName);
        Object fieldContentFrom = getFieldContent(objectFrom, fieldName);

        if (fieldContentFrom == null)
            return;

        if (isFieldToIsNullAndFieldFromIsNumber(fieldContentTo, fieldContentFrom)) {
            setFieldContent(objectTo, fieldName, fieldContentFrom);
            return;
        }

        sumNumberFieldDependsOfType(fieldName, fieldContentTo, fieldContentFrom);
    }

    private boolean isFieldFinal(String fieldName) {
        Optional<Field> field = getField(objectFrom, fieldName);
        return field.map(f -> Modifier.isFinal(f.getModifiers()))
                .orElse(false);
    }

    private boolean isFieldToIsNullAndFieldFromIsNumber(Object fieldContentTo, Object fieldContentFrom) {
        return fieldContentTo == null && fieldContentFrom instanceof Number;
    }

    private void sumNumberFieldDependsOfType(String fieldName, Object fieldContentTo, Object fieldContentFrom) {
        if (fieldContentTo instanceof Integer) {
            setFieldContent(objectTo, fieldName, (int) fieldContentTo + (int) fieldContentFrom);
        }
        else if (fieldContentTo instanceof Long) {
            setFieldContent(objectTo, fieldName, (long) fieldContentTo + (long) fieldContentFrom);
        }
        else if (fieldContentTo instanceof Double) {
            setFieldContent(objectTo, fieldName, (double) fieldContentTo + (double) fieldContentFrom);
        }
        else if (fieldContentTo instanceof Float) {
            setFieldContent(objectTo, fieldName, (float) fieldContentTo + (float) fieldContentFrom);
        }
    }
}

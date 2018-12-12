package merge.old.impl;

import merge.old.MergeObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static merge.old.utils.ReflectionUtils.*;

public class MergeObjectImpl implements MergeObject {

    private Object objectTo;
    private Object objectFrom;

    public void sumNumberFields(Object objectTo, Object objectFrom, Set<String> fieldNames) {
        setObjects(objectTo, objectFrom);

        for (String fieldName: fieldNames) {
            sumNumberField(fieldName);
        }
    }

    @Override
    public void sumNumberFields(Object objectTo, Object objectFrom) {
        Set<String> allFieldNames = getNotFinalFieldNames(objectTo);
        sumNumberFields(objectTo, objectFrom, allFieldNames);
    }

    private Set<String> getNotFinalFieldNames(Object objectTo) {
        Field[] allFields = getAllFields(objectTo.getClass());
        return Arrays.stream(allFields)
                .filter(el -> !Modifier.isFinal(el.getModifiers()))
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    private void setObjects(Object objectTo, Object objectFrom) {
        this.objectTo = objectTo;
        this.objectFrom = objectFrom;
    }

    private void sumNumberField(String fieldName) {
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

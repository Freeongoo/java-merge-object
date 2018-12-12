package merge.old.impl;

import merge.old.MergeObject;

import java.util.Set;

import static merge.old.utils.ReflectionUtils.getFieldContent;
import static merge.old.utils.ReflectionUtils.setFieldContent;

public class MergeObjectImpl implements MergeObject {

    private Object objectTo;
    private Object objectFrom;

    public void sumNumberFields(Object objectTo, Object objectFrom, Set<String> fields) {
        setObjects(objectTo, objectFrom);

        for (String field: fields) {
            sumNumberField(field);
        }
    }

    private void setObjects(Object objectTo, Object objectFrom) {
        this.objectTo = objectTo;
        this.objectFrom = objectFrom;
    }

    private void sumNumberField(String field) {
        Object fieldContentTo = getFieldContent(objectTo, field);
        Object fieldContentFrom = getFieldContent(objectFrom, field);

        if (fieldContentFrom == null)
            return;

        if (isFieldToIsNullAndFieldFromIsNumber(fieldContentTo, fieldContentFrom)) {
            setFieldContent(objectTo, field, fieldContentFrom);
            return;
        }

        sumNumberFieldDependsOfType(field, fieldContentTo, fieldContentFrom);
    }

    private boolean isFieldToIsNullAndFieldFromIsNumber(Object fieldContentTo, Object fieldContentFrom) {
        return fieldContentTo == null && fieldContentFrom instanceof Number;
    }

    private void sumNumberFieldDependsOfType(String field, Object fieldContentTo, Object fieldContentFrom) {
        if (fieldContentTo instanceof Integer) {
            setFieldContent(objectTo, field, (int) fieldContentTo + (int) fieldContentFrom);
        }
        else if (fieldContentTo instanceof Long) {
            setFieldContent(objectTo, field, (long) fieldContentTo + (long) fieldContentFrom);
        }
        else if (fieldContentTo instanceof Double) {
            setFieldContent(objectTo, field, (double) fieldContentTo + (double) fieldContentFrom);
        }
        else if (fieldContentTo instanceof Float) {
            setFieldContent(objectTo, field, (float) fieldContentTo + (float) fieldContentFrom);
        }
    }
}

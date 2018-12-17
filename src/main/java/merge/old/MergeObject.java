package merge.old;

import java.util.Set;

public interface MergeObject {

    /**
     * This function summarizes only the transferred list of numeric fields
     * and modifies the first transferred object.
     *
     * If the field is not numeric, it is ignored. No action taken
     *
     * Numeric final and static fields are ignored.
     *
     * @param objectTo object where added fields will be summarize
     * @param objectFrom the object that will be used to get the field values
     * @param fieldNames set of field names to perform arithmetic addition
     */
    void sumNumberFields(Object objectTo, Object objectFrom, Set<String> fieldNames);

    /**
     * This function summarizes all only numeric fields (without final and static)
     * and modifies the first transferred object.
     *
     * If the field is not numeric, it is ignored. No action taken
     *
     * @param objectTo object where added fields will be summarize
     * @param objectFrom the object that will be used to get the field values
     */
    void sumNumberFields(Object objectTo, Object objectFrom);
}

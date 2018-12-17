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
     * @param objectTo Object
     * @param objectFrom Object
     * @param fieldNames Set<String>
     */
    void sumNumberFields(Object objectTo, Object objectFrom, Set<String> fieldNames);

    /**
     * This function summarizes all only numeric fields (without final and static)
     * and modifies the first transferred object.
     *
     * If the field is not numeric, it is ignored. No action taken
     *
     * @param objectTo Object
     * @param objectFrom Object
     */
    void sumNumberFields(Object objectTo, Object objectFrom);
}

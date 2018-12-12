package merge.old.impl;

import data.HolderInfo;
import merge.old.MergeObject;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MergeObjectImplTest {

    private MergeObject mergeObject;
    private HolderInfo holderInfoTo;
    private HolderInfo holderInfoFrom;

    @Before
    public void setUp() {
        mergeObject = new MergeObjectImpl();
        holderInfoTo = new HolderInfo();
        holderInfoFrom = new HolderInfo();
    }

    @Test
    public void sumNumberFields_WhenEmptyFields() {
        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, getAllNumberFields());

        assertThat(holderInfoTo, equalTo(new HolderInfo()));
    }

    @Test
    public void sumNumberFields_WhenOnlyFromValues() {
        holderInfoFrom.setCount(156732453L);
        holderInfoFrom.setDiameter(123.);
        holderInfoFrom.setOverload(123F);
        holderInfoFrom.setSize(55);

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, getAllNumberFields());

        assertThat(holderInfoTo, equalTo(holderInfoFrom));
    }

    @Test
    public void sumNumberFields_WhenOnlyToValues() {
        holderInfoTo.setCount(156732453L);
        holderInfoTo.setDiameter(123.);
        holderInfoTo.setOverload(123F);
        holderInfoTo.setSize(55);

        HolderInfo holderInfoClone = SerializationUtils.clone(holderInfoTo);

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, getAllNumberFields());

        assertThat(holderInfoTo, equalTo(holderInfoClone));
    }

    @Test
    public void sumNumberFields_WhenAllNumberFieldsSummery() {
        holderInfoTo.setCount(3L);
        holderInfoTo.setDiameter(1.);
        holderInfoTo.setOverload(3F);
        holderInfoTo.setSize(5);
        holderInfoFrom.setCount(0L);
        holderInfoFrom.setDiameter(2.);
        holderInfoFrom.setOverload(-6.4F);
        holderInfoFrom.setSize(3);

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, getAllNumberFields());

        // expected
        HolderInfo expectedHolderInfo = new HolderInfo();
        expectedHolderInfo.setCount(3L);
        expectedHolderInfo.setDiameter(3.);
        expectedHolderInfo.setOverload(-3.4F);
        expectedHolderInfo.setSize(8);

        assertThat(expectedHolderInfo, equalTo(holderInfoTo));
    }

    @Test
    public void sumNumberFields_WhenNotAllNumberFieldsSummery() {
        holderInfoTo.setCount(3L);
        holderInfoTo.setDiameter(1.);
        holderInfoTo.setOverload(3F);
        holderInfoTo.setSize(5);
        holderInfoFrom.setCount(2L);
        holderInfoFrom.setDiameter(2.);
        holderInfoFrom.setOverload(-6.4F);
        holderInfoFrom.setSize(3);

        HashSet<String> fields = new HashSet<>();
        fields.add("count");
        fields.add("diameter");

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, fields);

        // expected
        HolderInfo expectedHolderInfo = new HolderInfo();
        expectedHolderInfo.setCount(5L);
        expectedHolderInfo.setDiameter(3.);
        expectedHolderInfo.setOverload(3F);
        expectedHolderInfo.setSize(5);

        assertThat(expectedHolderInfo, equalTo(holderInfoTo));
    }

    @Test
    public void sumNumberFields_WhenTrySummeryNotNumberField() {
        holderInfoTo.setName("nameValue1");
        holderInfoFrom.setName("nameValue2");

        HashSet<String> fields = new HashSet<>();
        fields.add("name");

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, fields);

        // expected
        HolderInfo expectedHolderInfo = new HolderInfo();
        expectedHolderInfo.setName("nameValue1");

        assertThat(expectedHolderInfo, equalTo(holderInfoTo));
    }

    @Test
    public void sumNumberFields_WhenTrySummeryNotNumberField_WhenToNotSet() {
        holderInfoFrom.setName("nameValue2");

        HashSet<String> fields = new HashSet<>();
        fields.add("name");

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, fields);

        // expected
        HolderInfo expectedHolderInfo = new HolderInfo();

        assertThat(expectedHolderInfo, equalTo(holderInfoTo));
    }

    @Test
    public void sumNumberFields_WhenExistNull() {
        holderInfoTo.setCount(null);
        holderInfoTo.setDiameter(1.);
        holderInfoTo.setOverload(3F);
        holderInfoTo.setSize(5);
        holderInfoFrom.setCount(0L);
        holderInfoFrom.setDiameter(null);
        holderInfoFrom.setOverload(-6.4F);
        holderInfoFrom.setSize(3);

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, getAllNumberFields());

        // expected
        HolderInfo expectedHolderInfo = new HolderInfo();
        expectedHolderInfo.setCount(0L);
        expectedHolderInfo.setDiameter(1.);
        expectedHolderInfo.setOverload(-3.4F);
        expectedHolderInfo.setSize(8);

        assertThat(expectedHolderInfo, equalTo(holderInfoTo));
    }

    @Test(expected = RuntimeException.class)
    public void sumNumberFields_WhenPassNotExistField() {
        holderInfoTo.setCount(null);
        holderInfoTo.setDiameter(1.);
        holderInfoTo.setOverload(3F);
        holderInfoTo.setSize(5);
        holderInfoFrom.setCount(0L);
        holderInfoFrom.setDiameter(null);
        holderInfoFrom.setOverload(-6.4F);
        holderInfoFrom.setSize(3);

        HashSet<String> fields = new HashSet<>();
        fields.add("notExistFieldName");

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, fields);
    }

    private Set<String> getAllNumberFields() {
        Set<String> fields = new HashSet<>();

        fields.add("count");
        fields.add("size");
        fields.add("diameter");
        fields.add("overload");

        return fields;
    }
}
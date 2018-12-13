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

    private Set<String> fields;

    @Before
    public void setUp() {
        fields = new HashSet<>();

        mergeObject = new MergeObjectImpl();
        holderInfoTo = new HolderInfo();
        holderInfoFrom = new HolderInfo();
    }

    @Test
    public void sumNumberFields_WhenPassedNulls() {
        mergeObject.sumNumberFields(null, null, null);
    }

    @Test
    public void sumNumberFields_WhenObjectFromIsNull() {
        setNumericFieldsSomeData(holderInfoTo);

        HolderInfo holderInfoClone = SerializationUtils.clone(holderInfoTo);

        mergeObject.sumNumberFields(holderInfoTo, null, getAllNumberFields());

        assertThat(holderInfoTo, equalTo(holderInfoClone));
    }

    @Test
    public void sumNumberFields_WhenObjectToIsNull() {
        setNumericFieldsSomeData(holderInfoFrom);

        HolderInfo holderInfoClone = SerializationUtils.clone(holderInfoFrom);

        mergeObject.sumNumberFields(null, holderInfoFrom, getAllNumberFields());

        assertThat(holderInfoFrom, equalTo(holderInfoClone));
    }

    @Test
    public void sumNumberFields_WhenEmptyObjects() {
        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, getAllNumberFields());

        assertThat(holderInfoTo, equalTo(new HolderInfo()));
    }

    @Test
    public void sumNumberFields_WhenObjectToIsEmpty() {
        setNumericFieldsSomeData(holderInfoFrom);

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, getAllNumberFields());

        assertThat(holderInfoTo, equalTo(holderInfoFrom));
    }

    @Test
    public void sumNumberFields_WhenObjectFromIsEmpty() {
        setNumericFieldsSomeData(holderInfoTo);

        HolderInfo holderInfoClone = SerializationUtils.clone(holderInfoTo);

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, getAllNumberFields());

        assertThat(holderInfoTo, equalTo(holderInfoClone));
    }

    @Test
    public void sumNumberFields_WhenAllNumberFieldsPassedInSet() {
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
    public void sumNumberFields_WhenAllFields() {
        holderInfoTo.setName("MyName1");
        holderInfoTo.setCount(3L);
        holderInfoTo.setDiameter(1.);
        holderInfoTo.setOverload(3F);
        holderInfoTo.setSize(5);
        holderInfoFrom.setName("MyName2");
        holderInfoFrom.setCount(0L);
        holderInfoFrom.setDiameter(2.);
        holderInfoFrom.setOverload(-6.4F);
        holderInfoFrom.setSize(3);

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom);

        // expected
        HolderInfo expectedHolderInfo = new HolderInfo();
        expectedHolderInfo.setName("MyName1");
        expectedHolderInfo.setCount(3L);
        expectedHolderInfo.setDiameter(3.);
        expectedHolderInfo.setOverload(-3.4F);
        expectedHolderInfo.setSize(8);

        assertThat(expectedHolderInfo, equalTo(holderInfoTo));
    }

    @Test
    public void sumNumberFields_WhenNotAllNumberFieldsPassedInSet() {
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
    public void sumNumberFields_WhenTrySumNotNumberField() {
        holderInfoTo.setName("nameValue1");
        holderInfoFrom.setName("nameValue2");

        fields.add("name");
        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, fields);

        // expected
        HolderInfo expectedHolderInfo = new HolderInfo();
        expectedHolderInfo.setName("nameValue1");

        assertThat(expectedHolderInfo, equalTo(holderInfoTo));
    }

    @Test
    public void sumNumberFields_WhenTrySumNotNumberField_WhenObjectToIsEmpty() {
        holderInfoFrom.setName("nameValue2");

        fields.add("name");
        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, fields);

        // expected
        HolderInfo expectedHolderInfo = new HolderInfo();

        assertThat(expectedHolderInfo, equalTo(holderInfoTo));
    }

    @Test
    public void sumNumberFields_WhenExistNullInFieldsContent() {
        holderInfoTo.setCount(null);
        holderInfoTo.setDiameter(1.);
        holderInfoTo.setOverload(3F);
        holderInfoTo.setSize(null);
        holderInfoFrom.setCount(0L);
        holderInfoFrom.setDiameter(null);
        holderInfoFrom.setOverload(-6.4F);
        holderInfoFrom.setSize(null);

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, getAllNumberFields());

        // expected
        HolderInfo expectedHolderInfo = new HolderInfo();
        expectedHolderInfo.setCount(0L);
        expectedHolderInfo.setDiameter(1.);
        expectedHolderInfo.setOverload(-3.4F);
        expectedHolderInfo.setSize(null);

        assertThat(expectedHolderInfo, equalTo(holderInfoTo));
    }

    @Test(expected = RuntimeException.class)
    public void sumNumberFields_WhenPassedNotExistingFieldName() {
        holderInfoTo.setCount(null);
        holderInfoTo.setDiameter(1.);
        holderInfoTo.setOverload(3F);
        holderInfoTo.setSize(5);
        holderInfoFrom.setCount(0L);
        holderInfoFrom.setDiameter(null);
        holderInfoFrom.setOverload(-6.4F);
        holderInfoFrom.setSize(3);

        fields.add("notExistFieldName");

        mergeObject.sumNumberFields(holderInfoTo, holderInfoFrom, fields);
    }

    private Set<String> getAllNumberFields() {
        fields.add("count");
        fields.add("size");
        fields.add("diameter");
        fields.add("overload");

        return fields;
    }

    private void setNumericFieldsSomeData(HolderInfo holderInfo) {
        holderInfo.setCount(156732453L);
        holderInfo.setDiameter(123.);
        holderInfo.setOverload(123F);
        holderInfo.setSize(55);
    }
}
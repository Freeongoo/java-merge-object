package data;

import java.io.Serializable;
import java.util.Objects;

public class HolderInfo implements Serializable {

    private static final long serialVersionUID = -684665112848132620L;

    private String name;
    private Long count;
    private Integer size;
    private Double diameter;
    private Float overload;
    private Short smallNum;

    private static String staticStr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Double getDiameter() {
        return diameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    public Float getOverload() {
        return overload;
    }

    public void setOverload(Float overload) {
        this.overload = overload;
    }

    public Short getSmallNum() {
        return smallNum;
    }

    public void setSmallNum(Short smallNum) {
        this.smallNum = smallNum;
    }

    public static String getStaticStr() {
        return staticStr;
    }

    public static void setStaticStr(String staticStr) {
        HolderInfo.staticStr = staticStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HolderInfo that = (HolderInfo) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (diameter != null ? !diameter.equals(that.diameter) : that.diameter != null) return false;
        if (overload != null ? !overload.equals(that.overload) : that.overload != null) return false;
        return smallNum != null ? smallNum.equals(that.smallNum) : that.smallNum == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (diameter != null ? diameter.hashCode() : 0);
        result = 31 * result + (overload != null ? overload.hashCode() : 0);
        result = 31 * result + (smallNum != null ? smallNum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HolderInfo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", count=").append(count);
        sb.append(", size=").append(size);
        sb.append(", diameter=").append(diameter);
        sb.append(", overload=").append(overload);
        sb.append(", smallNum=").append(smallNum);
        sb.append('}');
        return sb.toString();
    }
}

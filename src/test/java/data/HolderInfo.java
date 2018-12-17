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
        return Objects.equals(name, that.name) &&
                Objects.equals(count, that.count) &&
                Objects.equals(size, that.size) &&
                Objects.equals(diameter, that.diameter) &&
                Objects.equals(overload, that.overload);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, count, size, diameter, overload);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HolderInfo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", count=").append(count);
        sb.append(", size=").append(size);
        sb.append(", diameter=").append(diameter);
        sb.append(", overload=").append(overload);
        sb.append('}');
        return sb.toString();
    }
}

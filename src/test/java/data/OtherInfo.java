package data;

import java.util.Objects;

public class OtherInfo {
    private String someName;
    private Integer size;

    public String getSomeName() {
        return someName;
    }

    public void setSomeName(String someName) {
        this.someName = someName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OtherInfo otherInfo = (OtherInfo) o;
        return Objects.equals(someName, otherInfo.someName) &&
                Objects.equals(size, otherInfo.size);
    }

    @Override
    public int hashCode() {

        return Objects.hash(someName, size);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OtherInfo{");
        sb.append("someName='").append(someName).append('\'');
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}

package example;

public class Yeet {

    String yeet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Yeet yeet1 = (Yeet) o;

        return getYeet() != null ? getYeet().equals(yeet1.getYeet()) : yeet1.getYeet() == null;
    }

    @Override
    public int hashCode() {
        return getYeet() != null ? getYeet().hashCode() : 0;
    }

    public Yeet(String yeet) {
        this.yeet = yeet;
    }

    public String getYeet() {
        return yeet;
    }

    public void setYeet(String yeet) {
        this.yeet = yeet;
    }
}

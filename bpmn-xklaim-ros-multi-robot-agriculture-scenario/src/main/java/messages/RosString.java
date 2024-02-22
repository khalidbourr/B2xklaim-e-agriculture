package messages;

public class RosString {
    private String data;

    public RosString() {
        this.data = "";
    }

    public RosString(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}


package cena.mcs.nothi;

public class Data{
    String id;
    String title;
    String desc;

    public Data() {
    }

    public Data(String id,String title, String desc) {
        this.id=id;
        this.title = title;
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

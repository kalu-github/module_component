package lib.kalu.db.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public final class Http {

    @Id(assignable = true)
    private long id;
    @Index
    private String url = "";
    private String json = "";

    public Http() {
    }

    public Http(long id, String url, String json) {
        this.id = id;
        this.url = url;
        this.json = json;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}

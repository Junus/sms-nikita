package kg.taptap.android.backend.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "response")
public class NikitaDTO {
    @Element(name = "id")
    private String id;

    @Element(name = "status")
    private int status;

    @Element(name = "phones")
    private int phones;

    @Element(name = "smscnt")
    private int smscnt;

    @Element(name = "message", required = false)
    private String message;

    public NikitaDTO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPhones() {
        return phones;
    }

    public void setPhones(int phones) {
        this.phones = phones;
    }

    public int getSmscnt() {
        return smscnt;
    }

    public void setSmscnt(int smscnt) {
        this.smscnt = smscnt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package it.eg.sloth.framework.pageinfo;

import lombok.Data;

@Data
public class Accessibility {

    boolean create;
    boolean update;
    boolean read;
    boolean delete;

    public Accessibility() {
        this.create = true;
        this.update = true;
        this.read = true;
        this.delete = true;
    }
}

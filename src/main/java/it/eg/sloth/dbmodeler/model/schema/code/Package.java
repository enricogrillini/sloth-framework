package it.eg.sloth.dbmodeler.model.schema.code;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
public class Package {
    private String name;
    private String definition;
    private String bodyDefinition;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Method> methods;

    public Package() {
        this(null, null, null);
    }

    @Builder
    public Package(String name, String definition, String bodyDefinition) {
        this.name = name;
        this.definition = definition;
        this.bodyDefinition = bodyDefinition;

        methods = new ArrayList<>();
    }

    // Methods
    public Collection<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }


}
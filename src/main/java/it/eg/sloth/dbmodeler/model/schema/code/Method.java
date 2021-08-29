package it.eg.sloth.dbmodeler.model.schema.code;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class Method {

    String name;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Argument> arguments;

    public Method() {
        this(null);
    }

    @Builder
    public Method(String name) {
        this.name = name;

        arguments = new ArrayList<>();
    }

    // Arguments
    public Collection<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    public void addArgument(Argument argument) {
        arguments.add(argument);
    }

}

package it.eg.sloth.dbmodeler.model.schema.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.eg.sloth.framework.common.base.BaseFunction;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Singleton per la gestione delle Scedulazioni
 *
 * @author Enrico Grillini
 */
@Data
public class Method {

    String name;
    String overload;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Argument> arguments;

    public Method() {
        this(null, "0");
    }

    @Builder
    public Method(String name, String overload) {
        this.name = name;
        this.overload = overload;

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

    @JsonIgnore
    public String getUniqueName() {
        return getName() + BaseFunction.nvl(getOverload(), "");
    }

    @JsonIgnore
    public boolean isJavaPortable() {
        for (Argument argument : arguments) {
            if (argument.getType().contains("RECORD") || argument.getType().contains("TABLE") || argument.getInOut() == ArgumentType.INOUT || (argument.getInOut() == ArgumentType.OUT && argument.getPosition() > 0)) {
                return false;
            }
        }

        return true;
    }

}

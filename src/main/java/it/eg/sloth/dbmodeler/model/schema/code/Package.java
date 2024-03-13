package it.eg.sloth.dbmodeler.model.schema.code;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
public class Package {

    private String name;
    private String definition;
    private String bodyDefinition;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Function> functionMap;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Procedure> procedureMap;

    public Package() {
        this(null, null, null);
    }

    public Package(String name, String definition) {
        this(name, definition, null);
    }

    //@Builder
    public Package(String name, String definition, String bodyDefinition) {
        this.name = name;
        this.definition = definition;
        this.bodyDefinition = bodyDefinition;

        functionMap = new HashMap<>();
        procedureMap = new HashMap<>();
    }

    // Function
    public Collection<Function> getFunctionCollection() {
        return Collections.unmodifiableCollection(functionMap.values());
    }

    public void setFunctionCollection(Collection<Function> functionCollection) {
        for (Function dbObject : functionCollection) {
            addFunction(dbObject);
        }
    }

    public Package addFunction(Function function) {
        functionMap.put(function.getName().toLowerCase() + function.getOverload(), function);
        return this;
    }

    public Function getFunction(String methodName, String overload) {
        return functionMap.get(methodName.toLowerCase() + overload);
    }

    // Procedure
    public Collection<Procedure> getProcedureCollection() {
        return Collections.unmodifiableCollection(procedureMap.values());
    }

    public void setProcedureCollection(Collection<Procedure> procedureCollection) {
        for (Procedure dbObject : procedureCollection) {
            addProcedure(dbObject);
        }
    }

    public Package addProcedure(Procedure procedure) {
        procedureMap.put(procedure.getName().toLowerCase() + procedure.getOverload(), procedure);
        return this;
    }

    public Procedure getProcedure(String methodName, String overload) {
        return procedureMap.get(methodName.toLowerCase() + overload);
    }


    public Package addMethod(StoredProcedureType type, String name, String overload) {
        switch (type) {
            case FUNCTION:
                addFunction(new Function(name, overload, null));
                break;
            case PROCEDURE:
                addProcedure(new Procedure(name, overload, null));
                break;
            default:
                // NOP
        }

        return this;
    }

    public Method getMethod(String name, String overload) {
        if (getFunction(name, overload) != null) {
            return getFunction(name, overload);
        } else if (getProcedure(name, overload) != null) {
            return getProcedure(name, overload);
        } else {
            return null;
        }

    }
}
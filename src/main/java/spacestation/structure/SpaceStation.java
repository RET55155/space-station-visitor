package spacestation.structure;

import spacestation.element.StationModule;
import spacestation.visitor.ModuleVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpaceStation {
    private final String name;
    private final List<StationModule> modules = new ArrayList<>();

    public SpaceStation(String name) {
        this.name = name;
    }

    public void addModule(StationModule module) {
        modules.add(module);
    }

    public List<StationModule> getModules() {
        return Collections.unmodifiableList(modules);
    }

    public String getName() {
        return name;
    }

    public void accept(ModuleVisitor visitor) {
        for (StationModule module : modules) {
            module.accept(visitor);
        }
    }
}

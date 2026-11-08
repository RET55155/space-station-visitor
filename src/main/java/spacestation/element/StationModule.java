package spacestation.element;
import spacestation.visitor.ModuleVisitor;

public interface StationModule {
    String getName();

    void accept(ModuleVisitor visitor);
}

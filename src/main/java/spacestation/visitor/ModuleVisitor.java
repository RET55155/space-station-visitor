package spacestation.visitor;

import spacestation.element.EngineModule;
import spacestation.element.LabModule;
import spacestation.element.LivingModule;
import spacestation.element.SolarPanelModule;

public interface ModuleVisitor {

    void visitLivingModule(LivingModule module);
    void visitLabModule(LabModule module);
    void visitEngineModule(EngineModule module);
    void visitSolarPanelModule(SolarPanelModule module);
}

package spacestation.visitor;

import spacestation.element.EngineModule;
import spacestation.element.LabModule;
import spacestation.element.LivingModule;
import spacestation.element.SolarPanelModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//concrete visitor 2
public class SafetyInspectionVisitor implements ModuleVisitor {

    private static final int DANGEROUS_HAZARD_LEVEL = 4;
    private static final double LOW_FUEL_RATIO = 0.25;
    private static final int PANEL_REPLACEMENT_AGE_YEARS = 15;

    private final List<String> issues = new ArrayList<>();

    @Override
    public void visitLivingModule(LivingModule module) {
        if (module.getCurrentCrew() > module.getDesignCapacity()) {
            issues.add(module.getName() + ": overcrowded ("
                    + module.getCurrentCrew() + "/" + module.getDesignCapacity() + ")");
        }
    }

    @Override
    public void visitLabModule(LabModule module) {
        if (module.getHazardLevel() >= DANGEROUS_HAZARD_LEVEL && module.getActiveExperiments() > 0) {
            issues.add(module.getName() + ": hazard level " + module.getHazardLevel()
                    + " with " + module.getActiveExperiments() + " running experiments");
        }
    }

    @Override
    public void visitEngineModule(EngineModule module) {
        double fuelRatio = module.getFuelTons() / module.getFuelCapacityTons();
        if (fuelRatio < LOW_FUEL_RATIO) {
            issues.add(module.getName() + ": low fuel ("
                    + Math.round(fuelRatio * 100) + "%)");
        }
    }

    @Override
    public void visitSolarPanelModule(SolarPanelModule module) {
        if (module.getAgeYears() >= PANEL_REPLACEMENT_AGE_YEARS) {
            issues.add(module.getName() + ": panels are " + module.getAgeYears()
                    + " years old, replacement needed");
        }
    }

    public List<String> getIssues() {
        return Collections.unmodifiableList(issues);
    }

    public boolean isStationSafe() {
        return issues.isEmpty();
    }
}

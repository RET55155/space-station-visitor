package spacestation.visitor;

import spacestation.element.EngineModule;
import spacestation.element.LabModule;
import spacestation.element.LivingModule;
import spacestation.element.SolarPanelModule;

public class MaintenanceCostVisitor implements ModuleVisitor {

    private static final double LIVING_BASE_COST = 120.0;
    private static final double LIVING_COST_PER_PERSON = 15.0;
    private static final double LAB_BASE_COST = 200.0;
    private static final double LAB_COST_PER_HAZARD_LEVEL = 40.0;
    private static final double ENGINE_COST_PER_KN = 8.0;
    private static final double SOLAR_COST_PER_M2 = 0.5;

    private double totalCostThousandUsd;

    @Override
    public void visitLivingModule(LivingModule module) {
        totalCostThousandUsd += LIVING_BASE_COST
                + module.getCurrentCrew() * LIVING_COST_PER_PERSON;
    }

    @Override
    public void visitLabModule(LabModule module) {
        totalCostThousandUsd += LAB_BASE_COST
                + module.getHazardLevel() * LAB_COST_PER_HAZARD_LEVEL;
    }

    @Override
    public void visitEngineModule(EngineModule module) {
        totalCostThousandUsd += module.getThrustKn() * ENGINE_COST_PER_KN;
    }

    @Override
    public void visitSolarPanelModule(SolarPanelModule module) {
        totalCostThousandUsd += module.getAreaSquareMeters() * SOLAR_COST_PER_M2;
    }

    public double getTotalCostThousandUsd() {
        return totalCostThousandUsd;
    }
}

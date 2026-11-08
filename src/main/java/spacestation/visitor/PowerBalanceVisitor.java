package spacestation.visitor;

import spacestation.element.EngineModule;
import spacestation.element.LabModule;
import spacestation.element.LivingModule;
import spacestation.element.SolarPanelModule;

//concrete visitor
public class PowerBalanceVisitor implements ModuleVisitor {

    private static final double KW_PER_CREW_MEMBER = 1.2;
    private static final double ENGINE_IDLE_KW = 5.0;
    private static final double SOLAR_CONSTANT_KW_PER_M2 = 1.36;
    private static final double YEARLY_PANEL_DEGRADATION = 0.005;

    private double consumedKw;
    private double producedKw;

    @Override
    public void visitLivingModule(LivingModule module) {
        consumedKw += module.getCurrentCrew() * KW_PER_CREW_MEMBER;
    }

    @Override
    public void visitLabModule(LabModule module) {
        consumedKw += module.getPowerDemandKw();
    }

    @Override
    public void visitEngineModule(EngineModule module) {
        consumedKw += ENGINE_IDLE_KW;
    }

    @Override
    public void visitSolarPanelModule(SolarPanelModule module) {
        double degradation = 1.0 - module.getAgeYears() * YEARLY_PANEL_DEGRADATION;
        producedKw += module.getAreaSquareMeters()
                * SOLAR_CONSTANT_KW_PER_M2
                * module.getEfficiency()
                * degradation;
    }

    public double getConsumedKw() {
        return consumedKw;
    }

    public double getProducedKw() {
        return producedKw;
    }

    public double getNetBalanceKw() {
        return producedKw - consumedKw;
    }
}

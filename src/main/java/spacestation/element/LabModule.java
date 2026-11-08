package spacestation.element;

import spacestation.visitor.ModuleVisitor;

//Concrete element
public class LabModule implements StationModule {

    private final String name;
    private final double powerDemandKw;
    private final int hazardLevel;
    private final int activeExperiments;

    public LabModule(String name, double powerDemandKw, int hazardLevel, int activeExperiments) {
        this.name = name;
        this.powerDemandKw = powerDemandKw;
        this.hazardLevel = hazardLevel;
        this.activeExperiments = activeExperiments;
    }

    @Override
    public String getName() {
        return name;
    }

    public double getPowerDemandKw() {
        return powerDemandKw;
    }

    public int getHazardLevel() {
        return hazardLevel;
    }

    public int getActiveExperiments() {
        return activeExperiments;
    }

    @Override
    public void accept(ModuleVisitor visitor) {
        visitor.visitLabModule(this);
    }
}

package spacestation.element;

import spacestation.visitor.ModuleVisitor;

public class SolarPanelModule implements StationModule {

    private final String name;
    private final double areaSquareMeters;
    private final double efficiency;
    private final int ageYears;

    public SolarPanelModule(String name, double areaSquareMeters, double efficiency, int ageYears) {
        this.name = name;
        this.areaSquareMeters = areaSquareMeters;
        this.efficiency = efficiency;
        this.ageYears = ageYears;
    }

    @Override
    public String getName() {
        return name;
    }

    public double getAreaSquareMeters() {
        return areaSquareMeters;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public int getAgeYears() {
        return ageYears;
    }

    @Override
    public void accept(ModuleVisitor visitor) {
        visitor.visitSolarPanelModule(this);
    }
}

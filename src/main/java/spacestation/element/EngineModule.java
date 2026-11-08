package spacestation.element;

import spacestation.visitor.ModuleVisitor;

//concrete element
public class EngineModule implements StationModule {

    private final String name;
    private final double thrustKn;
    private final double fuelTons;
    private final double fuelCapacityTons;

    public EngineModule(String name, double thrustKn, double fuelTons, double fuelCapacityTons) {
        this.name = name;
        this.thrustKn = thrustKn;
        this.fuelTons = fuelTons;
        this.fuelCapacityTons = fuelCapacityTons;
    }

    @Override
    public String getName() {
        return name;
    }

    public double getThrustKn() {
        return thrustKn;
    }

    public double getFuelTons() {
        return fuelTons;
    }

    public double getFuelCapacityTons() {
        return fuelCapacityTons;
    }

    @Override
    public void accept(ModuleVisitor visitor) {
        visitor.visitEngineModule(this);
    }
}

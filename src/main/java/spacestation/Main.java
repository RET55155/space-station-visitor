package spacestation;

import spacestation.element.EngineModule;
import spacestation.element.LabModule;
import spacestation.element.LivingModule;
import spacestation.element.SolarPanelModule;
import spacestation.structure.SpaceStation;
import spacestation.visitor.MaintenanceCostVisitor;
import spacestation.visitor.PowerBalanceVisitor;
import spacestation.visitor.SafetyInspectionVisitor;

public class Main {

    public static void main(String[] args) {
        SpaceStation station = buildStation();

        PowerBalanceVisitor power = new PowerBalanceVisitor();
        station.accept(power);
        System.out.println("=== Power balance ===");
        System.out.printf("Consumed: %.2f kW%n", power.getConsumedKw());
        System.out.printf("Produced: %.2f kW%n", power.getProducedKw());
        System.out.printf("Net:      %.2f kW%n%n", power.getNetBalanceKw());

        SafetyInspectionVisitor safety = new SafetyInspectionVisitor();
        station.accept(safety);
        System.out.println("/// Safety inspection ///");
        if (safety.isStationSafe()) {
            System.out.println("No issues found.");
        } else {
            safety.getIssues().forEach(issue -> System.out.println("! " + issue));
        }
        System.out.println();

        MaintenanceCostVisitor cost = new MaintenanceCostVisitor();
        station.accept(cost);
        System.out.println("=== Yearly maintenance cost ===");
        System.out.printf("Total: %.1f thousand USD%n", cost.getTotalCostThousandUsd());
    }

    private static SpaceStation buildStation() {
        SpaceStation station = new SpaceStation("Astana-1");
        station.addModule(new LivingModule("Zarya Habitat", 6, 8));
        station.addModule(new LabModule("Biolab Tulpar", 35.0, 4, 3));
        station.addModule(new LabModule("Optics Lab", 20.0, 2, 1));
        station.addModule(new EngineModule("Orbit Thruster", 12.5, 2.0, 10.0));
        station.addModule(new SolarPanelModule("Solar Wing A", 120.0, 0.30, 16));
        station.addModule(new SolarPanelModule("Solar Wing B", 120.0, 0.30, 4));
        return station;
    }
}

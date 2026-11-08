package spacestation.element;

import spacestation.visitor.ModuleVisitor;

//Concrete element
public class LivingModule implements StationModule {

    private final String name;
    private final int designCapacity;
    private final int currentCrew;

    public LivingModule(String name, int designCapacity, int currentCrew) {
        this.name = name;
        this.designCapacity = designCapacity;
        this.currentCrew = currentCrew;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getDesignCapacity() {
        return designCapacity;
    }

    public int getCurrentCrew() {
        return currentCrew;
    }

    @Override
    public void accept(ModuleVisitor visitor) {
        visitor.visitLivingModule(this);
    }
}

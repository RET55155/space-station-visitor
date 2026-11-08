# Assignment #6 — Visitor Pattern (Space Station)

**Course:** Software Design Patterns  
**Topic:** Space Station — a stable structure of modules (living, lab, engine, solar panel)
on which several unrelated operations are performed (power balance, safety inspection,
maintenance cost) **without modifying the module classes**.

## Project structure

```
src/main/java/spacestation/
├── element/
│   ├── StationModule.java        <- Element interface:  accept(ModuleVisitor)
│   ├── LivingModule.java         <- Concrete Element 1
│   ├── LabModule.java            <- Concrete Element 2
│   ├── EngineModule.java         <- Concrete Element 3
│   └── SolarPanelModule.java     <- Concrete Element 4
├── visitor/
│   ├── ModuleVisitor.java        <- Visitor interface: one visitX() per element
│   ├── PowerBalanceVisitor.java  <- Concrete Visitor 1
│   ├── SafetyInspectionVisitor.java <- Concrete Visitor 2
│   └── MaintenanceCostVisitor.java  <- Concrete Visitor 3 (added later, no element changes)
├── structure/
│   └── SpaceStation.java         <- Object Structure (collection + traversal)
└── Main.java                     <- Client
```

## How to run

```bash
mkdir out
javac -d out $(find src -name "*.java")     # Linux / macOS / Git Bash
java -cp out spacestation.Main
```
(In IntelliJ IDEA: open the folder, mark `src/main/java` as *Sources Root*, run `Main`.)

Expected output:

```
=== Power balance ===
Consumed: 69.60 kW
Produced: 93.02 kW
Net:      23.42 kW

=== Safety inspection ===
! Zarya Habitat: overcrowded (8/6)
! Biolab Tulpar: hazard level 4 with 3 running experiments
! Orbit Thruster: low fuel (20%)
! Solar Wing A: panels are 16 years old, replacement needed

=== Yearly maintenance cost ===
Total: 1100.0 thousand USD
```

## Requirements checklist

| Requirement | Where |
| --- | --- |
| Element interface declares `accept(Visitor)` | `StationModule` |
| >= 2 Concrete Elements, each calls matching `visitX(this)` | 4 classes in `element/` |
| Visitor interface with one `visitX()` per element | `ModuleVisitor` (4 methods) |
| >= 2 Concrete Visitors implementing every `visitX()` | 3 classes in `visitor/` |
| Object Structure traversed by the client, calling `accept()` | `SpaceStation.accept(...)` |

## Double dispatch — how it works

```java
station.accept(visitor);          
module.accept(visitor);            
visitor.visitLabModule(this);     
```

Java chooses overloads by the *static* type of the argument. Inside `LabModule.accept()` the
type of `this` is statically known to be `LabModule`, so the right `visitLabModule` is chosen at
compile time, while the concrete visitor is selected at runtime. Together this replaces an
`if (module instanceof LabModule) ... else if (...)` chain.

## Clean Code principles applied (and why)

1. **Meaningful names, clear Element vs. Visitor roles.**
   Elements are nouns of the domain (`LabModule`, `EngineModule`), visitors are named after the
   operation they perform (`PowerBalanceVisitor`, `SafetyInspectionVisitor`). Methods are
   `accept` (element side) and `visitLabModule` (visitor side), so the role of a class is clear
   from its name and from the package (`element` vs. `visitor`).

2. **No `instanceof` / type-checking chains anywhere.**
   Double dispatch fully replaces them. `grep -rn instanceof src` returns nothing.
   The compiler forces every visitor to handle every module type.

3. **Single Responsibility Principle.**
   Each concrete visitor implements exactly one operation across all elements
   (power / safety / cost). Each element only stores its own data and knows how to
   "accept" a visitor — no business algorithms inside the elements.

4. **Open/Closed Principle — demonstrated extensibility.**
   `MaintenanceCostVisitor` is the third visitor: it was added without changing
   `StationModule`, any concrete module, or `SpaceStation`. New operation = new class.

5. **No magic numbers.**
   All coefficients are named constants (`KW_PER_CREW_MEMBER`, `LOW_FUEL_RATIO`,
   `PANEL_REPLACEMENT_AGE_YEARS`, ...), so the algorithms read like documentation.

6. **Small methods / one level of abstraction.**
   Every `visitX()` is a few lines long and does one thing; the traversal logic lives only in
   `SpaceStation.accept()`.

7. **Immutability and encapsulation of the structure.**
   Element fields are `private final`; `SpaceStation.getModules()` returns an unmodifiable view;
   `SafetyInspectionVisitor.getIssues()` returns an unmodifiable list.

### Encapsulation trade-off (known cost of Visitor)

Visitors need the internal data of elements (crew size, hazard level, fuel...), so elements
expose **read-only getters** (no setters). Trade-off: the public getters become part of the
element API. We kept it minimal — only data that some visitor really needs is exposed, and
nothing can be mutated through these getters.

Another known trade-off: adding a **new element** type (e.g. `DockingModule`) requires adding a
`visitDockingModule()` to the `ModuleVisitor` interface and to every visitor. Visitor fits best
when the element hierarchy is stable and the set of operations grows — which is exactly the
case for a space station.

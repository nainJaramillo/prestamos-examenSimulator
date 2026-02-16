# **Practical Exam DAM1 — Programming + Development Environments**

## **Context**

At the school there is a small “storage room” of equipment (laptops, projectors, etc.) that is lent to teachers. You must build a **console** Java application to register equipment, manage loans, and work with a CSV file of materials.

> Important: this repository
>
>
> **does not include implementations**
>

---

# **1) Development Environments (DE) Requirements**

## **1.1 GitHub (Fork + Project + Issues + PR)**

1. **FORK** this repository to your account.
2. Create a **GitHub Project** (board) in your fork with these columns:
    - To do
    - In progress
    - In review
    - Done
3. Create at least **3 Issues** (tasks) and add them to the Project. Examples:
    - Model (OOP)
    - Generic repositories
    - Services + rules
    - Exceptions
    - Maven (dependencies + exec plugin)
    - Tests (JUnit + Mockito)
    - CSV integration
4. You must work with branches named feature/*.
5. **Any change you want to integrate into main must go through a Pull Request (PR)** (direct merge to main is not allowed).
6. Each PR must be **approved by you** (self-review) before merging.
7. Each PR must:
    - include a brief description of what was done
    - link an Issue (e.g., Closes #3)

---

# **2) Maven (DE) Requirements — mandatory**

## **2.1 Java**

The project must compile with **Java 21**.

## **2.2 Required dependencies (add to**

## **pom.xml**

## **)**

You must add exactly these dependencies and versions:

- **Apache Commons CSV**
    - groupId: org.apache.commons
    - artifactId: commons-csv
    - version: 1.14.1
- **JUnit Jupiter (JUnit 5)**
    - groupId: org.junit.jupiter
    - artifactId: junit-jupiter
    - version: 5.14.1
    - scope: test
- **Mockito JUnit Jupiter**
    - groupId: org.mockito
    - artifactId: mockito-junit-jupiter
    - version: 5.21.0
    - scope: test

## **2.3 Plugins**

- The **maven-surefire-plugin** (tests) is **already configured** in this repository and **you must not change it**.
- You must configure **exec-maven-plugin** so you can run mvn exec:java:
    - groupId: org.codehaus.mojo
    - artifactId: exec-maven-plugin
    - version: 3.6.3
    - required configuration:
        - <mainClass>es.fplumara.dam1.prestamos.app.Main</mainClass>

✅ It must be runnable with:

- mvn clean test
- mvn exec:java

---

# **3) Programming Requirements**

## **3.1 Layers and packages**

You must organize the code by layers using these packages:

- es.fplumara.dam1.prestamos.app
- es.fplumara.dam1.prestamos.model
- es.fplumara.dam1.prestamos.repository
- es.fplumara.dam1.prestamos.service
- es.fplumara.dam1.prestamos.csv
- es.fplumara.dam1.prestamos.exception

“No everything in Main” is allowed.

---

## **3.2 Model**

You must create the classes/interfaces/enums indicated in the diagram in docs/diagrama-clases.md.

Minimum requirements:

- An **interface** Identificable with String getId().
- An **enum** EstadoMaterial: DISPONIBLE, PRESTADO, BAJA.
- An **abstract** class Material (with an abstract getTipo()).
- Subclasses:
    - Portatil (includes ramGB)
    - Proyector (includes lumens)
- Material must have a Set<String> of etiquetas (no duplicates).
- Class Prestamo with:
    - id, idMaterial, profesor, fecha (LocalDate)

*(The full diagram is in docs/diagrama-clases.md.)*

---

## **3.3 Repositories**

Create a generic repository:

- Repository<T extends Identificable> with:
    - save(T)
    - findById(String) → Optional<T>
    - listAll() → List<T>
    - delete(String)

Implementation:

- BaseRepository<T extends Identificable>
    - must internally use a Map<String, T>
- MaterialRepositoryImpl extends BaseRepository<Material>
- PrestamoRepositoryImpl extends BaseRepository<Prestamo>

---

## **3.4 Services (business rules)**

> In all methods, when “→ exception” is indicated, you must throw
>
>
> **one of your custom exceptions**
>

### **Material service**

Create MaterialService with at least:

- registrarMaterial(Material m)
    - If a material with the same id already exists → **DuplicadoException**
    - If m is null or id is null/blank → **IllegalArgumentException**
- darDeBaja(String idMaterial)
    - If the material does not exist → **NoEncontradoException**
    - If it is already BAJA → **MaterialNoDisponibleException**
    - If it exists and is not BAJA: change state to BAJA and save the change
- listar()
    - Returns List<Material> with all materials

### **Loan service**

Create PrestamoService with at least:

- crearPrestamo(String idMaterial, String profesor, LocalDate fecha)
    - If any parameter is null/blank (or date is null) → **IllegalArgumentException**
    - If no material exists with that id → **NoEncontradoException**
    - If it exists but its state is not DISPONIBLE → **MaterialNoDisponibleException**
    - If OK:
        - create a Prestamo (id e.g. with UUID)
        - save the loan with PrestamoRepository
        - change the material state to PRESTADO and save the updated material
- devolverMaterial(String idMaterial)
    - If idMaterial is null/blank → **IllegalArgumentException**
    - If the material does not exist → **NoEncontradoException**
    - If it exists but its state is not PRESTADO → **MaterialNoDisponibleException**
    - If OK: change state to DISPONIBLE and save the updated material
- listarPrestamos()
    - Returns List<Prestamo> with all loans

---

## **3.5 Custom exceptions**

Create and use these exceptions (package ...exception):

- DuplicadoException
- NoEncontradoException
- MaterialNoDisponibleException
- CsvInvalidoException

---

# **4) CSV (Apache Commons CSV) —**

# **provided code (do not modify)**

## **4.1 CSV file**

There is an example file data/materiales.csv with this format:

```
tipo,id,nombre,estado,extra,etiquetas
PORTATIL,M001,Portátil Aula 1,DISPONIBLE,16,ofimatica|aula1
PROYECTOR,M010,Proyector Epson,DISPONIBLE,3200,video|salon
```

## **4.2 Provided CSV code (do not edit)**

The repository provides utility classes in the package:

- es.fplumara.dam1.prestamos.csv

For example:

- CsvMaterialImporter
- CsvMaterialExporter
- RegistroMaterialCsv (record/DTO)

**Rule:** these classes are there to **use**, not to rewrite the parser.

- You must not modify their internal logic.
- You must **integrate** them into your application and understand what they return.

---

# **5) Unit tests (JUnit + Mockito) — mandatory**

Create tests for PrestamoService using **Mockito** with mocked repositories.

Minimum 4 tests:

1. crearPrestamo_ok_cambiaEstado_y_guarda
2. crearPrestamo_materialNoExiste_lanzaNoEncontrado
3. crearPrestamo_materialNoDisponible_lanzaMaterialNoDisponible
4. devolverMaterial_ok_cambiaADisponible

Requirements:

- use @ExtendWith(MockitoExtension.class)
- repository mock(s)
- verify interactions with verify(...) at least in the OK case

---

# **6) Main program (minimum)**

In app.Main, demonstrate a simple flow (no complex menu):

1. Import materials from data/materiales.csv (provided importer)
2. Convert/register materials in the repository (using MaterialService)
3. Create a loan
4. List materials and loans to the console
5. Return the material
6. Export materials to data/salida_materiales.csv (provided exporter)

---

# **7) Evaluation criteria**

## **DE**

- Fork + Project + Issues used correctly
- feature/* branches and traceability with PRs
- Maven handling and configuration
- JUnit+Mockito tests with verify

## **Programming**

- Correct OOP model (interface/abstract/enum/inheritance/polymorphism)
- Repositories + Map + Optional + List/Set
- Services with rules + correctly applied exceptions
- Creation and use of custom exceptions
- CSV integrated (reading + conversion + export)
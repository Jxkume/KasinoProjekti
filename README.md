# KasinoSimulaattori

A **Casino Simulator** desktop application built with Java and JavaFX. It simulates casino operations including games (Roulette, Blackjack, Craps), service desks, and payout points, with a discrete-event simulation engine and optional database persistence.

## Authors

- Tapio Humaljoki  
- Valtteri Kuitula  
- Jhon Rastrojo  

## Features

- **Simulation engine** – Discrete-event simulation (Moottori, Kello, Tapahtumalista)
- **Casino games** – Ruletti (Roulette), Blackjack, Kraps (Craps)
- **Service points** – Palvelutiski (service desk), Voittojen nostopiste (payout point)
- **JavaFX UI** – FXML-based views and controllers
- **Persistence** – Hibernate ORM with MySQL/MariaDB (JPA)
- **Testing** – JUnit tests (e.g. `AsiakasTest`, `PalvelupisteTest`)

## Tech stack

| Category    | Technology        |
|------------|-------------------|
| Language   | Java 25           |
| UI         | JavaFX 25         |
| Build      | Maven             |
| ORM        | Hibernate 6.6     |
| Database   | MySQL (Connector/J 9.x) |

## Prerequisites

- **JDK 25** ([Oracle](https://www.oracle.com/java/technologies/downloads/) or [Eclipse Temurin](https://adoptium.net/))
- **Maven** (optional; needed only for command-line run)
- **MySQL** (optional; only if you use database persistence)

## Getting started

### 1. Clone and build

```bash
git clone https://github.com/YOUR_USERNAME/KasinoProjekti.git
cd KasinoProjekti
mvn clean compile
```

### 2. Run the application

**Option A – Maven (recommended)**

From the project root (where `pom.xml` is):

```bash
mvn javafx:run
```

**Option B – Eclipse**

1. Import as **Existing Maven Project** and use **JDK 25** as project JRE.
2. Right-click **`src/main/SimulaattoriMain.java`** → **Run As** → **Run Configurations...**
3. Select or create a **Java Application** run for `main.SimulaattoriMain`.
4. In **Arguments** → **VM arguments**, set (replace `YOUR_USERNAME` with your Windows username if needed):

   ```
   --module-path "C:\Users\YOUR_USERNAME\.m2\repository\org\openjfx\javafx-base\25\javafx-base-25-win.jar;C:\Users\YOUR_USERNAME\.m2\repository\org\openjfx\javafx-graphics\25\javafx-graphics-25-win.jar;C:\Users\YOUR_USERNAME\.m2\repository\org\openjfx\javafx-controls\25\javafx-controls-25-win.jar;C:\Users\YOUR_USERNAME\.m2\repository\org\openjfx\javafx-fxml\25\javafx-fxml-25-win.jar" --add-modules javafx.controls,javafx.fxml,javafx.graphics
   ```

5. **JRE** tab: use **JDK 25**.
6. **Apply** → **Run**.

> **Non-Windows:** In `pom.xml`, set `<javafx.platform>` to `linux` or `mac` and use the corresponding `-linux` or `-mac` JAR paths in `--module-path` in your IDE.

## Project structure

```
KasinoProjekti/
├── pom.xml
├── src/
│   ├── main/                    # Application entry (SimulaattoriMain)
│   ├── view/                    # FXML views and controllers
│   ├── simu/
│   │   ├── framework/           # Simulation engine (Moottori, Kello, Trace, etc.)
│   │   └── model/               # Domain (Kasino, Asiakas, Palvelupiste, games)
│   ├── dao/                     # Data access (KasinoDao, PalvelupisteDao)
│   ├── datasource/              # DB connection (e.g. MariaDB JPA)
│   ├── eduni/distributions/     # Random distributions for simulation
│   ├── tests/                   # JUnit tests
│   ├── view/                    # FXML files (Ruletti, Blackjack, Kraps, etc.)
│   └── META-INF/                # JPA persistence config
```

## License

This project does not include a custom license file. Use and distribution are at your own responsibility unless you add a license (e.g. MIT, Apache 2.0) later.

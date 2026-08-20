# One Piece Spring Application

**Ivanov Vlad** 

---

## Domain

The application is based on the One Piece universe and manages three entities:

- **Character** – a pirate or other figure (name, age, appearance URL, powertype, power level). A character can be a **Swordsman** (subclass) with an additional sword name attribute.
- **Crew** – a pirate crew (name, ship name, has bounty flag). One crew has many characters.
- **Battle** – a battle event (name, location, date/time, winner). Characters and Battles have a many-to-many relationship via a `character_battles` cross-table.

Relations:
- `Crew` → `Character`: one-to-many
- `Character` ↔ `Battle`: many-to-many

---

## Profiles

The application supports four Spring profiles that control the repository layer:

| Profile    | Repository implementation         | Database          |
|------------|-----------------------------------|--------------------|
| `memory`   | Java Collections (InMemory)       | none (in-memory)  |
| `jdbc`     | Spring JdbcClient                 | H2 (in-memory)     |
| `jpa`      | JPA EntityManager                 | H2 or PostgreSQL   |
| `datajpa`  | Spring Data JpaRepository          | H2 or PostgreSQL   |

Database profiles (combined with a repository profile, only relevant for `jpa`/`datajpa`):

| Profile | Database             |
|---------|-----------------------|
| `dev`   | H2 in-memory           |
| `prod`  | PostgreSQL             |

**Default active profile (`application.properties`): `memory`** — running `./gradlew bootRun` with no arguments uses the in-memory `DataFactory` implementation, no database required.

To switch profile, pass it on the command line, e.g.:
```
./gradlew bootRun --args='--spring.profiles.active=jdbc'
./gradlew bootRun --args='--spring.profiles.active=jpa,dev'
./gradlew bootRun --args='--spring.profiles.active=datajpa,prod'
```

---

## Database configuration

**H2 (dev, used with `jpa`/`datajpa`):**
```
spring.datasource.url=jdbc:h2:mem:onepiece;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=
```
H2 console: http://localhost:8080/h2-console

**H2 (jdbc profile, self-contained, no `dev`/`prod` needed):**
```
spring.datasource.url=jdbc:h2:mem:onepiece;DB_CLOSE_DELAY=-1
```
Schema and seed data come from `schema.sql` / `data.sql`.

**PostgreSQL (prod):**
```
spring.datasource.url=jdbc:postgresql:pro3_db
spring.datasource.username=postgres
spring.datasource.password=Student_1234
```
Requires a running PostgreSQL instance with a `pro3_db` database, e.g. via Docker:
```
docker run --name onepiece-pg -e POSTGRES_PASSWORD=Student_1234 -e POSTGRES_DB=pro3_db -p 5432:5432 -d postgres
```

---

## Running the application

**Default (in-memory, no database):**
```
./gradlew bootRun
```

**With JDBC profile (H2):**
```
./gradlew bootRun --args='--spring.profiles.active=jdbc'
```

**With JPA + H2:**
```
./gradlew bootRun --args='--spring.profiles.active=jpa,dev'
```

**With Spring Data JPA + PostgreSQL:**
```
docker run --name onepiece-pg -e POSTGRES_PASSWORD=Student_1234 -e POSTGRES_DB=pro3_db -p 5432:5432 -d postgres
./gradlew bootRun --args='--spring.profiles.active=datajpa,prod'
```

**Start URL:** http://localhost:8080/characters

The console application (from week 1) is still fully functional and runs alongside the web application in the same process — after starting, the console menu appears in the terminal while the web server listens on port 8080.

---

## What is completed

| Feature                                                                                   | Status |
|--------------------------------------------------------------------------------------------|--------|
| Console application, DataFactory, filtering, streams                                       | done     |
| Layered architecture, Spring Boot, CommandLineRunner                                       | done     |
| Web front-end, 4 pages, filtering, logging                                                 | done     |
| Thymeleaf fragments, custom CSS, i18n (EN/NL), `#lists` utility                             | done     |
| Bootstrap cards, responsive layout (1/2/4 cols), Navbar, footer, client-side validation, `th:class` conditional styling | done |
| ViewModels, custom Converter (`StringToCrewConverter`), Bean Validation, Session History page | done |
| JdbcClient + H2, `schema.sql` / `data.sql`, profiles                                        | done     |
| Relationships in DB (cross-table), delete functionality, detail pages with sub-lists         | done     |
| JPA EntityManager, `application-dev`/`prod.properties`, PostgreSQL                          | done     |
| `open-in-view=false`, `Swordsman` subclass, Spring Data `JpaRepository`, method queries, `@Query` | done |
| `GlobalExceptionHandler` (`@ControllerAdvice`), `CharacterNotFoundException`, 2 error pages, logging | done |
| README.md                                                                                   | done     |

Everything required across all assignments is implemented; there are no known gaps.

---

## What makes this implementation unique

**Domain:** One Piece universe with real character/battle data gives a rich many-to-many dataset that makes all relationships meaningful.

**Technical:**
- `Swordsman` uses JPA `SINGLE_TABLE` inheritance with `DiscriminatorValue` — visible in the character list as a badge and on the detail page with a live sword-name update form (no page reload needed).
- The Search page (`/characters/search`) uses two Spring Data derived query methods (`findByNameContainingIgnoreCase`, `findByPowerGreaterThanEqual`) plus one `@Query` (`findByMinBattles`) — all exposed on a single page, with equivalent implementations across all four repository profiles (in-memory streams, JdbcClient SQL, JPQL, Spring Data).
- Four interchangeable repository implementations (`memory`/`jdbc`/`jpa`/`datajpa`) sit behind the same repository interfaces, swappable purely via Spring profiles without touching the service or presentation layers.
- Battle rows are conditionally highlighted with `th:classappend` based on the winner name — a clear example of combined Thymeleaf + Bootstrap styling.
- `HistoryInterceptor` records every GET visit to the session without polluting controllers.
- Filter queries that combine a text field with an optional value avoid binding untyped `null` JPA/JDBC parameters (PostgreSQL cannot infer a type for those) — each filter combination is built as its own explicit query instead.

**Presentation:**
- Ocean-themed Bootstrap color palette (`bg-ocean`) applied via a custom CSS variable.
- Characters with power ≥ 9.0 DON get a gold card border — visually distinguishing the strongest fighters.
- The character count badge is hidden on small screens (`d-none d-lg-inline-block`), demonstrating `d-none` usage.

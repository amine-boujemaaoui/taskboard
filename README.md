# TaskBoard

TaskBoard est une application web de gestion de tâches, en cours d'évolution vers un **outil de tableaux Kanban collaboratif** : tableaux partagés entre plusieurs utilisateurs, colonnes et cartes en glisser-déposer, rôles, historique d'activité.

Le projet est construit étape par étape, chaque étape faisant l'objet d'une pull request (voir [Avancement](#avancement)).

## Stack technique

| Partie | Technologies |
|---|---|
| Backend | Java 21, Spring Boot 4 (Web MVC, Data JPA, Validation), Maven |
| Base de données | PostgreSQL 16, migrations Flyway |
| Frontend | Angular 21 (composants standalone), Tailwind CSS, Vitest |
| Infrastructure | Docker Compose |

## Structure du dépôt

```
taskboard/
├── backend/    API REST Spring Boot
│   └── src/main/resources/db/migration/   migrations Flyway (schéma SQL)
├── frontend/   application Angular
└── infra/      fichiers Docker Compose pour l'environnement de développement
```

## Lancer le projet en local

### Prérequis

- JDK 21
- Node.js 24 (LTS)
- Docker Desktop

Maven n'a pas besoin d'être installé : le projet embarque le *Maven Wrapper* (`mvnw`).

### 1. Démarrer la base de données

```bash
docker compose -f infra/compose.dev.yml up -d
```

PostgreSQL écoute alors sur `localhost:5432` (base, utilisateur et mot de passe : `taskboard`, valeurs réservées au développement).

### 2. Démarrer le backend

```bash
cd backend
./mvnw spring-boot:run
```

Au démarrage, Flyway applique les migrations, puis l'API est disponible sur `http://localhost:8080/api/tasks`.

### 3. Démarrer le frontend

```bash
cd frontend
npm ci
npm start
```

L'application est disponible sur `http://localhost:4200`.

### Lancer les tests

```bash
cd backend && ./mvnw test      # la base de données doit être démarrée
cd frontend && npm test -- --watch=false
```

## API

| Méthode | Route | Description |
|---|---|---|
| `GET` | `/api/tasks` | Liste des tâches |
| `POST` | `/api/tasks` | Création d'une tâche |
| `PUT` | `/api/tasks/{id}` | Modification d'une tâche |
| `DELETE` | `/api/tasks/{id}` | Suppression d'une tâche |

## Avancement

**Phase 0 : fondations**
- [x] 0.1 Passage en Java 21, README, licence
- [ ] 0.2 Tests d'intégration avec Testcontainers
- [ ] 0.3 Intégration continue avec GitHub Actions

**Phase 1 : assainir le backend**
- [ ] 1.1 Énumérations pour le statut et la priorité
- [ ] 1.2 Couche service et DTO
- [ ] 1.3 Gestion des erreurs (`ProblemDetail`)
- [ ] 1.4 Configuration par variables d'environnement

**Phase 2 : authentification**
- [ ] 2.1 Utilisateurs et inscription
- [ ] 2.2 Connexion par JWT avec Spring Security
- [ ] 2.3 Connexion côté Angular

**Phase 3 : Kanban collaboratif**
- [ ] 3.1 Tableaux et membres
- [ ] 3.2 Colonnes et cartes
- [ ] 3.3 Autorisations par tableau
- [ ] 3.4 Interface Kanban en glisser-déposer
- [ ] 3.5 Étiquettes, échéances, commentaires
- [ ] 3.6 Historique d'activité

**Phase 4 : production**
- [ ] 4.1 Dockerfiles et Compose complet
- [ ] 4.2 Documentation OpenAPI et supervision
- [ ] 4.3 Déploiement continu et démo en ligne
- [ ] 4.4 Temps réel par WebSocket
- [ ] 4.5 Tests de bout en bout

## Licence

Distribué sous licence MIT. Voir [LICENSE](LICENSE).

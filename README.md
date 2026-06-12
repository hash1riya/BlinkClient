# Blink Client 📱

Blink Client is a modern, responsive desktop messaging interface built with **JavaFX** and modular Java architecture. Serving as the frontend engine for the Blink ecosystem, it provides a seamless chat workspace supporting public multi-user channels and private 1-to-1 direct messages. 

Engineered for extreme performance, the client implements non-blocking I/O and responsive data streaming patterns to deliver a smooth desktop user experience without interface stuttering.

---

## 🎨 Core Design Patterns

### 1. Asynchronous Optimistic UI Pipeline
To eliminate perceived network latency, the client handles outbound message state shifts optimistically:
* **Instant UI Mutation:** When a user hits "Send", the client immediately appends the message package to the active `ListView` with a `PENDING` state status token.
### Future Updates
* **Non-Blocking Execution Threading:** The heavy HTTP POST network execution loop is handed off to a background thread pool via `CompletableFuture.supplyAsync()`. The main JavaFX UI thread stays completely unblocked, allowing users to continue typing or switching channels seamlessly.
* **Thread-Safe Synchronization:** Upon receiving a confirmation payload from the Spring Boot API, the application uses `Platform.runLater()` to safely jump back onto the JavaFX Application Thread, inject database parameters (ID and server timestamp), and shift the state flag to `SENT` or `FAILED`.

### 2. Client-Side Adapter Layer (DTO Mapping)
Because the API yields raw, normalized database payloads (e.g., passing a `userId` string rather than full user profiles), the client utilizes a defensive adapter parsing pipeline. It reads incoming JSON streams through Jackson and transforms them into rich, UI-friendly UI models, dynamically resolving elements like author usernames and room title decorations layout-wide.

---

## 🛠️ Technology Stack

| Component | Technology | Purpose |
| :--- | :--- | :--- |
| **Language Core** | Java 25 | Utilizes modern language features and records |
| **Graphics Engine** | JavaFX 21 & FXML | Declarative layout composition and hardware acceleration |
| **JSON Serialization** | Jackson (Core & JSR310) | Automated binding and modern Java 8+ Date/Time handling |
| **Networking Layer** | Java `HttpClient` | Built-in asynchronous HTTP/1.1 and HTTP/2 pipeline support |
| **Build Automation** | Apache Maven | Modular dependency management and lifecycle tracking |

---

## ⚙️ Configuration & Installation

### Prerequisites
* **Java Development Kit (JDK) 21** or higher.
* **Apache Maven** configuration tools installed globally.

### Launching the Application
1. Clone the client repository and enter the workspace directory:
```bash
   git clone [https://github.com/yourusername/blink-client.git](https://github.com/yourusername/blink-client.git)
   cd blink-client
```
Compile resources and execute the modular application pipeline via the JavaFX Maven target:

```bash
   mvn clean javafx:run
```

## Future Roadmap
### 1. Real-Time Protocol Migration (WebSockets)
**Objective** Replace the current HTTP request polling architecture with a full-duplex, persistent connection using WebSockets or Server-Sent Events (SSE).

**Impact** Eliminates network overhead, slashes latency, and allows the UI to receive messages from other users instantly without constantly pinging the backend.

### 2. Custom Visual Component Architecture (ListCell Factories)
**Objective** Implement advanced JavaFX CellFactory handlers for both the message feed and user directories.

**Impact** Allows rich-media rendering, such as user profile pictures, color-coded online status indicators, and distinct contextual styling (e.g., a faded layout for PENDING messages, and a red warning icon with a "Click to Retry" action hook for FAILED entries).

### 3. Client-Side Persistent Cache (SQLite / H2)
**Objective** Introduce an embedded local database layer to store encrypted message logs on the user's machine.

**Impact** Enables instant offline access to previous chat histories upon application launch and drastically reduces the data load on your Spring Boot API.

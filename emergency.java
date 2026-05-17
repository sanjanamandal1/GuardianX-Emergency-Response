import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.*;

/**
 * SafeSignal – Smart SOS Alert Platform
 * A Silent Emergency Alert System for personal safety.
 *
 * Features:
 *  - Emergency contact management
 *  - One-click SOS alert simulation
 *  - Secret distress keyword detection
 *  - Danger-level analysis & escalation
 *  - Emergency history logging
 *  - Repeated distress pattern detection
 *  - Silent mode alert simulation
 *  - Unsafe-zone tracking
 *
 * Tech: Java | OOP | ArrayList | File Handling | Exception Handling
 *
 * Inspired by increasing concerns around personal safety
 * and emergency response accessibility.
 *
 * @author  Your Name
 * @version 1.0
 */
public class SafeSignal {

    // ─────────────────────────────────────────────
    //  Inner Classes
    // ─────────────────────────────────────────────

    /** Represents an emergency contact. */
    static class Contact {
        String name;
        String phone;
        String relationship;
        int priority; // 1 = highest

        Contact(String name, String phone, String relationship, int priority) {
            this.name         = name;
            this.phone        = phone;
            this.relationship = relationship;
            this.priority     = priority;
        }

        @Override
        public String toString() {
            return String.format("[P%d] %s (%s) – %s", priority, name, relationship, phone);
        }
    }

    /** Represents a single distress / alert event in history. */
    static class AlertEvent {
        String type;
        String details;
        int    dangerLevel; // 1–5
        String timestamp;
        String location;

        AlertEvent(String type, String details, int dangerLevel, String location) {
            this.type        = type;
            this.details     = details;
            this.dangerLevel = dangerLevel;
            this.location    = location;
            this.timestamp   = LocalDateTime.now()
                                   .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        @Override
        public String toString() {
            return String.format("[%s] %-20s | Danger: %s | Location: %s | %s",
                    timestamp, type, dangerBar(dangerLevel), location, details);
        }

        private String dangerBar(int level) {
            String[] bars = {"", "█░░░░ LOW", "██░░░ MODERATE", "███░░ HIGH",
                             "████░ CRITICAL", "█████ EXTREME"};
            return bars[Math.min(level, 5)];
        }
    }

    /** Tracks unsafe / flagged zones. */
    static class Zone {
        String name;
        String coordinates; // simulated as text
        String reason;
        boolean active;

        Zone(String name, String coordinates, String reason) {
            this.name        = name;
            this.coordinates = coordinates;
            this.reason      = reason;
            this.active      = true;
        }

        @Override
        public String toString() {
            return String.format("%-20s | Coords: %-25s | Reason: %s | %s",
                    name, coordinates, reason, active ? "ACTIVE" : "INACTIVE");
        }
    }

    // ─────────────────────────────────────────────
    //  Application State
    // ─────────────────────────────────────────────

    private static final ArrayList<Contact>    contacts    = new ArrayList<>();
    private static final ArrayList<AlertEvent> history     = new ArrayList<>();
    private static final ArrayList<Zone>       unsafeZones = new ArrayList<>();

    private static final Set<String> DISTRESS_KEYWORDS = new HashSet<>(Arrays.asList(
            "help", "sos", "emergency", "danger", "save me", "call police",
            "im in danger", "scared", "threat", "attack"
    ));

    private static       int     dangerLevel      = 1;
    private static       int     distressCount    = 0;
    private static       boolean silentMode       = false;
    private static       String  currentLocation  = "Unknown";
    private static final String  LOG_FILE         = "safesignal_log.txt";
    private static final Scanner scanner          = new Scanner(System.in);

    // ─────────────────────────────────────────────
    //  Entry Point
    // ─────────────────────────────────────────────

    public static void main(String[] args) {
        loadSampleData();
        printBanner();
        mainMenu();
    }

    // ─────────────────────────────────────────────
    //  UI Helpers
    // ─────────────────────────────────────────────

    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║   SafeSignal – Smart SOS Alert Platform      ║");
        System.out.println("  ║   Silent Emergency Alert System  v1.0        ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");
        System.out.println("  Status: ACTIVE  |  Mode: " + (silentMode ? "SILENT" : "NORMAL")
                         + "  |  Danger: " + dangerLevel + "/5");
        System.out.println();
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n  ─── MAIN MENU ───────────────────────────────");
            System.out.println("  1. 🆘  Trigger SOS Alert");
            System.out.println("  2. 👤  Manage Emergency Contacts");
            System.out.println("  3. 📍  Set / Update My Location");
            System.out.println("  4. 🔇  Toggle Silent Mode  [" + (silentMode ? "ON" : "OFF") + "]");
            System.out.println("  5. 🔍  Distress Keyword Check");
            System.out.println("  6. 🗺️   Manage Unsafe Zones");
            System.out.println("  7. 📋  View Alert History");
            System.out.println("  8. 📊  Danger Level Analysis");
            System.out.println("  9. 💾  Export Log to File");
            System.out.println("  0. ❌  Exit");
            System.out.print("\n  Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> triggerSOS();
                case "2" -> manageContacts();
                case "3" -> updateLocation();
                case "4" -> toggleSilentMode();
                case "5" -> distressKeywordCheck();
                case "6" -> manageUnsafeZones();
                case "7" -> viewHistory();
                case "8" -> dangerAnalysis();
                case "9" -> exportLog();
                case "0" -> { System.out.println("\n  Stay safe. Goodbye.\n"); return; }
                default  -> System.out.println("  ⚠ Invalid option.");
            }
        }
    }

    // ─────────────────────────────────────────────
    //  Feature 1 – SOS Alert
    // ─────────────────────────────────────────────

    private static void triggerSOS() {
        System.out.println("\n  ══════════════════════════════════════");
        System.out.println("  🆘  SOS ALERT TRIGGERED");
        System.out.println("  ══════════════════════════════════════");

        distressCount++;
        escalateDanger(2);

        String details = "Manual SOS triggered by user";
        AlertEvent event = new AlertEvent("SOS ALERT", details, dangerLevel, currentLocation);
        history.add(event);

        System.out.println("  ✔  Alert recorded at: " + event.timestamp);
        System.out.println("  ✔  Location: " + currentLocation);
        System.out.println("  ✔  Current Danger Level: " + dangerLevel + "/5");
        System.out.println();

        notifyContacts("SOS ALERT", details);
        saveEventToFile(event);

        if (dangerLevel >= 4) {
            System.out.println("  🚨 PRIORITY ESCALATION ACTIVATED");
            System.out.println("  🚨 Alerting ALL contacts simultaneously...");
        }
    }

    // ─────────────────────────────────────────────
    //  Feature 2 – Contact Management
    // ─────────────────────────────────────────────

    private static void manageContacts() {
        while (true) {
            System.out.println("\n  ─── EMERGENCY CONTACTS (" + contacts.size() + ") ───");
            System.out.println("  1. View contacts");
            System.out.println("  2. Add contact");
            System.out.println("  3. Remove contact");
            System.out.println("  4. Back");
            System.out.print("  Choice: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> listContacts();
                case "2" -> addContact();
                case "3" -> removeContact();
                case "4" -> { return; }
                default  -> System.out.println("  ⚠ Invalid option.");
            }
        }
    }

    private static void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("  No contacts saved.");
            return;
        }
        contacts.stream()
                .sorted(Comparator.comparingInt(c -> c.priority))
                .forEach(c -> System.out.println("  " + c));
    }

    private static void addContact() {
        System.out.print("  Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("  Phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("  Relationship (e.g., Mother, Friend): ");
        String rel = scanner.nextLine().trim();
        System.out.print("  Priority (1=highest): ");
        int priority = 3;
        try { priority = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException ignored) {}

        contacts.add(new Contact(name, phone, rel, priority));
        System.out.println("  ✔ Contact added: " + name);
    }

    private static void removeContact() {
        listContacts();
        if (contacts.isEmpty()) return;
        System.out.print("  Enter name to remove: ");
        String name = scanner.nextLine().trim();
        boolean removed = contacts.removeIf(c -> c.name.equalsIgnoreCase(name));
        System.out.println(removed ? "  ✔ Removed." : "  ⚠ Not found.");
    }

    // ─────────────────────────────────────────────
    //  Feature 3 – Location
    // ─────────────────────────────────────────────

    private static void updateLocation() {
        System.out.print("\n  Enter your current location (or press Enter to simulate GPS): ");
        String loc = scanner.nextLine().trim();
        if (loc.isEmpty()) {
            // Simulated GPS coordinates
            double lat = 22.0 + (Math.random() * 5);
            double lon = 88.0 + (Math.random() * 5);
            currentLocation = String.format("GPS %.4f°N, %.4f°E", lat, lon);
        } else {
            currentLocation = loc;
        }
        System.out.println("  ✔ Location set to: " + currentLocation);

        // Check if location matches any unsafe zone
        checkUnsafeZone();
    }

    private static void checkUnsafeZone() {
        for (Zone z : unsafeZones) {
            if (z.active && currentLocation.toLowerCase().contains(z.name.toLowerCase())) {
                System.out.println("  ⚠ WARNING: You are near an UNSAFE ZONE: " + z.name);
                System.out.println("  ⚠ Reason: " + z.reason);
                escalateDanger(1);
            }
        }
    }

    // ─────────────────────────────────────────────
    //  Feature 4 – Silent Mode
    // ─────────────────────────────────────────────

    private static void toggleSilentMode() {
        silentMode = !silentMode;
        System.out.println("  ✔ Silent Mode: " + (silentMode ? "ENABLED – alerts send without sound/UI." : "DISABLED."));
    }

    // ─────────────────────────────────────────────
    //  Feature 5 – Distress Keyword Detection
    // ─────────────────────────────────────────────

    private static void distressKeywordCheck() {
        System.out.println("\n  ─── DISTRESS KEYWORD DETECTOR ───");
        System.out.println("  (Type a message to analyze. Type 'back' to return.)");

        while (true) {
            System.out.print("  > ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("back")) return;
            if (input.isEmpty()) continue;

            boolean detected = DISTRESS_KEYWORDS.stream().anyMatch(input::contains);

            if (detected) {
                distressCount++;
                System.out.println("  🚨 DISTRESS DETECTED! Count: " + distressCount);

                if (distressCount >= 3) {
                    System.out.println("  🚨 REPEATED DISTRESS PATTERN – escalating danger!");
                    escalateDanger(1);
                    AlertEvent event = new AlertEvent(
                            "REPEATED DISTRESS", "Keyword: \"" + input + "\" | Count: " + distressCount,
                            dangerLevel, currentLocation);
                    history.add(event);
                    notifyContacts("REPEATED DISTRESS DETECTED", event.details);
                    saveEventToFile(event);
                } else {
                    escalateDanger(1);
                    AlertEvent event = new AlertEvent(
                            "KEYWORD ALERT", "Keyword in: \"" + input + "\"", dangerLevel, currentLocation);
                    history.add(event);
                    notifyContacts("DISTRESS KEYWORD", event.details);
                    saveEventToFile(event);
                }
            } else {
                System.out.println("  ✔ No distress keywords detected.");
            }
        }
    }

    // ─────────────────────────────────────────────
    //  Feature 6 – Unsafe Zone Management
    // ─────────────────────────────────────────────

    private static void manageUnsafeZones() {
        while (true) {
            System.out.println("\n  ─── UNSAFE ZONES (" + unsafeZones.size() + ") ───");
            System.out.println("  1. View zones");
            System.out.println("  2. Add zone");
            System.out.println("  3. Toggle zone active/inactive");
            System.out.println("  4. Back");
            System.out.print("  Choice: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> {
                    if (unsafeZones.isEmpty()) System.out.println("  No zones saved.");
                    else unsafeZones.forEach(z -> System.out.println("  " + z));
                }
                case "2" -> {
                    System.out.print("  Zone name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("  Coordinates / Address: ");
                    String coords = scanner.nextLine().trim();
                    System.out.print("  Reason for flagging: ");
                    String reason = scanner.nextLine().trim();
                    unsafeZones.add(new Zone(name, coords, reason));
                    System.out.println("  ✔ Zone added.");
                }
                case "3" -> {
                    System.out.print("  Zone name to toggle: ");
                    String name = scanner.nextLine().trim();
                    unsafeZones.stream()
                               .filter(z -> z.name.equalsIgnoreCase(name))
                               .findFirst()
                               .ifPresentOrElse(z -> {
                                   z.active = !z.active;
                                   System.out.println("  ✔ Zone is now " + (z.active ? "ACTIVE" : "INACTIVE"));
                               }, () -> System.out.println("  ⚠ Zone not found."));
                }
                case "4" -> { return; }
                default  -> System.out.println("  ⚠ Invalid option.");
            }
        }
    }

    // ─────────────────────────────────────────────
    //  Feature 7 – Alert History
    // ─────────────────────────────────────────────

    private static void viewHistory() {
        System.out.println("\n  ─── ALERT HISTORY (" + history.size() + " events) ───");
        if (history.isEmpty()) {
            System.out.println("  No alerts recorded yet.");
            return;
        }
        for (int i = 0; i < history.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + history.get(i));
        }
    }

    // ─────────────────────────────────────────────
    //  Feature 8 – Danger Level Analysis
    // ─────────────────────────────────────────────

    private static void dangerAnalysis() {
        System.out.println("\n  ─── DANGER LEVEL ANALYSIS ───");
        System.out.println("  Current Level  : " + dangerLevel + " / 5");
        System.out.println("  Distress Count : " + distressCount);
        System.out.println("  Active Alerts  : " + history.size());
        System.out.println("  Location       : " + currentLocation);
        System.out.println("  Silent Mode    : " + (silentMode ? "YES" : "NO"));
        System.out.println();

        String assessment;
        switch (dangerLevel) {
            case 1 -> assessment = "✅ SAFE    – No immediate threat detected.";
            case 2 -> assessment = "🟡 CAUTION – Minor distress signals present.";
            case 3 -> assessment = "🟠 WARNING – Elevated risk. Stay alert.";
            case 4 -> assessment = "🔴 DANGER  – HIGH risk. Contacts alerted.";
            case 5 -> assessment = "🚨 EXTREME – Emergency services recommended!";
            default -> assessment = "Unknown";
        }
        System.out.println("  Assessment: " + assessment);

        System.out.println("\n  Recommendations:");
        if (dangerLevel >= 3) System.out.println("  → Move to a populated, well-lit area.");
        if (dangerLevel >= 4) System.out.println("  → Call emergency services: 112 / 100 / 911.");
        if (distressCount >= 3) System.out.println("  → Repeated pattern detected – escalation active.");
        if (dangerLevel == 1) System.out.println("  → All clear. Continue regular safety checks.");

        System.out.print("\n  Reset danger level to 1? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            dangerLevel   = 1;
            distressCount = 0;
            System.out.println("  ✔ Danger level reset.");
        }
    }

    // ─────────────────────────────────────────────
    //  Feature 9 – Export Log
    // ─────────────────────────────────────────────

    private static void exportLog() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            pw.println("═══════════════════════════════════════════════");
            pw.println("SafeSignal Export – " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pw.println("═══════════════════════════════════════════════");
            pw.println("Location     : " + currentLocation);
            pw.println("Danger Level : " + dangerLevel);
            pw.println("Alert Count  : " + history.size());
            pw.println();
            pw.println("─── CONTACTS ─────────────────────────────────");
            contacts.forEach(c -> pw.println(c.toString()));
            pw.println();
            pw.println("─── ALERT HISTORY ────────────────────────────");
            history.forEach(e -> pw.println(e.toString()));
            pw.println();
            System.out.println("  ✔ Log exported to: " + LOG_FILE);
        } catch (IOException e) {
            System.out.println("  ⚠ Export failed: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  Helpers
    // ─────────────────────────────────────────────

    private static void escalateDanger(int amount) {
        dangerLevel = Math.min(5, dangerLevel + amount);
        System.out.println("  ⚠ Danger level → " + dangerLevel + "/5");
    }

    private static void notifyContacts(String alertType, String details) {
        if (contacts.isEmpty()) {
            System.out.println("  ⚠ No emergency contacts saved! Add contacts for alerts.");
            return;
        }
        System.out.println("\n  📡 Notifying emergency contacts" + (silentMode ? " [SILENT]" : "") + "...");
        contacts.stream()
                .sorted(Comparator.comparingInt(c -> c.priority))
                .forEach(c -> System.out.printf(
                        "  → %s  (%s)  –  %s%n", c.name, c.phone,
                        silentMode ? "[Silent SMS sent]" : "[SMS + Call initiated]"));
        System.out.println("  ✔ All contacts notified.");
    }

    private static void saveEventToFile(AlertEvent event) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            pw.println(event.toString());
        } catch (IOException e) {
            System.out.println("  ⚠ Could not write to log: " + e.getMessage());
        }
    }

    private static void loadSampleData() {
        // Pre-load sample contacts so the app feels ready to use
        contacts.add(new Contact("Mom",          "+91-98765-11111", "Mother",  1));
        contacts.add(new Contact("Best Friend",  "+91-98765-22222", "Friend",  2));
        contacts.add(new Contact("Police Local", "100",             "Police",  1));

        // Pre-load sample unsafe zones
        unsafeZones.add(new Zone("Dark Alley Rd",   "22.5726°N, 88.3639°E", "Poor lighting, reported incidents"));
        unsafeZones.add(new Zone("Abandoned Market", "22.4707°N, 88.3697°E", "No CCTV coverage"));
    }
}
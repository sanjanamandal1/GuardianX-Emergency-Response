# 🛡️ GuardianX – Emergency Response System

> *Inspired by increasing concerns around personal safety and emergency response accessibility.*

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/OOP-Design-blue?style=for-the-badge)
![Build](https://img.shields.io/badge/Build-Passing-brightgreen?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active-success?style=for-the-badge)

🌐 Live Demo: https://sanjanamandal1.github.io/GuardianX-Emergency-Response/

---

## 🎯 Problem Statement

People in danger often **cannot call for help openly**. A victim of harassment, domestic abuse, or street crime may be watched, threatened, or unable to speak freely. Traditional emergency calls require time, visibility, and voice — all of which may be unavailable in critical moments.

**GuardianX** is a console-based safety application that simulates a real-world silent emergency response system — detecting distress patterns, managing emergency contacts, and escalating alerts based on danger level.

---

##  Features

| Feature | Description |
|---|---|
| 🆘 SOS Alert | One-click emergency trigger with instant contact notification |
| 👤 Contact Management | Add/remove contacts with priority levels (P1 = most urgent) |
| 🔍 Keyword Detection | Detects distress keywords (`help`, `danger`, `SOS`, etc.) in real-time |
| 📍 Location Tracking | Manual entry or simulated GPS coordinates |
| 🔇 Silent Mode | Alerts sent without audible feedback — discreet in dangerous situations |
| 🗺️ Unsafe Zone Tracking | Flag and monitor dangerous zones; auto-warns when nearby |
| 📊 Danger Level Analysis | 1–5 scale with pattern-based escalation and safety recommendations |
| 📋 Alert History | Full timestamped log of all events in-session |
| 💾 File Export | Append-mode log export to `guardianx_log.txt` |

### Smart Behaviours
- **Repeated Distress Detection** — if distress keywords appear 3+ times, danger escalates automatically
- **Priority Escalation** — at Danger Level 4+, all contacts are alerted simultaneously
- **Unsafe Zone Proximity Warning** — location is checked against flagged zones on every update

---

## 🧠 OOP Concepts Demonstrated

| Concept | Where Used |
|---|---|
| **Classes & Objects** | `Contact`, `AlertEvent`, `Zone` inner classes |
| **Encapsulation** | Private fields with controlled access via methods |
| **Collections** | `ArrayList<Contact>`, `ArrayList<AlertEvent>`, `HashSet<String>` |
| **Sorting** | `Comparator.comparingInt` for priority-ordered contact listing |
| **File Handling** | Append-mode logging with `FileWriter` + `PrintWriter` |
| **Exception Handling** | `try-catch` for `IOException`, `NumberFormatException` |
| **String Methods** | `.toLowerCase()`, `.contains()`, `.equalsIgnoreCase()` |
| **Lambda & Streams** | `.forEach()`, `.stream().filter().findFirst()` |

---

## 🛠️ Tech Stack

```
Language       : Java (JDK 11+)
Paradigm       : Object-Oriented Programming (OOP)
Data Storage   : ArrayList, HashSet
File I/O       : FileWriter, PrintWriter (append mode)
Error Handling : try-catch blocks
Date/Time      : java.time.LocalDateTime, DateTimeFormatter
```

---

## 📁 Project Structure

```
GuardianX-Emergency-Response/
│
├── .github/
│   └── workflows/
│       └── build.yml        ← Auto-compiles on every push
│
├── emergency.java           ← Main application (all classes inside)
├── guardianx_log.txt        ← Auto-generated at runtime
├── .gitignore
└── README.md
```

---

## ▶️ How to Run

```bash
# Compile
javac emergency.java

# Run
java emergency
```

---

## 🧪 Demo Walkthrough

1. **Start the app** — pre-loaded with 3 contacts and 2 unsafe zones
2. **Set location** — type a zone name like `Dark Alley Rd` to trigger a zone warning
3. **Keyword check** — type `help` three times to trigger repeated distress escalation
4. **SOS** — triggers contact notification and logs the event
5. **Export** — saves the full session log to `guardianx_log.txt`

---

## 💡 Real-World Inspiration

This project simulates the logic behind tools used in actual safety applications:

- **Shake-to-SOS** apps like bSafe and Nirbhaya
- **Silent 911** systems used in domestic violence situations
- **Panic button** features built into women's safety apps

Building this as a Java console application demonstrates an understanding of the architecture and decision-making logic behind such systems — without requiring mobile SDKs or external frameworks.

---

## 🚀 Future Scope

- [ ] Integrate real SMS alerts via Twilio API
- [ ] Android app built with Android Studio
- [ ] Voice distress detection using Java Audio API
- [ ] Real GPS integration via device location services
- [ ] Encrypted local storage for contact data

---

## 👩‍💻 Author

**Sanjana Mandal**  
---

> *"Code can save lives — when built with empathy."*

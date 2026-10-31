# 🐟 MINGWIT

A Java-based virtual pet game that turns everyday pet care into a fun, interactive experience. Create and customize your own pet, keep it fed, hydrated, and medicated, and go fishing to catch fish of different rarities, earning points and coins along the way.

> **Note:** The medication feature is for gameplay purposes only and does not provide real veterinary or medical advice.

## Features

- **Pet Profile & Customization** – create a pet (name, type, age) and dress it up with hats and accessories
- **Food & Water Tracker** – record what and how much your pet eats and drinks, compared against daily targets
- **Medication Tracker** – add medications with dosage, doses per day, and treatment duration. Mark them as completed
- **Pet Health Status** – view health, hunger, hydration, and level
- **Daily Care Checklist** – see which care tasks are still pending for the day
- **Care History** – review previous food, water, and medication records
- **Mingwit Fishing Game** – reel in fish of different rarities to earn points and coins
- **Reward System** – spend coins in the shop on pet accessories
- **Save & Load** – progress is saved locally; no internet connection required

## Getting Started

Follow the instructions below to get a local copy of **Mingwit** up and running.

```bash
javac *.java
java Mingwit
```

## Java and OOP Concepts Used

| Concept | Where it is used |
|---|---|
| Classes and Objects | Classes such as Pet, Fish, Medication, FishingGame, and Accessory will represent the main components of the game. Objects will represent the actual pet, fish, medication records, and customization items. |
| Methods | Methods will handle actions such as eating, drinking, taking medication, checking health, fishing, catching fish, gaining experience, leveling up, and equipping accessories. |
| Encapsulation | Pet attributes such as health, hunger, hydration, happiness, energy, and level will be private and accessed or modified through appropriate methods. |
| Inheritance | Different fish types or pet-related items can inherit from a general class. |
| Polymorphism | Different fish or medications can have different values or difficulty levels by overriding methods from the Fish class. |
| Abstraction | A general Fish class can define common characteristics of fish while allowing specific fish classes to implement their own behavior. |
| Exception Handling | The program will handle invalid inputs such as negative food or water amounts, invalid medication information, invalid menu selections, and invalid game actions. |
| Arrays / Data Handling | Arrays or Java collections can be used to store fish collections, pet-care records, available accessories, and other game data. |


## Class Diagram

![MINGWIT UML Class Diagram](UML%20Diagram.png)

## Limitations

1. The game will be developed as a Java-based application and will not require an online   connection.
2. The game will only include the pet-care and fishing features planned for the project.
3. The medication feature is intended for gameplay purposes only and does not provide real veterinary or medical advice.
4. The available pets, fish, accessories, and items will be limited to those implemented by the development team.
5. The game will use predefined fish types, values, and rarities.
6. The project will not include online multiplayer functionality.
7. Player progress will be limited to the data storage system implemented by the team.
8. Advanced features such as real-time veterinary monitoring, online accounts, and cloud synchronization are outside the scope of the project.

## Team

- Luis Andrei C. Gequinto – Project Leader, Lead Programmer
- Yasmeen E. Ricafrente – Programmer, Documentation
- Greisa Gwyneth A. Salamo - Programmer, System / Design Lead
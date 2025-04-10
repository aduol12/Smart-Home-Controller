#Smart Home Controller – A Java Design Pattern Showcase
This project is a Java-based Smart Home Controller system developed as part of my Software Engineering course (CS 415). It demonstrates the implementation of multiple object-oriented design patterns to simulate a real-world smart home environment where users can control various devices like lights, air conditioners, and doors.

 Features
Control smart devices individually or by room (turn on/off).

Add features like motion detection and energy-saving mode to devices using the Decorator Pattern.

Maintain a log of all device actions (e.g., “Living Room Light turned ON”) via the Observer Pattern.

Dynamically create different smart devices using the Factory Pattern.

Centralized control through a Singleton SmartHomeController class.

Automation support for future extension (e.g., Night Mode, Vacation Mode).

 Design Patterns Used
Decorator Pattern – Dynamically enhance device behavior at runtime.

Observer Pattern – Log device actions upon state change.

Factory Pattern – Simplify and standardize device creation.

Singleton Pattern – Ensure a single shared controller instance.

 Project Structure
Device interface and concrete implementations (Light, AC, Door)

DeviceDecorator for adding runtime features

DeviceFactory for device instantiation

SmartHomeController as the Singleton control hub

Logger as an Observer for device activity

 Technologies
Java (JDK 17+)

Object-Oriented Programming (OOP)

UML Design Principles


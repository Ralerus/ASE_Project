# Software Requirements Specification

## 1. Introduction

### 1.1 Purpose

This Software Requirements Specification is a description of the *unitasks web application* - hereinafter referred to as "the application", "the program", "the software", "the platform" or "it" - that is being developed by the *unitasks Team*.

This document clearly states all functional and non-functional requirements that need to be fulfilled by the program. Furthermore, the SRS gives an overview of the usability, reliability, application design, and the defined standards and use cases. This specification shall minimize the risk of failure and eliminate misunderstandings between the customer and the developers of this software.

### 1.2 Scope

The SRS applies to the entire *unitasks* project. The *unitasks* platform will be a place in which you can track your study related task’s progress, upload solutions and exchange with other students.

#### 1.2.1 Actors

There are one type of actors: **Player**

- Player: Person, die Anwendung benutzt.  

#### 1.2.2 Subsystems

| **Subsystem** | **Description** |
| --- | --- |
| Wettkampfbereich | Festlegung der Wettkampfkonfiguration, Starten und Durchführung des Wettkampfes |
| Trainingsbereich | Festlegung der Trainingskonfiguration, Starten und Durchführung des Wettkampfes |
| Statistikbereich | Globaler HighScore, Statistiken für Wettkämpfe und Trainingsrunden des Spielers |
| Einstellungen | Nutzerverwaltung (Passwort, Nutzername), Gruppenverwaltung, neue Texte hinzufügen |

## 2. Overall Description

### 2.1 Vision

### 2.2 Use Case Diagram

![Overall Use Case Diagram](/requirements/img/OUCD.png)

## 3. Specific Requirements

### 3.1 Functionality

The following section points out all requirements and explains their functionality. Each of the subsections below represents a subsystem of the application.

#### 3.1.1 Wettkampfbereich

Jeder eingeloggte Nutzer kann im Wettkampfbereich einen Wettkampf konfigurieren, weitere existierende Nutzer per Nutzername zum Wettkampf hinzufügen und den Wettkampf anschließend für alle starten.  
Die Konfiguration umfasst die Schwierigkeit der Texte (Länge des Textes, Umgang mit Tippfehlern, etc.), anschließend wählt das System einen entsprechenden Text aus der Datenbank aus und der Wettkampf kann gestartet werden.
Die Spieler müssen nun den ausgewählten Text in zufälliger Reihenfolge so schnell wie möglich abtippen, ihre hierfür benötigte Zeit wird dabei bewusst verborgen. Ist ein Spieler fertig, muss sich der nächste am System anmelden und seine Runde spielen.  
Im Wettkampf läuft in einer Zeile dann der Text durch, bei jedem richtigen Zeichen, geht er ein Zeichen weiter. Bei falschen bleibt der Text stehen.

Haben alle Spieler den Text abgetippt erscheint am Ende das Ergebnis mit den Zeiten der einzelnen Spieler und das System ermittelt den Sieger.

#### 3.1.2 Trainingsbereich

Der Trainingsbereich funktioniert wie der Wettkampfbereich, lässt sich aber nur alleine durchführen. Außerdem gibt es für Trainingsläufe einen separate Statistik.

#### 3.1.3 Statistikbereich

Im Statistikbereich werden zum einen globale Statistiken wie die Anzahl an Wettkämpfen und Spielern im System sowie die besten Ergebnisse je Schwierigkeitsgrad angezeigt.  
Zum anderen gibt es auch spielerabhängige Statistiken wie die Ausgänge seiner letzten Wettkämpfe und Trainingsrunden. Auch persönliche Bestleistungen je Text werden angezeigt.

#### 3.1.4 Einstellungen

Im Bereich der Nutzerverwaltung kann der angemeldete Spieler seinen Benutzernamen und sein Passwort ändern.  
Außerdem lassen sich Gruppen aus Nutzern erstellen, um mehrere Nutzer auf einmal zu einem Wettkampf hinzuzufügen.  
Zudem können Nutzer eigene Texte zur Datenbank mit selbst klassifiziertem Schwierigkeitsgrad hinzufügen.  

# Dokumentation ASE-Projekt
Philipp Rall, TINF18B2, 5844601

---

## Projektidee
Meine Idee ist mit Java ein kleines, lokales Desktop-Spiel „Tippduell“ nach dem Vorbild von Typeracer zu entwickeln. D.h. das Spielziel besteht darin, einen vorgegebenen Text möglichst schnell korrekt abzutippen.
In einem Wettkampfbereich soll hierzu zunächst die Schwierigkeit vom Spielleiter festgelegt werden, bevor ein entsprechender Text aus der Datenbank geladen wird.
Anschließend tippt ein Spieler nach dem anderen den Text ab, der schnellste gewinnt die Runde.
Neben dem Wettkampfbereich ist außerdem ein Bereich zur Spielerverwaltung sowie ein HighScore bzw. Statistikbereich pro Spieler angedacht, die Statistiken sollen ebenfalls in der Datenbank gespeichert werden.
Auch ein Trainingsbereich für Spieler ist geplant.

Generell dient die geplante Anwendung dem übergeordneten Zweck die Tippgeschwindigkeit der Spieler auf kompetitive Art und Weise zu trainieren und zu verbessern.

## Programmierprinzipien
### SOLID
#### Single Responsibility Principle
Dieses Programmierprinzip sagt aus, dass jede Klasse nur eine Zuständigkeit, eine klar definierte Aufgabe besitzen soll.
Das ermöglicht niedrige Komplexität und Kopplung. So besitzt die Klasse `Session` lediglich die Zuständigkeit der Anmeldelogik, die durch die beiden Methoden
`login()` und `logoff` sowie das Attribut `loggedInPlayer` ausgedrückt wird.
#### Open Closed Principle
Das Open Closed Prinzip beschreibt, dass Klassen generell offen für Erweiterungen und geschlossen für Änderungen sein sollten.
Durch Abstraktionen kann die Erweiterbarkeit gefördert werden, sodass bei Erweiterungen der bestehende Code nicht geändert werden muss.
#### Liskov Substitution Principle
#### Interface Segregation Principle
#### Dependency Inversion Principle


### GRASP

### DRY
Installationsskript


## Entwurfsmuster
Als Entwurfsmuster wurde im Tippduell vor allem das **Observer-Pattern** bzw. **Listener-Pattern** angewandt. Es handelt sich hierbei um ein Verhaltensmuster, also einem 
Pattern zur Kommunikation zwischen Objekten und der Steuerung des Kontrollflusses einer Anwendung zur Laufzeit. Das Listener-Pattern ermöglicht eine automatische Reaktionen auf Zustandsänderungen und 
wird in der vorliegenden Anwendung für die Kommunikation zwischen Benutzeroberfläche und Applikationslogik eingesetzt.

Ein Interface wie beispielsweise `GameListener` gibt verschiedene Methoden vor, wie beispielsweise `startRoundFor(Player p)`, die die `Game`-Klasse implementiert.
Das `UserUI` bekommt über die Methode `setGameListener` dann eine Game-Instanz als privaten Member gesetzt, über den das `UserUI` bei Bedarf die erwähnte Methode aufrufen kann.
Die Klasse `Game` ist somit ein Observer, das `UserUI` ein Observable, das den Observer über die `startRoundFor(Player p)`-Methode benachrichtigen kann. Hierfür muss sich eine Instanz der Game-Klasse auf dem `UserUI` registrieren.


## Domain Driven Design

## Architektur
Das vorliegende Programm wurde in einer Schichtenarchitektur mit den drei Schichten *Presentation*, *Domain* und *Data* entwickelt.
Jede Schicht deckt dabei einen unterschiedlichen Aufgabenbereich ab:
- **Presentation**: Klassen zur Darstellung der Benutzeroberfläche, z.B. `GameUI`, `SettingsUI` oder `RoundUI`.
- **Domain**: Klassen für die eigentliche Spiellogik, steuern Spielfluss maßgeblich, z.B. `Game` oder `Round`.
- **Data**: Klassen für grundlegende Objekte der Anwendung, z.B. `Player`, `Text` oder `Stats` mit den dazugehörigen Repositories. Die Repositories wie z.B. das `PlayerRepository` beinhalten
  Code zum Verwalten der dazugehörigen Datenbankobjekte. In dieser Schicht werden alle Datenbankzugriffe realisiert.
Dabei gilt allgemein, dass jede Schicht nur von den unterliegenden Schichten abhängt und somit unabhängig von den überliegenden Schichten ist. Konkret bedeutet das, dass 
  beispielsweise die *Data*-Schicht unabhängig von den anderen beiden Schichten ist, die oberen beiden Schichten aber Methoden der *Data*-Schicht verwenden.

## Unit Tests

## Refactoring
Zur objektiven, metrikbasierten Identifikation von Code Smells wird für dieses Projekt das Analysetool *Codacy* eingesetzt. Anhand des Quality-Scores dort, kann der konkrete
Erfolg des Refactorings bewertet werden.

Ein Code Smell, nämlich eine Large Class mit Long Methods, kann in der `SettingsUI`-Klasse hierdurch identifiziert werden. Diese Klasse beinhaltet den UI-Code
für die Nutzerverwaltung und Textverwaltung. Allein durch diese Beschreibung wird klar, dass die Klasse nicht dem Single Responsiblitiy Prinzip folgt.
Deshalb soll in drei Schritten ein Refactoring durchgeführt werden.
1. Zunächst muss die Klasse `SettingsUI` in zwei Klassen `UserManagementUI` und `TextManagementUI` aufgeteilt werden. Diese Aufteilung ist
   in [diesem Commit](https://github.com/Ralerus/ASE_Project/commit/e33955c28d18d9d8b755d1edd9c3099472b45b3e) zu sehen. So kann sichergestellt werden, dass jede Klasse nur eine Aufgabe innehat.
2. Die weiteren Schritte sollen am Beispiel der neuen Klasse `UserManagementUI` aufgezeigt werden. Anschließend kann das Long Method-Problem angegangen werden und die lange Methoden `getUserManagementUI` in kleinere Methoden
   aufgeteilt werden, sodass jede Methode nur eine Aufgabe erfüllt. Dieser Zwischenstand ist in [diesem Commit](https://github.com/Ralerus/ASE_Project/commit/34447a0c0f96d04d599d24a7e31f3b98d9e2949b) sichtbar. 
   So konnte die Methode mit 149 Zeilen Code in neuen Methoden mit jeweils maximal 38 Zeilen Code aufgeteilt werden. Das steigert die Lesbarkeit und Wartbarkeit erheblich.
3. Allerdings ist das Ergebnis noch nicht zufriedenstellend, es fällt auf, dass Methoden wie `changeUsernameIfNotEmpty` eigentlich kein UI-Code, sondern Domain-Code sind. 
   Deshalb sollen diese Methoden in einer neuen Klasse `UserManagement` in der domain-Schicht zusammengefasst werden. Außerdem ist eine Umbenennung sinnvoll, da diese nach dem Refactoring lediglich einen Wahrheitswert zurückliefern,
   ob die Änderung erfolgreich war bzw. ob die Eingabefelder im UI geleert werden sollen. Die Methoden heißen deshalb z.B. `isUsernameChaned`. Siehe hierzu [diesen Commit](https://github.com/Ralerus/ASE_Project/commit/06659658d79ade7ebb4075a389f87117968bc6fe).



























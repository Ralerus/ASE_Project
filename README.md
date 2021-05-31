# Tippduell

Tippduell ist ein Spiel zum kompetitiven Training und Verbesserung der Tippgeschwindigkeit.
Die vollständige Dokumentation findet sich [hier](doc/Documentation.md).

## Installation
### Linux
Zur Installation auf Linux müssen folgende Schritte durchlaufen werden:
#### 1. Projekt clonen
```bash
git clone https://github.com/Ralerus/ASE_Project.git
```
Hierfür muss git installiert sein, ist dies noch nicht erfolgt:
```bash
sudo apt install git
```
#### 2. SQLite, JRE & Maven installieren
```bash
cd ~/ASE_Project
sh init.sh
```
#### 3. Applikation testen, compilieren und starten
```bash
sh start.sh
```
### Windows
Als Alternative zu Linux kann die Anwendung auch auf Windows ausgeführt werden. Dazu sind folgende Schritte notwendig:
#### 1. Windows-Executable herunterladen & entpacken
Dazu muss [dieser ZIP-komprimierter Ordner](executable/Tippduell.zip) auf das lokale Windows-Betriebssystem heruntergeladen und entpackt werden.
#### 2. Tippduell starten
Danach kann im Ordner `tippduell` die ausführbare Datei `Tippduell.exe` gestartet werden.

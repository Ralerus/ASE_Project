CREATE DATABASE tippduell;

CREATE TABLE IF NOT EXISTS player (username text PRIMARY KEY,password text NOT NULL,fullname text);

CREATE TABLE IF NOT EXISTS text (title text PRIMARY KEY,text text NOT NULL,difficulty integer NOT NULL,length integer NOT NULL);
                
CREATE TABLE IF NOT EXISTS game (id integer PRIMARY KEY,textTitle text NOT NULL,date text NOT NULL,
FOREIGN KEY (textTitle) REFERENCES text(title) ON UPDATE CASCADE ON DELETE SET NULL);

CREATE TABLE IF NOT EXISTS result (gameId integer NOT NULL,username text NOT NULL,duration real NOT NULL,
                FOREIGN KEY (gameId) REFERENCES game(id) ON UPDATE CASCADE ON DELETE CASCADE 
                FOREIGN KEY (username) REFERENCES player(username) ON UPDATE CASCADE ON DELETE CASCADE 
                PRIMARY KEY (gameId,username) );

CREATE TABLE IF NOT EXISTS training (id integer PRIMARY KEY,username text NOT NULL,duration real NOT NULL,textTitle text NOT NULL,date text NOT NULL,
                FOREIGN KEY (textTitle) REFERENCES text(title) ON UPDATE CASCADE ON DELETE SET NULL 
                FOREIGN KEY (username) REFERENCES player(username) ON UPDATE CASCADE ON DELETE CASCADE);
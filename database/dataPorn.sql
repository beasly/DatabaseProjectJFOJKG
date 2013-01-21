CREATE TABLE Buch(
ISBN varchar(14) not null primary key,
Preis float(2),
Titel varchar(100) not null);

CREATE TABLE Ausleiher(
AusleiherID serial primary key,
Vorname varchar(20),
Name varchar(20),
Email varchar(30));

CREATE TABLE Verlag(
VerlagsID serial primary key,
Name varchar(30) not null,
Ort varchar(25));

CREATE TABLE Regal(
RegalID serial primary key,
Ort varchar(30) not null);

CREATE TABLE Autoren(
AutorenID serial primary key,
Vorname varchar(30),
Name varchar(30) not null);

CREATE TABLE Schlagwort(
SchlagwortID serial primary key,
Schlagwort varchar(30) not null);

CREATE TABLE Genre(
GenreID serial primary key,
Genre varchar(30) not null);

CREATE TABLE veroeffentlichtVon(
Buch varchar(14) references Buch(ISBN) on delete cascade,
Verlag integer references Verlag(VerlagsID) on delete cascade,
primary key(Buch, Verlag));

CREATE TABLE ausgeliehenAn(
Buch varchar(14) references Buch(ISBN) on delete cascade,
Ausleiher integer references Ausleiher(AusleiherID) on delete cascade,
Leihdatum Date not null,
Rueckgabedatum Date,
primary key(Buch, Ausleiher));

CREATE TABLE geschriebenVon(
Buch varchar(14) references Buch(ISBN) on delete cascade,
Autoren integer references Autoren(AutorenID) on delete cascade,
primary key(Buch, Autoren));

CREATE TABLE liegtIn(
Buch varchar(14) references Buch(ISBN) on delete cascade,
Regal integer references Regal(RegalID) on delete cascade,
primary key(Buch, Regal)); 

CREATE TABLE hatSchlagwort(
Buch varchar(14) references Buch(ISBN) on delete cascade,
Schlagwort integer references Schlagwort(SchlagwortID) on delete cascade,
primary key(Buch, Schlagwort));

CREATE TABLE hatGenre(
Buch varchar(14) references Buch(ISBN) on delete cascade,
Genre integer references Genre(GenreID) on delete cascade,
primary key(Buch, Genre));

CREATE TABLE buch_changelog (
							change_id SERIAL primary key,
							ISBN varchar(14),
							preis_alt float(2),
							preis_neu float(2),
							titel_alt varchar(100),
							titel_neu varchar(100),
							mod_type char(1),
							mod_user text DEFAULT CURRENT_USER,
							mod_timestamp timestamp DEFAULT current_timestamp);
							
CREATE TABLE vanished_books (
							vanished_id SERIAL primary key,
							ISBN varchar(14),
							preis float(2),
							titel varchar(100),
							name varchar(100),
							vorname varchar(100),
							email varchar(100),
							timestamp timestamp DEFAULT current_timestamp);

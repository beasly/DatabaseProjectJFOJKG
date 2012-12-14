CREATE TABLE Buch(
BuchID integer primary key,
Preis float,
Titel varchar(40));

CREATE TABLE Ausleiher(
AusleiherID integer primary key not null,
Vorname varchar(20),
Name varchar(20),
Email varchar(30));

CREATE TABLE Verlag(
VerlagsID integer primary key,
Name varchar(20),
Ort varchar(15));

CREATE TABLE Regal(
RegalID integer primary key,
Ort varchar(15));

CREATE TABLE Autoren(
AutorenID integer primary key,
Vorname varchar(20),
Name varchar(20));

CREATE TABLE Schlagwort(
SchlagwortID integer primary key,
Schlagwort varchar(20),
Genre varchar(20),
Hauptcharakter varchar(20));

CREATE TABLE veroeffentlichtVon(
Buch integer references Buch(BuchID) on delete cascade,
Verlag integer references Verlag(VerlagsID) on delete cascade,
Datum Date,
primary key(Buch, Verlag));

CREATE TABLE ausgeliehenAn(
Buch integer references Buch(BuchID) on delete cascade,
Ausleiher integer references Ausleiher(AusleiherID) on delete cascade,
Leihdatum Date,
Rueckgabedatum Date,
primary key(Buch, Ausleiher));

CREATE TABLE geschriebenVon(
Buch integer references Buch(BuchID) on delete cascade,
Autoren integer references Autoren(AutorenID) on delete cascade,
Vorname varchar(20),
Name varchar(20),
primary key(Buch, Autoren));

CREATE TABLE liegtIn(
Buch integer references Buch(BuchID) on delete cascade,
Regal integer references Regal(RegalID) on delete cascade,
Fach integer,
primary key(Buch, Regal)); 

CREATE TABLE hat(
Buch integer references Buch(BuchID) on delete cascade,
Schlagwort integer references Schlagwort(SchlagwortID) on delete cascade,
primary key(Buch, Schlagwort));
CREATE TABLE Buch(
ISBN varchar(17) not null primary key,
Preis float,
Titel varchar(40) not null);

CREATE TABLE Ausleiher(
AusleiherID integer primary key not null,
Vorname varchar(20),
Name varchar(20),
Email varchar(30));

CREATE TABLE Verlag(
VerlagsID integer primary key not null,
Name varchar(20) not null,
Ort varchar(15));

CREATE TABLE Regal(
RegalID integer primary key not null,
Ort varchar(15) not null);

CREATE TABLE Autoren(
AutorenID integer primary key not null,
Vorname varchar(20),
Name varchar(20) not null);

CREATE TABLE Schlagwort(
SchlagwortID integer primary key not null,
Schlagwort varchar(20) not null,
Genre varchar(20),
Hauptcharakter varchar(20));

CREATE TABLE veroeffentlichtVon(
Buch varchar(17) references Buch(ISBN) on delete cascade,
Verlag integer references Verlag(VerlagsID) on delete cascade,
Datum Date,
primary key(Buch, Verlag));

CREATE TABLE ausgeliehenAn(
Buch varchar(17) references Buch(ISBN) on delete cascade,
Ausleiher integer references Ausleiher(AusleiherID) on delete cascade,
Leihdatum Date not null,
Rueckgabedatum Date,
primary key(Buch, Ausleiher));

CREATE TABLE geschriebenVon(
Buch varchar(17) references Buch(ISBN) on delete cascade,
Autoren integer references Autoren(AutorenID) on delete cascade,
Vorname varchar(20),
Name varchar(20),
primary key(Buch, Autoren));

CREATE TABLE liegtIn(
Buch varchar(17) references Buch(ISBN) on delete cascade,
Regal integer references Regal(RegalID) on delete cascade,
Fach integer,
primary key(Buch, Regal)); 

CREATE TABLE hat(
Buch varchar(17) references Buch(ISBN) on delete cascade,
Schlagwort integer references Schlagwort(SchlagwortID) on delete cascade,
primary key(Buch, Schlagwort));
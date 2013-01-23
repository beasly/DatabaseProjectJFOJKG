INSERT INTO Buch VALUES ('978-3897211971', 37.00, 'SQL in a nutshell');
INSERT INTO Buch VALUES ('978-3897213326', 37.00, 'Java in a nutshell');
INSERT INTO Buch VALUES ('978-0596006976', 34.42, 'C in a nutshell');
INSERT INTO Buch VALUES ('978-3827418241', 15.95, 'Theoretische Informatik - kurz gefasst');
INSERT INTO Buch VALUES ('978-3423214124', 7.95, 'Der kleien Hobbit');

INSERT INTO Autoren VALUES (DEFAULT, 'David', 'Flanagan');
INSERT INTO Autoren VALUES (DEFAULT, 'Peter', 'Prinz');
INSERT INTO Autoren VALUES (DEFAULT, 'Uwe' , 'Schoening');
INSERT INTO Autoren VALUES (DEFAULT, 'Kevin' , 'Kline');
INSERT INTO Autoren VALUES (DEFAULT, 'Daniel' , 'Kline');
INSERT INTO Autoren VALUES (DEFAULT, 'Brand' , 'Hunt');
INSERT INTO Autoren VALUES (DEFAULT, 'J.R.R.', 'Tolkien');

INSERT INTO geschriebenvon VALUES ('978-3897213326', 1);
INSERT INTO geschriebenvon VALUES ('978-0596006976', 2);
INSERT INTO geschriebenvon VALUES ('978-3827418241', 3);
INSERT INTO geschriebenvon VALUES ('978-3897211971', 4);
INSERT INTO geschriebenvon VALUES ('978-3897211971', 5);
INSERT INTO geschriebenvon VALUES ('978-3897211971', 6);
INSERT INTO geschriebenvon VALUES ('978-3423214124', 7);

INSERT INTO Verlag VALUES (DEFAULT,'OReilly', 'Koeln');
INSERT INTO Verlag VALUES (DEFAULT, 'Deutscher Taschenbuch Verlag', 'Berlin');
INSERT INTO Verlag VALUES (DEFAULT, 'Spektrum Akademischer Verlag', 'Heidelberg');

INSERT INTO veroeffentlichtvon VALUES ('978-3897211971',1,'01/01/01');
INSERT INTO veroeffentlichtvon VALUES ('978-3897213326',1,'01/01/01');
INSERT INTO veroeffentlichtvon VALUES ('978-0596006976',1,'01/01/01');
INSERT INTO veroeffentlichtvon VALUES ('978-3423214124',2,'01/10/01');
INSERT INTO veroeffentlichtvon VALUES ('978-3827418241',3,'01/05/08');

INSERT INTO Regal VALUES (DEFAULT, 'WohnzimmerLinks');
INSERT INTO Regal VALUES (DEFAULT, 'WohnzimmerRechts');
INSERT INTO Regal VALUES (DEFAULT, 'Schlafzimmer');
INSERT INTO Regal VALUES (DEFAULT, 'Bibliothek Regal A');
INSERT INTO Regal VALUES (DEFAULT, 'Bibliothek Regal B');

INSERT INTO liegtin VALUES ('978-3897211971',4);
INSERT INTO liegtin VALUES ('978-3897213326',4);
INSERT INTO liegtin VALUES ('978-0596006976',4);
INSERT INTO liegtin VALUES ('978-3827418241',5);
INSERT INTO liegtin VALUES ('978-3423214124',4);

INSERT INTO genre VALUES ( DEFAULT, 'Fahliteratur' );
INSERT INTO genre VALUES ( DEFAULT, 'Roman');
INSERT INTO genre VALUES ( DEFAULT, 'Sci-Fi');
INSERT INTO genre VALUES ( DEFAULT, '1337-teratur');

INSERT INTO hatgenre VALUES ('978-3897211971', 4);
INSERT INTO hatgenre VALUES ('978-3897213326', 1);
INSERT INTO hatgenre VALUES ('978-0596006976', 1);
INSERT INTO hatgenre VALUES ('978-3827418241', 3);
INSERT INTO hatgenre VALUES ('978-3423214124', 2);

INSERT INTO Schlagwort VALUES (DEFAULT, 'Programmieren');
INSERT INTO Schlagwort VALUES (DEFAULT, 'Hobbit' );
INSERT INTO Schlagwort VALUES (DEFAULT, 'Informatik');
INSERT INTO Schlagwort VALUES (DEFAULT, 'Datenbank');

INSERT INTO hatSchlagwort VALUES ('978-3897211971', 4);
INSERT INTO hatSchlagwort VALUES ('978-3897213326', 1);
INSERT INTO hatSchlagwort VALUES ('978-0596006976', 1);
INSERT INTO hatSchlagwort VALUES ('978-3827418241', 3);
INSERT INTO hatSchlagwort VALUES ('978-3423214124', 2);

INSERT INTO Ausleiher VALUES (DEFAULT, 'Julianus muhammad', 'Finkus', 'jufikus@hamas.ir');
INSERT INTO Ausleiher VALUES (DEFAULT, 'Ozzy', 'Ozkerus', 'ozky@hotmail.sz');
INSERT INTO Ausleiher VALUES (DEFAULT, 'Nicht', 'verliehen', ' ');

INSERT INTO ausgeliehenan VALUES ('978-3423214124',2, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-3897211971',3, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-3897213326',3, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-3827418241',3, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-0596006976',3, '04/08/12');

GRANT ALL PRIVILEGES ON TABLE buch TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE ausgeliehenan TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE ausleiher TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE autoren TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE genre TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE geschriebenvon TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE hatgenre TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE hatschlagwort TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE liegtin TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE regal TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE schlagwort TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE verlag TO _s0538977__buechersammlung_generic;
GRANT ALL PRIVILEGES ON TABLE veroeffentlichtvon TO _s0538977__buechersammlung_generic;
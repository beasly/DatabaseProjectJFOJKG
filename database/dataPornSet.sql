INSERT INTO Buch VALUES ('978-3897211971', 37.00, 'SQL in a nutshell');
INSERT INTO Buch VALUES ('978-3897213326', 37.00, 'Java in a nutshell');
INSERT INTO Buch VALUES ('978-0596006976', 34.42, 'C in a nutshell');
INSERT INTO Buch VALUES ('978-3827418241', 15.95, 'Theoretische Informatik - kurz gefasst');
INSERT INTO Buch VALUES ('978-3423214124', 7.95, 'Der kleien Hobbit');

INSERT INTO Autoren VALUES (1, 'David', 'Flanagan');
INSERT INTO Autoren VALUES (2, 'Peter', 'Prinz');
INSERT INTO Autoren VALUES (3, 'Uwe' , 'Schoening');
INSERT INTO Autoren VALUES (4, 'Kevin' , 'Kline');
INSERT INTO Autoren VALUES (5, 'Daniel' , 'Kline');
INSERT INTO Autoren VALUES (6, 'Brand' , 'Hunt');
INSERT INTO Autoren VALUES (7, 'J.R.R.', 'Tolkien');

INSERT INTO geschriebenvon VALUES ('978-3897213326', 1);
INSERT INTO geschriebenvon VALUES ('978-0596006976', 2);
INSERT INTO geschriebenvon VALUES ('978-3827418241', 3);
INSERT INTO geschriebenvon VALUES ('978-3897211971', 4);
INSERT INTO geschriebenvon VALUES ('978-3897211971', 5);
INSERT INTO geschriebenvon VALUES ('978-3897211971', 6);
INSERT INTO geschriebenvon VALUES ('978-3423214124', 7);

INSERT INTO Verlag VALUES (1,'OReilly', 'Koeln');
INSERT INTO Verlag VALUES (2, 'Deutscher Taschenbuch Verlag', 'Berlin');
INSERT INTO Verlag VALUES (3, 'Spektrum Akademischer Verlag', 'Heidelberg');

INSERT INTO veroeffentlichtvon VALUES ('978-3897211971',1,'01/01/01');
INSERT INTO veroeffentlichtvon VALUES ('978-3897213326',1,'01/01/01');
INSERT INTO veroeffentlichtvon VALUES ('978-0596006976',1,'01/01/01');
INSERT INTO veroeffentlichtvon VALUES ('978-3423214124',2,'01/10/01');
INSERT INTO veroeffentlichtvon VALUES ('978-3827418241',3,'01/05/08'); 

INSERT INTO Regal VALUES (1, 'WohnzimmerLinks');
INSERT INTO Regal VALUES (2, 'WohnzimmerRechts');
INSERT INTO Regal VALUES (3, 'Schlafzimmer');
INSERT INTO Regal VALUES (4, 'Bibliothek Regal A');
INSERT INTO Regal VALUES (5, 'Bibliothek Regal B');

INSERT INTO liegtin VALUES ('978-3897211971',4);
INSERT INTO liegtin VALUES ('978-3897213326',4);
INSERT INTO liegtin VALUES ('978-0596006976',4);
INSERT INTO liegtin VALUES ('978-3827418241',5);

INSERT INTO Schlagwort VALUES (1, 'Programmieren', 'Fachliteratur');
INSERT INTO Schlagwort VALUES (2, 'Hobbit', 'Roman', 'Bilbo Beutlin');
INSERT INTO Schlagwort VALUES (3, 'Informatik', 'Fachliteratur');
INSERT INTO Schlagwort VALUES (4, 'Datenbank', 'Fachliteratur');

INSERT INTO hat VALUES ('978-3897211971', 4);
INSERT INTO hat VALUES ('978-3897213326', 1);
INSERT INTO hat VALUES ('978-0596006976', 1);
INSERT INTO hat VALUES ('978-3827418241', 3);
INSERT INTO hat VALUES ('978-3423214124', 2);

INSERT INTO Ausleiher VALUES (1, 'Julianus', 'Finkus', 'jufikus@hotmail.sz');
INSERT INTO Ausleiher VALUES (2, 'Ozzy', 'Ozkerus', 'ozky@hotmail.sz');

INSERT INTO ausgeliehenan VALUES ('978-3423214124',2, '04/08/12');




INSERT INTO Buch VALUES ('978-3897211971', 37.00, 'SQL in a nutshell');
INSERT INTO Buch VALUES ('978-3897213326', 37.00, 'Java in a nutshell');
INSERT INTO Buch VALUES ('978-0596006976', 34.42, 'C in a nutshell');
INSERT INTO Buch VALUES ('978-3827418241', 15.95, 'Theoretische Informatik - kurz gefasst');
INSERT INTO Buch VALUES ('978-3423214124', 7.95, 'Der kleine Hobbit');
INSERT INTO Buch VALUES ('978-3050030463', 19.99, 'Negative Dialektik');
INSERT INTO Buch VALUES ('978-3746626734', 16.99, 'Krieg und Frieden');
INSERT INTO Buch VALUES ('978-3746626734', 16.99, 'Krieg und Frieden');
INSERT INTO Buch VALUES ('978-3438012609', 9.99, 'Die Bibel');
INSERT INTO Buch VALUES ('978-3880599925', 20.99, 'Das Kapital');


INSERT INTO Autoren VALUES (DEFAULT, 'David', 'Flanagan');
INSERT INTO Autoren VALUES (DEFAULT, 'Peter', 'Prinz');
INSERT INTO Autoren VALUES (DEFAULT, 'Uwe' , 'Schoening');
INSERT INTO Autoren VALUES (DEFAULT, 'Kevin' , 'Kline');
INSERT INTO Autoren VALUES (DEFAULT, 'Daniel' , 'Kline');
INSERT INTO Autoren VALUES (DEFAULT, 'Brand' , 'Hunt');
INSERT INTO Autoren VALUES (DEFAULT, 'J.R.R.', 'Tolkien');
INSERT INTO Autoren VALUES (DEFAULT, 'Theodor W.', 'Adorno');
INSERT INTO Autoren VALUES (DEFAULT, 'Lew', 'Tolstoi');
INSERT INTO Autoren VALUES (DEFAULT, 'Martin', 'Luther');
INSERT INTO Autoren VALUES (DEFAULT, 'Karl', 'Marx');



INSERT INTO geschriebenvon VALUES ('978-3897213326', 1);
INSERT INTO geschriebenvon VALUES ('978-0596006976', 2);
INSERT INTO geschriebenvon VALUES ('978-3827418241', 3);
INSERT INTO geschriebenvon VALUES ('978-3897211971', 4);
INSERT INTO geschriebenvon VALUES ('978-3897211971', 5);
INSERT INTO geschriebenvon VALUES ('978-3897211971', 6);
INSERT INTO geschriebenvon VALUES ('978-3423214124', 7);
INSERT INTO geschriebenvon VALUES ('978-3050030463', 8);
INSERT INTO geschriebenvon VALUES ('978-3746626734', 9);
INSERT INTO geschriebenvon VALUES ('978-3438012609', 10);
INSERT INTO geschriebenvon VALUES ('978-3880599925', 11);




INSERT INTO Verlag VALUES (DEFAULT, 'OReilly', 'Koeln');
INSERT INTO Verlag VALUES (DEFAULT, 'Deutscher Taschenbuch Verlag', 'Berlin');
INSERT INTO Verlag VALUES (DEFAULT, 'Spektrum Akademischer Verlag', 'Heidelberg');
INSERT INTO Verlag VALUES (DEFAULT, 'Akademischer Verlag', 'Berlin');
INSERT INTO Verlag VALUES (DEFAULT, 'Aufbau Taschenbuch Verlag', 'Berlin');
INSERT INTO Verlag VALUES (DEFAULT, 'Deutsche Bibelgesellschaft', 'Stuttgart');
INSERT INTO Verlag VALUES (DEFAULT, 'Parkland Verlag', 'Koeln');




INSERT INTO veroeffentlichtvon VALUES ('978-3897211971', 1);
INSERT INTO veroeffentlichtvon VALUES ('978-3897213326', 1);
INSERT INTO veroeffentlichtvon VALUES ('978-0596006976', 1);
INSERT INTO veroeffentlichtvon VALUES ('978-3423214124', 2);
INSERT INTO veroeffentlichtvon VALUES ('978-3827418241', 3);
INSERT INTO veroeffentlichtvon VALUES ('978-3050030463', 4);
INSERT INTO veroeffentlichtvon VALUES ('978-3746626734', 5);
INSERT INTO veroeffentlichtvon VALUES ('978-3438012609', 6);
INSERT INTO veroeffentlichtvon VALUES ('978-3880599925', 7);





INSERT INTO Regal VALUES (DEFAULT, 'WohnzimmerLinks');
INSERT INTO Regal VALUES (DEFAULT, 'WohnzimmerRechts');
INSERT INTO Regal VALUES (DEFAULT, 'Schlafzimmer');
INSERT INTO Regal VALUES (DEFAULT, 'Bibliothek Regal A');
INSERT INTO Regal VALUES (DEFAULT, 'Bibliothek Regal B');
INSERT INTO Regal VALUES (DEFAULT, 'Klo');





INSERT INTO liegtin VALUES ('978-3897211971', 4);
INSERT INTO liegtin VALUES ('978-3897213326', 4);
INSERT INTO liegtin VALUES ('978-0596006976', 4);
INSERT INTO liegtin VALUES ('978-3827418241', 5);
INSERT INTO liegtin VALUES ('978-3423214124', 4);
INSERT INTO liegtin VALUES ('978-3050030463', 1);
INSERT INTO liegtin VALUES ('978-3746626734', 3);
INSERT INTO liegtin VALUES ('978-3438012609', 6);
INSERT INTO liegtin VALUES ('978-3880599925', 4);


INSERT INTO genre VALUES ( DEFAULT, 'Fachliteratur' );
INSERT INTO genre VALUES ( DEFAULT, 'Roman');
INSERT INTO genre VALUES ( DEFAULT, 'Sci-Fi');
INSERT INTO genre VALUES ( DEFAULT, '1337-teratur');
INSERT INTO genre VALUES ( DEFAULT, 'Philosophie');
INSERT INTO genre VALUES ( DEFAULT, 'Religion');


INSERT INTO hatgenre VALUES ('978-3897211971', 4);
INSERT INTO hatgenre VALUES ('978-3897213326', 1);
INSERT INTO hatgenre VALUES ('978-0596006976', 1);
INSERT INTO hatgenre VALUES ('978-3827418241', 3);
INSERT INTO hatgenre VALUES ('978-3423214124', 2);
INSERT INTO hatgenre VALUES ('978-3050030463', 5);
INSERT INTO hatgenre VALUES ('978-3746626734', 2);
INSERT INTO hatgenre VALUES ('978-3438012609', 6);
INSERT INTO hatgenre VALUES ('978-3880599925', 5);






INSERT INTO Schlagwort VALUES (DEFAULT, 'Programmieren');
INSERT INTO Schlagwort VALUES (DEFAULT, 'Hobbit' );
INSERT INTO Schlagwort VALUES (DEFAULT, 'Informatik');
INSERT INTO Schlagwort VALUES (DEFAULT, 'Datenbank');
INSERT INTO Schlagwort VALUES (DEFAULT, 'Aufklärung');
INSERT INTO Schlagwort VALUES (DEFAULT, 'Russland');
INSERT INTO Schlagwort VALUES (DEFAULT, 'Christentum');
INSERT INTO Schlagwort VALUES (DEFAULT, 'Kapitalismuskritik');


INSERT INTO hatSchlagwort VALUES ('978-3897211971', 4);
INSERT INTO hatSchlagwort VALUES ('978-3897213326', 1);
INSERT INTO hatSchlagwort VALUES ('978-0596006976', 1);
INSERT INTO hatSchlagwort VALUES ('978-3827418241', 3);
INSERT INTO hatSchlagwort VALUES ('978-3423214124', 2);
INSERT INTO hatSchlagwort VALUES ('978-3050030463', 5);
INSERT INTO hatSchlagwort VALUES ('978-3746626734', 6);
INSERT INTO hatSchlagwort VALUES ('978-3438012609', 7);
INSERT INTO hatSchlagwort VALUES ('978-3880599925', 8);






INSERT INTO Ausleiher VALUES (DEFAULT, 'Lucky', 'Luke', 'lucky@luke.com);
INSERT INTO Ausleiher VALUES (DEFAULT, 'Peter', 'Parker', 'spiderman@net.com');
INSERT INTO Ausleiher VALUES (DEFAULT, 'Nicht', 'verliehen', ' ');
INSERT INTO Ausleiher VALUES (DEFAULT, 'Bruce', 'Wayne', 'batman@cave.com');
INSERT INTO Ausleiher VALUES (DEFAULT, 'Bibi', 'Blocksberg', 'kartoffelbrei@bes.en');

INSERT INTO ausgeliehenan VALUES ('978-3423214124',2, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-3897211971',3, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-3897213326',3, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-3827418241',3, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-0596006976',3, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-3050030463',3, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-3746626734',3, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-3438012609',3, '04/08/12');
INSERT INTO ausgeliehenan VALUES ('978-3880599925',3, '04/08/12');

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
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

CREATE TRIGGER rueckgabe_trigger AFTER UPDATE OF rueckgabedatum ON
ausgeliehenan FOR EACH ROW EXECUTE PROCEDURE update_rueckgabe();

CREATE OR REPLACE FUNCTION update_rueckgabe() RETURNS trigger AS $$
BEGIN
Delete from ausgeliehenan where buch = old.buch;
insert into ausgeliehenan (buch, ausleiher, leihdatum) values (old.buch, 3, to_date(to_char(current_date,'dd/mm/yyyy'), 'dd/mm/yyyy'));
RETURN NEW;
END;
$$ LANGUAGE plpgsql;



CREATE RULE buch_log_update AS ON update TO buch
DO
INSERT INTO buch_changelog (change_id, ISBN, preis_alt, preis_neu, titel_alt, titel_neu, mod_type) values
													 (default, old.isbn, old.preis, new.preis, old.titel, new.titel, 'U');

CREATE RULE buch_log_insert AS ON INSERT TO buch
DO
INSERT INTO buch_changelog (change_id, ISBN, preis_alt, preis_neu, titel_alt, titel_neu, mod_type) values
													 (default, new.isbn, new.preis, new.preis, 'N.A.', new.titel, 'I');

CREATE RULE buch_log_delete AS ON DELETE TO buch
DO
INSERT INTO buch_changelog (change_id, ISBN, preis_alt, preis_neu, titel_alt, titel_neu, mod_type) values
													 (default, old.isbn, old.preis, old.preis, old.titel, 'N.A.', 'D');

CREATE TABLE vanished_books (
							vanished_id SERIAL,
							ISBN varchar(14),
							preis float(2),
							titel varchar(100),
							name varchar(100),
							vorname varchar(100),
							email varchar(100),
							timestamp timestamp DEFAULT current_timestamp);

CREATE OR REPLACE function deleteLostBooks()
RETURNS VOID AS $$
DECLARE
	curs CURSOR IS select ausgeliehenan.buch, ausgeliehenan.ausleiher, ausgeliehenan.leihdatum, buch.preis, buch.titel, ausleiher.name, ausleiher.vorname, ausleiher.email from buch, ausleiher, ausgeliehenan where ausgeliehenan.buch = buch.isbn AND ausgeliehenan.ausleiher = ausleiher.ausleiherid AND ausleiher.ausleiherid != 3;
	rec RECORD;
BEGIN
	OPEN curs;
		LOOP
			FETCH NEXT FROM CURS INTO REC;
			IF NOT FOUND THEN EXIT;
			END IF;

			IF ( date_part('day', current_date::timestamp - rec.leihdatum::timestamp) > 365) THEN
			INSERT into vanished_books values (default, rec.buch, rec.preis, rec.titel, rec.name, rec.vorname, rec.email);
			Delete from buch where isbn = rec.buch;
			END IF;
		END LOOP;
	CLOSE curs;
	END;
	$$ LANGUAGE 'plpgsql';





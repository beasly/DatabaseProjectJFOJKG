CREATE OR REPLACE function deleteInactiveLender()
RETURNS VOID AS $$
DECLARE
    lender INTEGER;
BEGIN
    FOR lender IN SELECT ausleiherid FROM ausleiher
   LOOP
        IF lender NOT IN (SELECT ausleiher FROM ausgeliehenan) THEN
            DELETE FROM ausleiher WHERE lender = ausleiherid;
        END IF;
   END LOOP;

END;
$$ LANGUAGE 'plpgsql';



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
				INSERT INTO vanished_books VALUES (default, rec.buch, rec.preis, rec.titel, rec.name, rec.vorname, rec.email);
				DELETE FROM buch WHERE isbn = rec.buch;
			END IF;
		END LOOP;
	CLOSE curs;
	END;
	$$ LANGUAGE 'plpgsql';


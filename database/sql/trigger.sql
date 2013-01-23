CREATE TRIGGER rueckgabe_trigger 
	AFTER UPDATE OF rueckgabedatum 
	ON ausgeliehenan 
	FOR EACH ROW 
	EXECUTE PROCEDURE update_rueckgabe();

CREATE OR REPLACE FUNCTION update_rueckgabe() RETURNS trigger AS $$
BEGIN
DELETE FROM ausgeliehenan WHERE buch = OLD.buch;
INSERT INTO ausgeliehenan (buch, ausleiher, leihdatum) VALUES (OLD.buch, 3, to_date(to_char(CURRENT_DATE,'dd/mm/yyyy'), 'dd/mm/yyyy'));
RETURN NEW;
END;
$$ LANGUAGE plpgsql;





CREATE TRIGGER init_ausleiher
  AFTER INSERT ON buch
	FOR EACH ROW
	EXECUTE PROCEDURE init_ausleiher();

CREATE OR REPLACE FUNCTION init_ausleiher()
	RETURNS trigger AS $$
	BEGIN
		INSERT INTO ausgeliehenan VALUES ( NEW.isbn, 3, CURRENT_DATE );
		RAISE NOTICE 'isbn = %', new.isbn;
		RETURN new;
	END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION init_ausleiher()
	RETURNS trigger AS $$
	BEGIN
		INSERT INTO ausgeliehenan VALUES ( NEW.isbn, 3, CURRENT_DATE );
		RAISE NOTICE 'isbn = %', new.isbn;
		RETURN new;
	END;
$$ language plpgsql;


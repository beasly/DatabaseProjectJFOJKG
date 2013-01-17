REINDEX DATABASE _s0538977__buechersammlung;



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
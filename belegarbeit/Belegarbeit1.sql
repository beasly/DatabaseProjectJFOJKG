1a)
CREATE VIEW Reisestatistik AS 
(SELECT m.mID, m.Name, count(mID) as AnzahlderReisen FROM Mitglieder as  m, nimmtteil as nt 
WHERE m.mID = nt.Mitglied AND m.mid NOT IN (SELECT Mitglied FROM 
hatkonsumiert as h) GROUP BY m.mid, m.name HAVING count(mid) > 5);

1b)
CREATE VIEW MitgliederReisen AS
(SELECT m.mid, m.name, r.rid, r.ziel, r.hotel FROM Mitglieder as m, Reisen as r, nimmtteil as nt
WHERE m.mid = nt.Mitglied AND r.rid = nt.reise);

1c)
CREATE RULE _insertMitgliederReisen AS ON INSERT TO MitgliederReisen INSTEAD
(INSERT INTO Mitglieder( 
<<<<<<< HEAD





2a)

ALTER TABLE hatkonsumiert ADD COLUMN gesamt numeric(5,2); 

2b)

CREATE OR REPLACE FUNCTION fill_amount() 
RETURNS INT AS $$
DECLARE
curs CURSOR IS SELECT * FROM hatkonsumiert as k, hatpreis as p WHERE k.gasthof=p.gasthof AND k.getraenk=p.getraenk for update ;
i INT:=0;
rec RECORD;
BEGIN 
OPEN curs;
LOOP
FETCH NEXT FROM curs into rec;
IF NOT FOUND THEN EXIT; 
END IF;
UPDATE hatkonsumiert SET gesamt= rec.anzahl * rec.preis WHERE CURRENT OF curs;
i:=i+1;
END LOOP;
CLOSE curs;
RETURN i;
END; 
$$ LANGUAGE 'plpgsql';
=======
sdfhbdasifhdsisudhfdsiuhfiudshfaiudsg Bla und keks
>>>>>>> c518ce9fc4a7dae05da154134c49f73359736ad4

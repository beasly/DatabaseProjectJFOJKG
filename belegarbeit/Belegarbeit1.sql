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
sdfhbdasifhdsisudhfdsiuhfiudshfaiudsg Bla und keks
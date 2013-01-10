CREATE OR REPLACE VIEW overview AS
SELECT buch.titel as buch, string_agg(autoren.name || ' ' || autoren.vorname, ', ') as autor, verlag.name as verlag, regal.ort as regal, genre.genre as genre, schlagwort.schlagwort as schlagwort, ausleiher.vorname ||  ' ' || ausleiher.name as ausleiher
from buch, autoren, geschriebenvon, verlag, veroeffentlichtvon, regal, liegtin, genre, hatgenre, schlagwort, hatschlagwort, ausleiher, ausgeliehenan 
where buch.isbn = geschriebenvon.buch and autoren.autorenid = geschriebenvon.autoren and
verlag.verlagsid = veroeffentlichtvon.verlag and buch.isbn = veroeffentlichtvon.buch and
regal.regalid = liegtin.regal and buch.isbn = liegtin.buch and
genre.genreid = hatgenre.genre and buch.isbn = hatgenre.buch and
schlagwort.schlagwortid = hatschlagwort.schlagwort and buch.isbn = hatschlagwort.buch and
buch.isbn = ausgeliehenan.buch and ausgeliehenan.ausleiher = ausleiher.ausleiherid 
group by buch.titel, verlag.name, regal.ort, genre.genre, schlagwort.schlagwort, ausleiher.name, ausleiher.vorname;

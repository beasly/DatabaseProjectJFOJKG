CREATE OR REPLACE RULE delete_genre AS ON DELETE TO genre
WHERE EXISTS (SELECT * FROM hatgenre hg WHERE OLD.genreid = hg.genre)
DO INSTEAD NOTHING;

CREATE OR REPLACE RULE delete_ausleiher AS ON DELETE TO ausleiher
WHERE EXISTS (SELECT * FROM ausgeliehenan aa WHERE OLD.ausleiherid = aa.ausleiher)
DO INSTEAD NOTHING;

CREATE OR REPLACE RULE autoren AS ON DELETE TO autoren
WHERE EXISTS (SELECT * FROM geschriebenvon gv WHERE OLD.autorenid = gv.autoren)
DO INSTEAD NOTHING;

CREATE OR REPLACE RULE delete_schlagwort AS ON DELETE TO schlagwort
WHERE EXISTS (SELECT * FROM hatschlagwort hs WHERE OLD.schlagwortid = hs.schlagwort)
DO INSTEAD NOTHING;

CREATE OR REPLACE RULE delete_verlag AS ON DELETE TO verlag
WHERE EXISTS (SELECT * FROM veroeffentlichtvon vv WHERE OLD.verlagsid = vv.verlag)
DO INSTEAD NOTHING;

CREATE OR REPLACE RULE delete_regal AS ON DELETE TO regal
WHERE EXISTS (SELECT * FROM liegtin li WHERE OLD.regalid = li.regal)
DO INSTEAD NOTHING;



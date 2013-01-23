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


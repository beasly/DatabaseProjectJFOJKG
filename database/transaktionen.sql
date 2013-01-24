db.disableAutoCommit();
			db.executeChanges("INSERT INTO buch (isbn, preis, titel) VALUES ('" + isbn + ("', ") + price + (", '") + title + ("')"));
			db.executeChanges("INSERT INTO hatgenre (buch, genre) VALUES ('" + isbn + ("', ") + genreid + (")"));
			db.executeChanges("INSERT INTO geschriebenvon (buch, autoren) VALUES ('" + isbn + ("', ") + autorenid + (")"));
			db.executeChanges("INSERT INTO veroeffentlichtvon (buch, verlag) VALUES ('" + isbn + "', " + verlagsid + ")");
			db.executeChanges("INSERT INTO liegtin (buch, regal) VALUES ('" + isbn + ("', ") + regalid + (")"));
			db.executeChanges("INSERT INTO hatschlagwort (buch, schlagwort) VALUES ('" + isbn + ("', ") + schlagwortid + (")"));
db.commitTransaction();
db.enableAutoCommit();
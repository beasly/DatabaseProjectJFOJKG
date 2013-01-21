create trigger init_ausleiher
  after insert on buch
	for each row
	execute procedure init_ausleiher();


-- create tables 
DROP TABLE IF EXISTS transaction_items;
  
CREATE TABLE transaction_items(
	row serial,
	id integer,
	item integer, 
	PRIMARY KEY(row)
);



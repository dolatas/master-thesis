-- create tables 
DROP TABLE IF EXISTS transaction_items;
  
CREATE TABLE transaction_items(
	row serial,
	id bigint,
	item bigint, 
	PRIMARY KEY(row)
);



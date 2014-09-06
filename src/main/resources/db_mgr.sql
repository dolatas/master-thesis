-- create tables 
DROP TABLE IF EXISTS transactions;
  
CREATE TABLE transactions(
	row serial,
	id bigint,
	item bigint, 
	PRIMARY KEY(row)
);



/* Drop all Tables if exist */
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS billing_info;

/* Create Tables if not exist */
CREATE TABLE IF NOT EXISTS users (
	id SERIAL,
	first_name VARCHAR,
	last_name VARCHAR,
	email VARCHAR NOT NULL UNIQUE,
	password CHAR(60) NOT NULL,
	account_type VARCHAR(7) NOT NULL,
	avatar_path VARCHAR,
	created_on TIMESTAMP,
	updated_on TIMESTAMP,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS billing_info (
	id SERIAL,
	user_id BIGINT NOT NULL,
	address_line_1 VARCHAR NOT NULL,
	address_line_2 VARCHAR,
	county VARCHAR(20) NOT NULL,
	eircode CHAR(8),
	card_full_name VARCHAR NOT NULL,
	card_number CHAR(19) NOT NULL,
	card_expiry DATE NOT NULL,
	card_cvc CHAR(3) NOT NULL,
    created_on TIMESTAMP,
	updated_on TIMESTAMP,
	PRIMARY KEY (id),
	CONSTRAINT elphie_users_db 
		FOREIGN KEY (user_id) 
			REFERENCES users(id)
);

/* Check Tables are created successfully */
SELECT * FROM users;
SELECT * FROM billing_info;
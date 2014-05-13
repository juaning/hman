CREATE TABLE Room (
	id integer not null generated always as identity (start with 1, increment by 1),
	hotel_id integer,
	room_type varchar(255),
	extra_bed boolean,
	extra_bed_price integer,
	num_double_bed integer,
	num_single_bed integer,
	price_per_night integer,
	status varchar(255),
	description varchar(255)
);

CREATE TABLE Room_Availability (
	id integer not null generated always as identity (start with 1, increment by 1),
	room_id integer not null,
	reservation_id integer not null,
	status varchar(255),
	date_start date,
	date_end date,
	PRIMARY KEY (id)
);

CREATE TABLE Hotel (
	id integer not null generated always as identity (start with 1, increment by 1),
	city_id integer,
	name varchar(255)
);

CREATE TABLE City (
	id integer not null generated always as identity (start with 1, increment by 1),
	name varchar(255)
);

CREATE TABLE Customer (
	id integer not null generated always as identity (start with 1, increment by 1),
	city_id integer,
	first_name varchar(255),
	last_name varchar(255),
	mobile_number varchar(255),
	email varchar(255)
);

CREATE TABLE Reservation (
	id integer not null generated always as identity (start with 1, increment by 1),
	customer_id integer,
	hotel_id integer,
	room_id integer,
	check_in date,
	check_out date
);

CREATE TABLE Hotel_User (
	id integer not null generated always as identity (start with 1, increment by 1),
	name varchar(255),
	password varchar(255),
	user_type_id integer,
	email varchar(255),
	created_at timestamp,
	last_login timestamp
);

CREATE TABLE User_Type (
	id integer not null generated always as identity (start with 1, increment by 1),
	name varchar(255)
);
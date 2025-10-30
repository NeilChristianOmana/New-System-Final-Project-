use incident_system;

create table non_fatal(
Incident_type varchar(100),
Select_type varchar(100),
recorded_time time default (current_time),
Date_of_Incident int default 0,
Location_or_Zone varchar(100),
Persons_involve varchar(100),
Persons_status varchar(100),
Description_of_incident varchar(100),
Date_recorded date default (current_date)
);

create table fatal(
Incident_type varchar(100),
Select_type varchar(100),
recorded_time time default (current_time),
Date_of_Incident int default 0,
Location_or_Zone varchar(100),
Persons_involve varchar(100),
Persons_status varchar(100),
Description_of_incident varchar(100),
Date_recorded date default (current_date)
);

create table account(
account_id INT auto_increment primary key,
username varchar(50) not null,
account_password varchar(50) not null,
created timestamp default current_timestamp
);

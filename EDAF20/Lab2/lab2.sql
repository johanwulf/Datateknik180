-- Enable the use of foreign_keys in SQLite, by default OFF!
-- Delete the tables if they exist. To be able to drop
-- them in arbitrary order, disable foreign key checks.
set foreign_key_checks = 0;

drop table if exists Users;
drop table if exists Ticket;
drop table if exists Screening;
drop table if exists Theatre;
drop table if exists Movie;

set foreign_key_checks = 1;

-- Create tables
create table Users (
    username    varchar(32) not null UNIQUE,
    name        varchar(32) not null,
    phoneNumber varchar(32) not null,
    address     varchar(32),
    primary key (username)
);

create table Movie (
    movieTitle  varchar(255),
    primary key (movieTitle)
);

create table Theatre (
    theatreName     varchar(32),
    availableSeats  int,
    primary key (theatreName)
);

create table Screening (
    screeningId     int not null AUTO_INCREMENT,
    availableSeats  int not null,
    date            date not null,
    movie           varchar(255) not null,
    theatre         varchar(32) not null,
    primary key(screeningId),
    foreign key (movie) references Movie(movieTitle),
    foreign key (theatre) references Theatre(theatreName),
    constraint perDay unique (movie, date)
);

create table Ticket (
    reservationNumber    int not null AUTO_INCREMENT,
    customer             varchar(32) not null,
    movie                varchar(255) not null,
    theatreName          varchar(32) not null,
    date                 date not null,
    screeningId          int,
    primary key (reservationNumber),
    foreign key (customer) references Users(username),
    foreign key (screeningId) references Screening(screeningId)
);

start transaction;

-- Insert values
insert into Movie values
('Shrek'),
('Shrek 1'),
('Shrek 2'),
('Shrek 3'),
('Shrek: The Fellowship of the Ring'),
('Shrek: The Two Towers'),
('Shrek: The Return of the King');

insert into Theatre values
('Kråkan', 20),
('Örnen', 15),
('Sparven', 25),
('Fiskmåsen', 1),
('Kajan', 5);

insert into Screening values
(0, 25, '2020-05-10', 'Shrek', 'Kråkan'),
(0, 25, '2020-05-11', 'Shrek', 'Kråkan'),
(0, 15, '2020-05-10', 'Shrek 1', 'Örnen');

insert into Users values
('kallekula', 'Kalle Karlsson', '911', null);

insert into Ticket values
(0, 'kallekula', 'Shrek 1', 'Örnen', '2020-05-10', null);
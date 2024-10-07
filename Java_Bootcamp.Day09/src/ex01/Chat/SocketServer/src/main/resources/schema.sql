create table users (
  id serial primary key,
  login varchar(250),
  password varchar(250)
);

create table messages (
  id serial primary key,
  author_id int not null,
  text text not null ,
  date timestamp default current_timestamp
);
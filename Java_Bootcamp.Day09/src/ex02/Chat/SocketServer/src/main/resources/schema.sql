create table users (
  id serial primary key,
  login varchar(250),
  password varchar(250),
  authorized boolean
);

create table chatrooms (
  id serial primary key,
  name varchar(250),
  owner_id int not null,
  foreign key (owner_id) references users(id) on delete cascade
);

create table messages (
  id serial primary key,
  author_id int not null,
  text text not null ,
  date timestamp default current_timestamp,
  chatroom_id int not null,
  foreign key (chatroom_id) references chatrooms(id) on delete cascade,
  foreign key (author_id) references users(id) on delete cascade
);
create table mindmap
(
    id varchar(1024) primary key
);

create table leaf
(
    path varchar(1024) primary key,
    text varchar(4096),
    mindmap_id varchar(1024),
    foreign key (mindmap_id) references mindmap(id)
);
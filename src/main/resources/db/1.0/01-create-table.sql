--liquibase formatted sql

--changeset magofrays:init

create table member(
    id uuid default gen_random_uuid() not null ,
    username varchar(127) unique not null ,
    password text not null,
    super_role integer not null,
    created_at timestamptz(0) default current_timestamp not null,
    primary key (id)
);

create table personal_info(
    id uuid default gen_random_uuid() not null,
    birth_date date not null,
    member_id uuid unique not null,
    firstname varchar(255) not null,
    lastname varchar(255) not null,
    primary key (id),
    foreign key (member_id)
        references member(id)
        on delete cascade
);

create table family(
    id uuid default gen_random_uuid() not null ,
    family_name varchar(255) not null,
    created_by_id uuid not null,
    created_at timestamptz(0) default current_timestamp not null ,
    primary key (id),
    foreign key ( created_by_id )
        references member (id)
        on delete cascade,
    unique (family_name, created_by_id)
);

create table family_member(
    id uuid default gen_random_uuid() not null ,
    member_id uuid not null,
    family_id uuid not null,
    added_at timestamptz(0) default current_timestamp not null,
    primary key (id),
    foreign key (member_id)
        references member (id)
        on delete cascade,
    foreign key (family_id)
        references family (id)
        on delete cascade,
    unique (member_id, family_id)
);

create table role (
    id uuid not null,
    value integer not null,
    family_id uuid not null,
    name varchar(255) not null,
    access_list smallint[] not null,
    created_at timestamptz(0) not null default current_timestamp,
    primary key (id),
    foreign key (family_id)
        references family (id)
        on delete cascade,
    unique (family_id, name)
);

create table family_member_roles (
    family_member_id uuid not null,
    roles_id uuid not null,
    primary key (family_member_id, roles_id),
    foreign key (family_member_id)
        references family_member(id)
        on delete cascade,
    foreign key (roles_id)
        references role (id)
        on delete cascade
);

create table task (
    id uuid default gen_random_uuid() not null,
    task_name varchar(255) not null,
    description text default null,
    created_by_id uuid not null,
    issued_to_id uuid not null,
    is_checked boolean default null,
    is_marked boolean default null,
    created_date timestamptz(0) not null default current_timestamp,
    due_date timestamptz(0) not null default current_timestamp,
    primary key (id),
    foreign key (created_by_id)
        references member (id)
        on delete cascade,
    foreign key (issued_to_id)
        references member (id)
        on delete cascade
);

create index on task (id, task_name);
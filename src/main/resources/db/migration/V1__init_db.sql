create table if not exists persons
(
    user_id bigint primary key,
    chat_id bigint not null
);

create table if not exists records
(
    id         uuid primary key,
    owner      bigint references persons (user_id) not null,
    weight     numeric,
    created_at date                                not null
);

create index record_creation_date_idx on records (created_at);
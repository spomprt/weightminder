create table if not exists url_links
(
    short_url    varchar primary key,
    original_url varchar not null
);

DROP TABLE IF EXISTS cities;
DROP SEQUENCE IF EXISTS global_seq;
CREATE SEQUENCE global_seq START WITH 1;

CREATE TABLE cities
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name        VARCHAR             NOT NULL,
    description VARCHAR             NOT NULL
);

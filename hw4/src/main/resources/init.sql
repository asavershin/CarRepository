CREATE SEQUENCE person_jn_seq START 1;

CREATE SEQUENCE car_jn_seq START 1;

CREATE SEQUENCE autoservice_ref_seq START 1;

CREATE TABLE person_jn
(
    id   bigint DEFAULT nextval('person_jn_seq') PRIMARY KEY,
    name varchar(50),
    age  integer NOT NULL,
    CHECK (age >= 18),
    CHECK (age <= 200)
);

CREATE TABLE autoservice_ref
(
    id      bigint DEFAULT nextval('autoservice_ref_seq') PRIMARY KEY,
    address varchar(50) NOT NULL ,
    country varchar(50) NOT NULL ,
    name    varchar(50) NOT NULL
);

CREATE TABLE car_jn
(
    id              bigint DEFAULT nextval('car_jn_seq') PRIMARY KEY,
    release_date    date NOT NULL ,
    color           varchar(10) NOT NULL ,
    model           varchar(15) NOT NULL ,
    evp             bigint NOT NULL,
    created_at      timestamp NOT NULL ,
    last_updated_at timestamp NOT NULL ,
    person_id       bigint UNIQUE,
    autoservice_id  bigint,
    FOREIGN KEY (person_id) REFERENCES person_jn(id),
    FOREIGN KEY (autoservice_id) REFERENCES autoservice_ref(id)
);
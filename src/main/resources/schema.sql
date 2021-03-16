CREATE TABLE country
(
    id    INTEGER PRIMARY KEY,
    title VARCHAR(100) NOT NULL
);

CREATE TABLE brand
(
    id         INTEGER PRIMARY KEY,
    title      VARCHAR(20) NOT NULL,
    country_id INTEGER     NOT NULL,
    CONSTRAINT fk__brand__country FOREIGN KEY (country_id)
        REFERENCES country (id)
        ON DELETE RESTRICT
);

CREATE TABLE segment
(
    id    INTEGER PRIMARY KEY,
    title VARCHAR(20) NOT NULL
);

CREATE TABLE model
(
    id         INTEGER PRIMARY KEY,
    title      VARCHAR(50) NOT NULL,
    brand_id   INTEGER     NOT NULL,
    segment_id INTEGER     NOT NULL,
    CONSTRAINT fk__models__brand FOREIGN KEY (brand_id)
        REFERENCES brand (id)
        ON DELETE RESTRICT,
    CONSTRAINT fk__models__segment FOREIGN KEY (segment_id)
        REFERENCES segment (id)
        ON DELETE RESTRICT
);

CREATE TABLE generation
(
    id       INTEGER PRIMARY KEY,
    title    VARCHAR(50) NOT NULL,
    model_id INTEGER     NOT NULL,
    years    VARCHAR(20) NOT NULL,
    length   integer     NOT NULL,
    width    integer     NOT NULL,
    height   integer     NOT NULL,
    CONSTRAINT fk__generation__model FOREIGN KEY (model_id)
        REFERENCES model (id)
        ON DELETE RESTRICT
);

CREATE TABLE modification
(
    id                  INTEGER PRIMARY KEY,
    title               VARCHAR(50)                                                   NOT NULL,
    generation_id       INTEGER                                                       NOT NULL,
    fuel_type           ENUM ('Diesel', 'Gasoline', 'Hybrid')                         NOT NULL,
    engine_type         ENUM ('L4', 'L5', 'L6', 'V6', 'V8','V10', 'V12', 'W8', 'W12') NOT NULL,
    engine_displacement integer                                                       NOT NULL,
    gearbox_type        ENUM ('Auto', 'CVT', 'Manual', 'Robotic')                     NOT NULL,
    wheeldrive_type     ENUM ('AWD', 'FWD', 'RWD')                                    NOT NULL,
    body_style          VARCHAR(255)                                                  NOT NULL,
    acceleration        FLOAT                                                         NOT NULL,
    hp                  INTEGER                                                       NOT NULL,
    max_speed           INTEGER                                                       NOT NULL,
    CONSTRAINT fk__modification__generation FOREIGN KEY (generation_id)
        REFERENCES generation (id)
        ON DELETE RESTRICT
);

create table car
(
    id              integer primary key,
    model_id        integer not null,
    modification_id integer not null,

    constraint fk__car__model foreign key (model_id)
        references model (id) on delete restrict,
    constraint fk__car__modification foreign key (modification_id)
        references modification (id) on delete restrict
);
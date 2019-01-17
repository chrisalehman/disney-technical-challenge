CREATE TABLE dog (
	id BIGSERIAL NOT NULL,
	breed VARCHAR (128) NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE NOT NULL,
	updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE dog_picture (
	id BIGSERIAL NOT NULL,
    dog_id BIGINT NOT NULL,
    url VARCHAR (2048) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id),
	FOREIGN KEY (dog_id) REFERENCES dog(id)
);

CREATE TABLE client (
	id BIGSERIAL NOT NULL,
	client_name VARCHAR (128) NOT NULL,
	shared_secret VARCHAR (128) NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE NOT NULL,
	updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE vote (
	id BIGSERIAL NOT NULL,
	dog_picture_id BIGINT NOT NULL,
	client_id BIGINT NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE NOT NULL,
	updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (dog_picture_id) REFERENCES dog_picture(id),
	FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE UNIQUE INDEX idx_unique_dog_breed ON dog (breed);
CREATE UNIQUE INDEX idx_unique_dog_picture_url ON dog_picture (url);
CREATE UNIQUE INDEX idx_unique_client_client_name ON client (client_name);
CREATE UNIQUE INDEX idx_vote_aggregate_key ON vote (dog_picture_id, client_id);
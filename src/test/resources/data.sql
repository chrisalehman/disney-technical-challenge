-- create some test clients
INSERT INTO client (client_name, shared_secret, created_at, updated_at)
    VALUES ('test_client1', 'test_shared_secret1', current_timestamp, current_timestamp);
INSERT INTO client (client_name, shared_secret, created_at, updated_at)
    VALUES ('test_client2', 'test_shared_secret2', current_timestamp, current_timestamp);
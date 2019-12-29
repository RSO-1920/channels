INSERT INTO channel_type (type_name) VALUES ('USER_DEFAULT');
INSERT INTO channel_type (type_name) VALUES ('PUBLIC');

INSERT INTO channel (channel_name, admin_id, fk_type_id, bucket_name) VALUES ('first channel user1', '1', 1, 'channel184');
INSERT INTO channel (channel_name, admin_id, fk_type_id, bucket_name) VALUES ('RSO', '1', 2, 'channel2');
INSERT INTO channel (channel_name, admin_id, fk_type_id, bucket_name) VALUES ('first channel user2', '2', 1, 'channel3');
INSERT INTO channel (channel_name, admin_id, fk_type_id, bucket_name) VALUES ('RSO 2', '2', 2, 'channel4');

INSERT INTO users_on_channel (user_id, fk_channel_id) VALUES ('1', 1);
INSERT INTO users_on_channel (user_id, fk_channel_id) VALUES ('1', 2);
INSERT INTO users_on_channel (user_id, fk_channel_id) VALUES ('2', 2);
INSERT INTO users_on_channel (user_id, fk_channel_id) VALUES ('2', 3);
INSERT INTO users_on_channel (user_id, fk_channel_id) VALUES ('2', 4);
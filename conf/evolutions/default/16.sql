# --- !Ups
ALTER TABLE n4user ADD COLUMN is_guest tinyint(1) default 0;
CREATE INDEX ix_n4user_is_guest ON n4user (is_guest);

CREATE TABLE df_group
(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	current_group_id bigint(20),
	project_id bigint(20),
	state_code varchar(64) NOT NULL,
	name varchar(128) NOT NULL,
	start_date date NOT NULL,
	end_date date NOT NULL,
	create_datetime datetime NOT NULL,
	CONSTRAINT ck_df_group_state_code CHECK (state_code IN ('READY', 'IN_PROGRESS', 'COMPLETED', 'DROPPED')),
	PRIMARY KEY (id)
);


CREATE TABLE df_member
(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	current_member_id bigint(20),
	user_id bigint(20) NOT NULL,
	team_name varchar(64),
  create_datetime datetime NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE df_member_group
(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	member_id bigint(20) NOT NULL,
	group_id bigint(20) NOT NULL,
	manager_yn bit(1) DEFAULT 0 NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (member_id, group_id)
);


CREATE TABLE df_time_usage
(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	member_id bigint(20) NOT NULL,
	group_id bigint(20) NOT NULL,
	start_datetime datetime NOT NULL,
	minute_usage smallint(6) NOT NULL,
	PRIMARY KEY (id)
);


ALTER TABLE df_group
	ADD FOREIGN KEY (current_group_id)
REFERENCES df_group (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_group
	ADD FOREIGN KEY (project_id)
REFERENCES project (id)
	ON UPDATE SET NULL
	ON DELETE SET NULL
;


ALTER TABLE df_member
	ADD FOREIGN KEY (current_member_id)
REFERENCES df_member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_member
	ADD FOREIGN KEY (user_id)
REFERENCES n4user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_member_group
	ADD FOREIGN KEY (group_id)
REFERENCES df_group (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_member_group
	ADD FOREIGN KEY (member_id)
REFERENCES df_member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_time_usage
	ADD FOREIGN KEY (group_id)
REFERENCES df_group (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_time_usage
	ADD FOREIGN KEY (member_id)
REFERENCES df_member (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


# --- !Downs

DROP INDEX IF EXISTS ix_n4user_is_guest ON n4user;
ALTER TABLE n4user DROP COLUMN is_guest;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS df_member_group;
DROP TABLE IF EXISTS df_time_usage;
DROP TABLE IF EXISTS df_group;
DROP TABLE IF EXISTS df_member;

SET FOREIGN_KEY_CHECKS=1;

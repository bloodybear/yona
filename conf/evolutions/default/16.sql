
# --- !Ups


CREATE TABLE df_group
(
	group_sn bigint(20) NOT NULL AUTO_INCREMENT,
	current_group_sn bigint(20),
	project_id bigint(20),
	state_code varchar(64) NOT NULL,
	name varchar(128) NOT NULL,
	start_date date NOT NULL,
	end_date date NOT NULL,
	create_datetime datetime NOT NULL,
	CONSTRAINT ck_df_group_state_code CHECK (state_code IN ('READY', 'IN_PROGRESS', 'COMPLETED', 'DROPPED')),
	PRIMARY KEY (group_sn)
);


CREATE TABLE df_member
(
	member_sn bigint(20) NOT NULL AUTO_INCREMENT,
	current_member_sn bigint(20),
	user_id bigint(20) NOT NULL,
	team_name varchar(64),
  create_datetime datetime NOT NULL,
	PRIMARY KEY (member_sn)
);


CREATE TABLE df_member_group
(
	member_group_sn bigint(20) NOT NULL AUTO_INCREMENT,
	member_sn bigint(20) NOT NULL,
	group_sn bigint(20) NOT NULL,
	manager_yn bit(1) DEFAULT 0 NOT NULL,
	PRIMARY KEY (member_group_sn),
	UNIQUE (member_sn, group_sn)
);


CREATE TABLE df_time_usage
(
	time_usage_sn bigint(20) NOT NULL AUTO_INCREMENT,
	member_sn bigint(20) NOT NULL,
	group_sn bigint(20) NOT NULL,
	start_datetime datetime NOT NULL,
	minute_usage smallint(6) NOT NULL,
	PRIMARY KEY (time_usage_sn)
);


ALTER TABLE df_group
	ADD FOREIGN KEY (current_group_sn)
REFERENCES df_group (group_sn)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_member_group
	ADD FOREIGN KEY (group_sn)
REFERENCES df_group (group_sn)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_time_usage
	ADD FOREIGN KEY (group_sn)
REFERENCES df_group (group_sn)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_member
	ADD FOREIGN KEY (current_member_sn)
REFERENCES df_member (member_sn)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_member_group
	ADD FOREIGN KEY (member_sn)
REFERENCES df_member (member_sn)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_time_usage
	ADD FOREIGN KEY (member_sn)
REFERENCES df_member (member_sn)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE df_group
	ADD FOREIGN KEY (project_id)
REFERENCES project (id)
	ON UPDATE SET NULL
	ON DELETE SET NULL
;


# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS df_member_group;
DROP TABLE IF EXISTS df_time_usage;
DROP TABLE IF EXISTS df_group;
DROP TABLE IF EXISTS df_member;

SET FOREIGN_KEY_CHECKS=1;

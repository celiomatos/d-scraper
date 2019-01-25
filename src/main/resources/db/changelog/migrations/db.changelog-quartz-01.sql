--liquibase formatted sql

--changeset celio:19

create schema quartz;

--rollback drop schema scraper;

--changeset celio:20

create table quartz.scheduler_job_info (
  job_id bigserial,
  cron_expression varchar(255),
  cron_job boolean NOT NULL,
  job_enable boolean NOT NULL,
  job_group varchar(255) NOT NULL,
  job_name varchar(255) NOT NULL UNIQUE,
  repeat_time bigint,
  primary key (id)
);

--rollback drop table quartz.scheduler_job_info;

--changeset celio:21

create table quartz.job_details (
    job_name character varying(128) NOT NULL,
    job_group character varying(80) NOT NULL,
    description character varying(120),
    job_class_name character varying(200) NOT NULL,
    is_durable boolean,
    is_nonconcurrent boolean,
    is_update_data boolean,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
    requests_recovery boolean,
    job_data bytea,
	primary key (sched_name, job_name, job_group)
);

--rollback drop table quartz.job_details;

--changeset celio:22

create table quartz.triggers (
    trigger_name character varying(80) NOT NULL,
    trigger_group character varying(80) NOT NULL,
    job_name character varying(80) NOT NULL,
    job_group character varying(80) NOT NULL,
    description character varying(120),
    next_fire_time bigint,
    prev_fire_time bigint,
    priority integer,
    trigger_state character varying(16) NOT NULL,
    trigger_type character varying(8) NOT NULL,
    start_time bigint NOT NULL,
    end_time bigint,
    calendar_name character varying(80),
    misfire_instr smallint,
    job_data bytea,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, trigger_name, trigger_group),
	foreign key (sched_name, job_name, job_group) references quartz.job_details(sched_name, job_name, job_group)
);

--rollback drop table quartz.triggers;

--changeset celio:23

create table quartz.simple_triggers (
    trigger_name character varying(80) NOT NULL,
    trigger_group character varying(80) NOT NULL,
    repeat_count bigint NOT NULL,
    repeat_interval bigint NOT NULL,
    times_triggered bigint NOT NULL,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, trigger_name, trigger_group),
	foreign key (sched_name, trigger_name, trigger_group) references quartz.triggers(sched_name, trigger_name, trigger_group)
);

--rollback drop table quartz.simple_triggers;

--changeset celio:24

create table quartz.cron_triggers (
    trigger_name character varying(80) NOT NULL,
    trigger_group character varying(80) NOT NULL,
    cron_expression character varying(80) NOT NULL,
    time_zone_id character varying(80),
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, trigger_name, trigger_group),
	foreign key (sched_name, trigger_name, trigger_group) references quartz.triggers(sched_name, trigger_name, trigger_group)
);

--rollback drop table quartz.cron_triggers;

--changeset celio:25

create table quartz.simprop_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    str_prop_1 character varying(512),
    str_prop_2 character varying(512),
    str_prop_3 character varying(512),
    int_prop_1 integer,
    int_prop_2 integer,
    long_prop_1 bigint,
    long_prop_2 bigint,
    dec_prop_1 numeric(13,4),
    dec_prop_2 numeric(13,4),
    bool_prop_1 boolean,
    bool_prop_2 boolean,
	primary key (sched_name, trigger_name, trigger_group),
	foreign key (sched_name, trigger_name, trigger_group) references quartz.triggers(sched_name, trigger_name, trigger_group)
);

--rollback drop table quartz.simprop_triggers;

--changeset celio:26

create table quartz.blob_triggers (
    trigger_name character varying(80) NOT NULL,
    trigger_group character varying(80) NOT NULL,
    blob_data text,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, trigger_name, trigger_group),
	foreign key (sched_name, trigger_name, trigger_group) references quartz.triggers(sched_name, trigger_name, trigger_group)
);

--rollback drop table quartz.blob_triggers;

--changeset celio:27

create table quartz.calendars (
    calendar_name character varying(80) NOT NULL,
    calendar text NOT NULL,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, calendar_name)
);

--rollback drop table quartz.calendars;

--changeset celio:28

create table quartz.paused_trigger_grps (
    trigger_group character varying(80) NOT NULL,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, trigger_group)
);

--rollback drop table quartz.paused_trigger_grps;

--changeset celio:29

create table quartz.fired_triggers (
    entry_id character varying(95) NOT NULL,
    trigger_name character varying(80) NOT NULL,
    trigger_group character varying(80) NOT NULL,
    instance_name character varying(80) NOT NULL,
    fired_time bigint NOT NULL,
    priority integer NOT NULL,
    state character varying(16) NOT NULL,
    job_name character varying(80),
    job_group character varying(80),
    is_nonconcurrent boolean,
    is_update_data boolean,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
    sched_time bigint NOT NULL,
    requests_recovery boolean,
	primary key (sched_name, entry_id)
);

--rollback drop table quartz.fired_triggers;

--changeset celio:30

create table quartz.scheduler_state (
    instance_name character varying(200) NOT NULL,
    last_checkin_time bigint,
    checkin_interval bigint,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, instance_name)
);

--rollback drop table quartz.scheduler_state;

--changeset celio:31

create table quartz.locks (
    lock_name character varying(40) NOT NULL,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, lock_name)
);

--rollback drop table quartz.locks;

--changeset celio:32

create index on quartz.job_details(sched_name, requests_recovery);

--changeset celio:33

create index on quartz.job_details(sched_name,job_group);

--changeset celio:34

create index on quartz.blob_triggers(sched_name,trigger_name, trigger_group);

--changeset celio:35

create index on quartz.triggers(sched_name,job_name,job_group);

--changeset celio:36

create index on quartz.triggers(sched_name,job_group);

--changeset celio:37

create index on quartz.triggers(sched_name,calendar_name);

--changeset celio:38

create index on quartz.triggers(sched_name,trigger_group);

--changeset celio:39

create index on quartz.triggers(sched_name,trigger_state);

--changeset celio:40

create index on quartz.triggers(sched_name,trigger_name,trigger_group,trigger_state);

--changeset celio:41

create index on quartz.triggers(sched_name,trigger_group,trigger_state);

--changeset celio:42

create index on quartz.triggers(sched_name,next_fire_time);

--changeset celio:43

create index on quartz.triggers(sched_name,trigger_state,next_fire_time);

--changeset celio:44

create index on quartz.triggers(sched_name,misfire_instr,next_fire_time);

--changeset celio:45

create index on quartz.triggers(sched_name,misfire_instr,next_fire_time,trigger_state);

--changeset celio:46

create index on quartz.triggers(sched_name,misfire_instr,next_fire_time,trigger_group,trigger_state);

--changeset celio:47

create index on quartz.fired_triggers(sched_name,instance_name);

--changeset celio:48

create index on quartz.fired_triggers(sched_name,instance_name,requests_recovery);


--changeset celio:49

create index on quartz.fired_triggers(sched_name,job_name,job_group);

--changeset celio:50

create index on quartz.fired_triggers(sched_name,job_group);

--changeset celio:51

create index on quartz.fired_triggers(sched_name,trigger_name,trigger_group);

--changeset celio:52

create index on quartz.fired_triggers(sched_name,trigger_group);

--changeset celio:53

INSERT INTO quartz.scheduler_job_info (cron_expression, job_enable, job_group, job_name, cron_job, repeat_time)
	VALUES ('0 0/5 * ? * *', TRUE, 'default-group', 'job-a', TRUE, NULL);

--rollback delete from quartz.scheduler_job_info where job_name = 'job-a';

--changeset celio:54

INSERT INTO quartz.scheduler_job_info (cron_expression, job_enable, job_group, job_name, cron_job, repeat_time)
	VALUES (NULL, TRUE, 'default-group', 'job-b', FALSE, '600000');

--rollback delete from quartz.scheduler_job_info where job_name = 'job-b';

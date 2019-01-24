create schema quartz;

create table quartz.scheduler_job_info (
  id bigserial,
  cron_expression varchar(255),
  cron_job boolean NOT NULL,
  job_enable boolean NOT NULL,
  job_group varchar(255) NOT NULL,
  job_name varchar(255) NOT NULL,
  repeat_time bigint,
  primary key (id)
);

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

create table quartz.cron_triggers (
    trigger_name character varying(80) NOT NULL,
    trigger_group character varying(80) NOT NULL,
    cron_expression character varying(80) NOT NULL,
    time_zone_id character varying(80),
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, trigger_name, trigger_group),
	foreign key (sched_name, trigger_name, trigger_group) references quartz.triggers(sched_name, trigger_name, trigger_group)
);

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

create table quartz.blob_triggers (
    trigger_name character varying(80) NOT NULL,
    trigger_group character varying(80) NOT NULL,
    blob_data text,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, trigger_name, trigger_group),
	foreign key (sched_name, trigger_name, trigger_group) references quartz.triggers(sched_name, trigger_name, trigger_group)
);

create table quartz.calendars (
    calendar_name character varying(80) NOT NULL,
    calendar text NOT NULL,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, calendar_name)
);

create table quartz.paused_trigger_grps (
    trigger_group character varying(80) NOT NULL,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, trigger_group)
);


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

create table quartz.scheduler_state (
    instance_name character varying(200) NOT NULL,
    last_checkin_time bigint,
    checkin_interval bigint,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, instance_name)
);

create table quartz.locks (
    lock_name character varying(40) NOT NULL,
    sched_name character varying(120) DEFAULT 'TestScheduler'::character varying NOT NULL,
	primary key (sched_name, lock_name)
);

create index on quartz.job_details(sched_name, requests_recovery);
create index on quartz.job_details(sched_name,job_group);
create index on quartz.blob_triggers(sched_name,trigger_name, trigger_group);
create index on quartz.triggers(sched_name,job_name,job_group);
create index on quartz.triggers(sched_name,job_group);
create index on quartz.triggers(sched_name,calendar_name);
create index on quartz.triggers(sched_name,trigger_group);
create index on quartz.triggers(sched_name,trigger_state);
create index on quartz.triggers(sched_name,trigger_name,trigger_group,trigger_state);
create index on quartz.triggers(sched_name,trigger_group,trigger_state);
create index on quartz.triggers(sched_name,next_fire_time);
create index on quartz.triggers(sched_name,trigger_state,next_fire_time);
create index on quartz.triggers(sched_name,misfire_instr,next_fire_time);
create index on quartz.triggers(sched_name,misfire_instr,next_fire_time,trigger_state);
create index on quartz.triggers(sched_name,misfire_instr,next_fire_time,trigger_group,trigger_state);
create index on quartz.fired_triggers(sched_name,instance_name);
create index on quartz.fired_triggers(sched_name,instance_name,requests_recovery);
create index on quartz.fired_triggers(sched_name,job_name,job_group);
create index on quartz.fired_triggers(sched_name,job_group);
create index on quartz.fired_triggers(sched_name,trigger_name,trigger_group);
create index on quartz.fired_triggers(sched_name,trigger_group);

INSERT INTO quartz.scheduler_job_info (cron_expression, job_enable, job_group, job_name, cron_job, repeat_time)
	VALUES ('0 0/5 * ? * *', TRUE, 'default-group', 'job-a', TRUE, NULL);

INSERT INTO quartz.scheduler_job_info (cron_expression, job_enable, job_group, job_name, cron_job, repeat_time)
	VALUES (NULL, TRUE, 'default-group', 'job-b', FALSE, '600000');
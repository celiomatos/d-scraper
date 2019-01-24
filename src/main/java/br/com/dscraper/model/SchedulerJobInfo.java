package br.com.dscraper.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "scheduler_job_info", schema = "quartz")
public class SchedulerJobInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "job_name", unique = true, nullable = false)
    private String jobName;

    @Column(name = "job_group", nullable = false)
    private String jobGroup;

    @Column(name = "job_enable", nullable = false)
    private boolean jobEnable;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "repeat_time")
    private Long repeatTime;

    @Column(name = "cron_job", nullable = false)
    private boolean cronJob;
}

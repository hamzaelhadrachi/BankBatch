package com.zerotohero.bankspringbatch.controller;


import com.zerotohero.bankspringbatch.processor.BankTransactionItemAnalyticProcessor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BankRestController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Autowired
    private BankTransactionItemAnalyticProcessor analyticProcessor;

    @GetMapping("/startJob")
    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> params = new HashMap<>();

        params.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(params);

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        while (jobExecution.isRunning()){
            System.out.println("is Running...");
        }
        return jobExecution.getStatus();
    }


    @GetMapping("/analytics")
    public Map<String, Double> analytics(){
        Map<String,Double> map = new HashMap<>();

        map.put("totalCredit", analyticProcessor.getTotalCredit());
        map.put("totalDebit", analyticProcessor.getTotalDebit());

        return map;
    }

}

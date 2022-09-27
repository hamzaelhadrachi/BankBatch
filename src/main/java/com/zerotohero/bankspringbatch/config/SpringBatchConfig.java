package com.zerotohero.bankspringbatch.config;

import com.zerotohero.bankspringbatch.dao.BankTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.Delimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemReader<BankTransaction> bankTransactionItemReader;
    @Autowired
    private ItemProcessor<BankTransaction, BankTransaction> bankTransactionProcessor;
    @Autowired
    private ItemWriter<BankTransaction> bankTransactionItemWriter;


    public Job bankJob(){
        Step step1 = stepBuilderFactory.get("step-data-loader")
                .<BankTransaction,BankTransaction>chunk(100)
                .reader(bankTransactionItemReader)
                .processor(bankTransactionProcessor)
                .writer(bankTransactionItemWriter)
                .build();

        return jobBuilderFactory.get("bank-job-data-loader")
               .start(step1)
                .build();

    }

    @Bean
    public FlatFileItemReader<BankTransaction> flatFileItemReader(@Value("${inputFile}") Resource inFile){
        FlatFileItemReader<BankTransaction> ffiReader = new FlatFileItemReader<>();
        ffiReader.setName("File-Reader");
        ffiReader.setLinesToSkip(1);
        ffiReader.setResource(inFile);
        ffiReader.setLineMapper(lineMapper());

        return ffiReader;
    }

    @Bean
    public LineMapper<BankTransaction> lineMapper() {
        DefaultLineMapper<BankTransaction> mapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setStrict(false);
        tokenizer.setNames("id","accountId","strTransactionDate","transactionType","transactionAmount");
        mapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<BankTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BankTransaction.class);
        mapper.setFieldSetMapper(fieldSetMapper);

        return mapper;
    }


}

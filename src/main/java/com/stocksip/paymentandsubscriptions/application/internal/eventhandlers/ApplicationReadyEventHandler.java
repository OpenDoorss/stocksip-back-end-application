package com.stocksip.paymentandsubscriptions.application.internal.eventhandlers;

import com.stocksip.paymentandsubscriptions.domain.model.commands.SeedPlansCommand;
import com.stocksip.paymentandsubscriptions.domain.services.PlanCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ApplicationReadyEventHandler {

    private final PlanCommandService planCommandService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    public ApplicationReadyEventHandler(PlanCommandService planCommandService) {
        this.planCommandService = planCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Seeding plans for {} at {}", applicationName, currentTimestamp());
        var command = new SeedPlansCommand();
        planCommandService.handle(command);
        LOGGER.info("Finished seeding plans for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}

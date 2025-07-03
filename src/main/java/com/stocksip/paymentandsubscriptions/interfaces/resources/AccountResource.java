package com.stocksip.paymentandsubscriptions.interfaces.resources;

import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.BusinessName;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.GeneralEmail;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.Role;

public record AccountResource(
        String email,

        Long userOwnerId,

        Role role,
        String businessName
) {}

package com.stocksip.orderoperationandmonitoring.domain.model.queries;

import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.AccountId;

public record GetPublishedCatalogsByAccountIdQuery(AccountId accountId, boolean onlyPublished)      {}

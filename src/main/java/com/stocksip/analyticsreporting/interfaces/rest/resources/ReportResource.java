package com.stocksip.analyticsreporting.interfaces.rest.resources;

import java.util.Date;

public record ReportResource(Long id, String productName, String type, double price, double amount, Date reportDate, double lostAmount) {
}

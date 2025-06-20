package com.stocksip.analyticsreporting.interfaces.rest.resources;

import java.util.Date;

/**
 * @summary 
 * Resource class for Report.
 * @param id - the ID of the Report.
 * @param productName - the product name of the Report.
 * @param type - the type of the Report.
 * @param price - the price of the Report.
 * @param amount - the amount of the Report.
 * @param reportDate - the report date of the Report.
 * @param lostAmount - the lost amount of the Report.
 */
public record ReportResource(Long id, String productName, String type, double price, double amount, Date reportDate, double lostAmount) {
}

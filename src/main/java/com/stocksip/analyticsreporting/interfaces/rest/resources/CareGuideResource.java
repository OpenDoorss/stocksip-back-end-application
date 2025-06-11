package com.stocksip.analyticsreporting.interfaces.rest.resources;

/**
 * @summary 
 * @description Resource class for CareGuide.
 * @param id - the ID of the CareGuide.
 * @param guideName - the guide name of the CareGuide.
 * @param type - the type of the CareGuide.
 * @param description - the description of the CareGuide.
 */
public record CareGuideResource(Long id, String guideName, String type, String description) {
}

package com.stocksip.inventorymanagement.domain.model.valueobjects;

/**
 * Enumeration representing the type of liquor of a product.
 *
 * @summary
 * This enum defines the possible types of liquor of a product.
 * The possible types of liquor are:
 * - RUM: This type of liquor is made from the distillation and fermentation of sugarcane juice.
 * - WHISKY: This type of liquor is obtained from the distillation of the wort that is obtained from fermented malt
 * - GIN: This type of liquor is elaborated with water and cereals like the wheat.
 * - VODKA: This type of liquor is similar to the gin, but uses fermented ingredients like potatoes.
 * - TEQUILA: This type of liquor is made from the distillation of the agave.
 * - BRANDY: This type of liquor is made from distilled wine.
 * - WINE: This liquor is made from fermented grapes.
 * - BEER: This is made with a base of malted cereals like the barley, hops, yeast and water.
 * - CREAMY: It's made with a base of another liquor type and a dairy product.
 * - HERBAL: This type of liquor is made from herbs.
 * - FRUITY: It's made with fruits.
 * - SPECIAL: In this category, there are liquors that don't fit in the regular categories.
 *
 * @since 1.0.0
 */
public enum LiquorType {
    RUM,
    WHISKY,
    GIN,
    VODKA,
    TEQUILA,
    BRANDY,
    WINE,
    BEER,
    CREAMY,
    HERBAL,
    FRUITY,
    SPECIAL
}

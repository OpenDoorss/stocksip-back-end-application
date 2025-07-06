package com.stocksip.authentication.domain.model.commands;

/**
 * Sign up command
 * <p>
 *     This class represents the command to sign up a user.
 * </p>
 * @param username the username of the user
 * @param password the password of the user
 *
 * @see com.stocksip.authentication.domain.model.aggregates.User
 */
public record SignUpCommand(String username, String password) {}

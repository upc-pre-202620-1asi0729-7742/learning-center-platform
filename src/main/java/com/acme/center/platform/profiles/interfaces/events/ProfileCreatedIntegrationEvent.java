package com.acme.center.platform.profiles.interfaces.events;

import com.acme.center.platform.profiles.domain.model.aggregates.Profile;

/**
 * Integration event published by the {@code profiles} bounded context when a new
 * {@link Profile} has been successfully created and persisted.
 *
 * <p>This is the <em>published language</em> of the {@code profiles} context.
 * Other bounded contexts (e.g. {@code learning}) must listen to this event rather
 * than to internal domain events such as
 * {@link com.acme.center.platform.profiles.domain.model.events.ProfileCreatedEvent},
 * which is an internal concern of the {@code profiles} domain.</p>
 *
 * @param profileId   The identity assigned to the newly created profile.
 * @param firstName   The profile owner's first name.
 * @param lastName    The profile owner's last name.
 * @param email       The profile owner's email address.
 * @param street      Street component of the profile's address.
 * @param number      Street number component of the profile's address.
 * @param city        City component of the profile's address.
 * @param postalCode  Postal code component of the profile's address.
 * @param country     Country component of the profile's address.
 */
public record ProfileCreatedIntegrationEvent(
        Long profileId,
        String firstName,
        String lastName,
        String email,
        String street,
        String number,
        String city,
        String postalCode,
        String country) {

    /**
     * Convenience factory that extracts all fields from a saved {@link Profile}.
     *
     * @param profile the saved profile (must already carry a non-null id)
     * @return a fully populated {@link ProfileCreatedIntegrationEvent}
     */
    public static ProfileCreatedIntegrationEvent from(Profile profile) {
        var name    = profile.getName();
        var address = profile.getStreetAddressValue();
        return new ProfileCreatedIntegrationEvent(
                profile.getId(),
                name.firstName(),
                name.lastName(),
                profile.getEmailAddress(),
                address.street(),
                address.number(),
                address.city(),
                address.postalCode(),
                address.country());
    }
}


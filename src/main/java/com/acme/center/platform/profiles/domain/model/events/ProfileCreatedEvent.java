package com.acme.center.platform.profiles.domain.model.events;

import com.acme.center.platform.profiles.domain.model.aggregates.Profile;

/**
 * Domain event published when a new {@link Profile} is successfully created and persisted.
 *
 * <p>Other bounded contexts (e.g. {@code learning}) can listen to this event
 * to react to profile creation without directly coupling to the {@code profiles}
 * application services.</p>
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
public record ProfileCreatedEvent(
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
     * Convenience factory that extracts all needed fields from a saved {@link Profile}.
     *
     * @param profile the saved profile (must already carry a non-null id)
     * @return a fully populated {@link ProfileCreatedEvent}
     */
    public static ProfileCreatedEvent from(Profile profile) {
        var name    = profile.getName();
        var address = profile.getStreetAddressValue();
        return new ProfileCreatedEvent(
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


package com.acme.center.platform.profiles.interfaces.acl;

/**
 * ACL facade that exposes Profiles bounded context capabilities to other contexts.
 */
public interface ProfilesContextFacade {
    /**
     * Creates a profile and returns its identifier.
     *
     * @param firstName first name
     * @param lastName last name
     * @param email email address
     * @param street street name
     * @param number street number
     * @param city city
     * @param postalCode postal code
     * @param country country
     * @return created profile identifier, or {@code 0L} when creation fails
     */
    Long createProfile(String firstName, String lastName, String email, String street, String number, String city, String postalCode, String country);

    /**
     * Fetches a profile identifier by email.
     *
     * @param email profile email address
     * @return profile identifier, or {@code 0L} when not found
     */
    Long fetchProfileIdByEmail(String email);
}

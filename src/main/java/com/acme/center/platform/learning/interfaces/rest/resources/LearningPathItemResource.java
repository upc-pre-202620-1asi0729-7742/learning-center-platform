package com.acme.center.platform.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Learning path item resource.
 */
@Schema(
    name = "LearningPathItemResponse",
    description = "Learning path item information response",
    example = "{\"learningPathItemId\": 1, \"courseId\": 1, \"tutorialId\": 5}"
)
public record LearningPathItemResource(
    @Schema(description = "Learning path item unique identifier", example = "1")
    Long learningPathItemId,

    @Schema(description = "Associated course identifier", example = "1")
    Long courseId,

    @Schema(description = "Associated tutorial identifier", example = "5")
    Long tutorialId
) {
}

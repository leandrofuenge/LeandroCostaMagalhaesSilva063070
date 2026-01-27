package com.app.music.service;

import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateLimitServiceTest {

    private RateLimitService rateLimitService;

    @BeforeEach
    void setUp() {
        rateLimitService = new RateLimitService();
    }

    @Test
    void shouldCreateNewBucketForNewKey() {
        String key = "user-1";

        Bucket bucket = rateLimitService.resolveBucket(key);

        assertThat(bucket).isNotNull();
    }

    @Test
    void shouldReturnSameBucketForSameKey() {
        String key = "user-2";

        Bucket first = rateLimitService.resolveBucket(key);
        Bucket second = rateLimitService.resolveBucket(key);

        assertThat(first).isSameAs(second); // Mesma instância
    }

    @Test
    void shouldCreateDifferentBucketsForDifferentKeys() {
        Bucket bucket1 = rateLimitService.resolveBucket("user-1");
        Bucket bucket2 = rateLimitService.resolveBucket("user-2");

        assertThat(bucket1).isNotSameAs(bucket2);
    }
}

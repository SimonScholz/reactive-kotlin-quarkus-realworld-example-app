package io.github.simonscholz

import io.quarkus.test.junit.QuarkusIntegrationTest

@QuarkusIntegrationTest
class NativeResourceIT : ResourceTest() {
    // Execute the same tests but in packaged mode.
}

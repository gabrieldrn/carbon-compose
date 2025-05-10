// Karma is using Mocha for testing. This file acts a configuration for Mocha.
// https://youtrack.jetbrains.com/issue/KT-73191/KJS-Gradle-Cant-set-browser-test-timeout-with-karma-DSL-from-KGP
config.set({
    client: {
        mocha: {
            timeout: 10000
        }
    }
});

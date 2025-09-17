# .github/workflows/tests.yml
name: innersaavn CI

on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]
  workflow_dispatch:

permissions:
  contents: read

concurrency:
  group: innersaavn-${{ github.ref }}
  cancel-in-progress: true

jobs:
  unit-tests:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up Java (Temurin 21)
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '21'

      - name: Set up Gradle (cache)
        uses: gradle/actions/setup-gradle@v3

      - name: Verify Gradle Wrapper
        shell: bash
        run: |
          if [ ! -f "./gradlew" ]; then
            echo "Gradle Wrapper missing. Generate locally: gradle wrapper --gradle-version 8.9"; exit 1
          fi
          chmod +x ./gradlew

      - name: Run unit tests
        run: ./gradlew test --stacktrace --no-daemon

  live-tests:
    if: ${{ secrets.SAAVN_BASE_URL != '' }}
    runs-on: ubuntu-latest
    env:
      SAAVN_BASE_URL: ${{ secrets.SAAVN_BASE_URL }}
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up Java (Temurin 21)
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '21'

      - name: Set up Gradle (cache)
        uses: gradle/actions/setup-gradle@v3

      - name: Verify Gradle Wrapper
        shell: bash
        run: |
          if [ ! -f "./gradlew" ]; then
            echo "Gradle Wrapper missing. Generate locally: gradle wrapper --gradle-version 8.9"; exit 1
          fi
          chmod +x ./gradlew

      - name: Run live tests
        run: ./gradlew test --stacktrace --no-daemon
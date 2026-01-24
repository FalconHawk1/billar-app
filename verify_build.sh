#!/bin/bash

# Build Verification Script for Billiard Player View App
# This script verifies the build and provides diagnostic information

echo "========================================="
echo "Billiard Player View - Build Verification"
echo "========================================="
echo ""

# Check Java version
echo "1. Checking Java version..."
java -version
echo ""

# Check Gradle wrapper
echo "2. Checking Gradle wrapper..."
if [ -f "./gradlew" ]; then
    echo "✓ Gradle wrapper found"
    chmod +x ./gradlew
else
    echo "✗ Gradle wrapper not found"
    exit 1
fi
echo ""

# Count Kotlin files
echo "3. Counting Kotlin source files..."
KOTLIN_COUNT=$(find app/src/main/java -name "*.kt" | wc -l)
echo "✓ Found $KOTLIN_COUNT Kotlin files"
echo ""

# List package structure
echo "4. Package structure:"
echo ""
echo "Data Layer:"
find app/src/main/java/co/hitech/billar_app/data -name "*.kt" | wc -l | xargs echo "  - Files:"
echo ""
echo "Domain Layer:"
find app/src/main/java/co/hitech/billar_app/domain -name "*.kt" | wc -l | xargs echo "  - Files:"
echo ""
echo "Presentation Layer:"
find app/src/main/java/co/hitech/billar_app/presentation -name "*.kt" | wc -l | xargs echo "  - Files:"
echo ""
echo "Utils:"
find app/src/main/java/co/hitech/billar_app/utils -name "*.kt" | wc -l | xargs echo "  - Files:"
echo ""
echo "UI Theme:"
find app/src/main/java/co/hitech/billar_app/ui -name "*.kt" | wc -l | xargs echo "  - Files:"
echo ""

# Check network connectivity to required repositories
echo "5. Checking network connectivity..."
echo ""

echo "  Checking Google Maven Repository..."
if curl -s --head --connect-timeout 5 https://dl.google.com/dl/android/maven2/ | grep "200 OK" > /dev/null; then
    echo "  ✓ Google Maven accessible"
else
    echo "  ✗ Google Maven NOT accessible (required for Android Gradle Plugin)"
fi
echo ""

echo "  Checking Maven Central..."
if curl -s --head --connect-timeout 5 https://repo1.maven.org/maven2/ | grep "200 OK" > /dev/null; then
    echo "  ✓ Maven Central accessible"
else
    echo "  ✗ Maven Central NOT accessible"
fi
echo ""

echo "  Checking Gradle Plugin Portal..."
if curl -s --head --connect-timeout 5 https://plugins.gradle.org/ | grep "200" > /dev/null; then
    echo "  ✓ Gradle Plugin Portal accessible"
else
    echo "  ✗ Gradle Plugin Portal NOT accessible"
fi
echo ""

# Try to build
echo "6. Attempting build..."
echo ""
echo "  Running: ./gradlew clean assembleDebug"
echo ""

if ./gradlew clean assembleDebug --no-daemon --stacktrace; then
    echo ""
    echo "========================================="
    echo "✓ BUILD SUCCESSFUL"
    echo "========================================="
    echo ""
    echo "APK Location:"
    find app/build/outputs/apk -name "*.apk" 2>/dev/null || echo "APK not found in expected location"
    echo ""
else
    echo ""
    echo "========================================="
    echo "✗ BUILD FAILED"
    echo "========================================="
    echo ""
    echo "Common issues:"
    echo "1. Network connectivity to Google Maven required"
    echo "2. Ensure Android SDK is installed"
    echo "3. Check ANDROID_HOME environment variable"
    echo ""
fi

echo ""
echo "Build verification complete."
echo ""

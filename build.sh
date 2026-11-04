#!/usr/bin/env bash
# Build "About You" without relying on a system Gradle install.
# Usage: ./build.sh [assembleDebug|assembleRelease|testDebugUnitTest]
set -u

GRADLE_HOME="${GRADLE_HOME:-/workspace/gradle-8.7}"
export JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-21-openjdk-amd64}"
export ANDROID_HOME="${ANDROID_HOME:-/workspace/android-sdk}"
export ANDROID_SDK_ROOT="$ANDROID_HOME"
export PATH="$GRADLE_HOME/bin:$JAVA_HOME/bin:$PATH"

TASK="${1:-assembleDebug}"
cd "$(dirname "$0")"

echo "JAVA_HOME=$JAVA_HOME"
echo "ANDROID_HOME=$ANDROID_HOME"
echo "Task: $TASK"

gradle "$TASK" --no-daemon

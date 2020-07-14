#!/usr/bin/env bash

# This script creates a new Scala project using the "mill" build tool
# Usage
#   $ cd PROJECT_DIR
#   $ ../setup/scripts/create_scala_project.sh PROJECT_NAME
#   where:
#      PROJECT_DIR is the new project directory
#      PROJECT_NAME is the new name of the project

set -e

MILL_VERSION="0.7.3"
EXAMPLE_PKG="example-3"
EXAMPLE_PROJECT="foo"
ZIPFILE="${EXAMPLE_PKG}.zip"
MILL_EXEC="./mill"
BUILD_SC="build.sc"

# Validate parameters
PROJECT_NAME="$1"
[ -z "$PROJECT_NAME" ] && echo "Must specify project name" && exit 1

set -u

# Do not install Mill if already installed
[ -f "$MILL_EXEC" ] && \
  echo "Mill is already installed. To re-install, delete the 'mill' script and run this script againt" && exit 1

echo "Cleaning up"
[ -f "$MILL_EXEC" ] && echo "Deleting $MILL_EXEC" && rm -f "$MILL_EXEC"
[ -f "$BUILD_SC" ] && echo "Deleting $BUILD_SC" && rm -f "$BUILD_SC"
[ -d "$PROJECT_NAME" ] && echo "Deleting $PROJECT_NAME" && rm -fr "$PROJECT_NAME"
[ -d "$EXAMPLE_PKG" ] && echo "Deleting $EXAMPLE_PKG" && rm -fr "$EXAMPLE_PKG"
[ -d "$EXAMPLE_PROJECT" ] && echo "Deleting $EXAMPLE_PROJECT" && rm -fr "$EXAMPLE_PROJECT"

echo "Downloading Mill"
curl -L https://github.com/lihaoyi/mill/releases/download/${MILL_VERSION}/${MILL_VERSION}-${EXAMPLE_PKG}.zip -o "$ZIPFILE"

echo "Installing Mill"
unzip "$ZIPFILE"
[ -f "$ZIPFILE" ] && rm -f "$ZIPFILE"
mv "${EXAMPLE_PKG}"/* .; [ -d "$EXAMPLE_PKG" ] && rm -fr "$EXAMPLE_PKG"
chmod +x $MILL_EXEC

echo "Setting the project name in project files"
mv $EXAMPLE_PROJECT "$PROJECT_NAME"
sed -i "" -e "s/object $EXAMPLE_PROJECT/object $PROJECT_NAME/" "$BUILD_SC"
find "$PROJECT_NAME" -type f -name "*.scala" -exec sed -i "" -e "s/package $EXAMPLE_PROJECT/package $PROJECT_NAME/" {} \;

echo "Generate IntelliJ files"
$MILL_EXEC mill.scalalib.GenIdea/idea

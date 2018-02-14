#!/bin/bash

# Get parent directory
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

cd ${DIR}/src/main/java
echo "Running pmd: "
pmd-coursera .

echo ""
checkstyle-coursera *.java

cd ${DIR}/out/production/classes
echo ""
findbugs-coursera *.class
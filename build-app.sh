#!/bin/sh

skipTests="-Dmaven.test.skip=true"

mvn $skipTests clean package
#!/bin/sh
set -e

cd /Users/anton/repo/silver/tutorials/dc
./silver-compile
cd -

rm -rf build_tmp/
mkdir build_tmp

echo "Gathering sources.."
find src -name "*.java" > sources.txt

echo "Building.."
# "Our own" class files
javac @sources.txt -cp /Users/anton/repo/silver/tutorials/dc/dc.jar:/Users/anton/repo/silver/jars/commonmark-0.17.1.jar:/Users/anton/repo/silver/jars/CopperCompiler.jar:/Users/anton/repo/silver/jars/silver.compiler.composed.Default.jar:/Users/anton/repo/silver/jars/SilverRuntime.jar -d build_tmp -source 8 -target 8

cd build_tmp

echo "Generating jar.."
echo "Main-Class: silverwrapper.SilverWrapper" >> Manifest.txt
echo "Class-Path: /Users/anton/repo/silver/tutorials/dc/dc.jar /Users/anton/repo/silver/jars/commonmark-0.17.1.jar /Users
 /anton/repo/silver/jars/SilverRuntime.jar /Users/anton/repo/silver/ja
 rs/CopperCompiler.jar" >> Manifest.txt


DST=../SilverWrapper.jar
jar cfm $DST Manifest.txt **/*

cd ..

echo "Cleaning up.."
rm sources.txt
rm -rf build_tmp

echo "Done! Built '$DST'"

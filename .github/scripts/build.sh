#!/usr/bin/env bash

cd "${0%/*}/../.." || exit

# clean old build
#./gradlew clean

# build ios library
./gradlew library:assembleKMPLibraryReleaseXCFramework || exit

cd "library/build/XCFrameworks/release/" || exit

zip -r XCFrameworks.zip KMPLibrary.xcframework

checksum="$(swift package compute-checksum XCFrameworks.zip)"

echo "
// swift-tools-version:5.3
import PackageDescription

let package = Package(
   name: \"KMPLibrary\",
   platforms: [
     .iOS(.v14),
   ],
   products: [
      .library(name: \"KMPLibrary\", targets: [\"KMPLibrary\"])
   ],
   targets: [
      .binaryTarget(
                     name: \"KMPLibrary\",
                     path: \"XCFrameworks.zip\"
                  )
   ]
)
" > Package.swift

cd "${0%/*}/../.." || exit
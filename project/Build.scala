import sbt._
import sbt.Keys._
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform._
import ScalariformKeys._

import wartremover._

object Build extends Build {

  lazy val base: Project = Project(
    "safe-config",
    file("."),
    settings = Defaults.defaultSettings ++ scalariformSettings ++ wartremoverSettings ++ Seq(
      organization := "com.kinja",
      version      := "0.0.1-SNAPSHOT",
      scalaVersion := "2.11.6",
      scalacOptions ++= Seq(
        "-deprecation",          // Show details of deprecation warnings.
        "-encoding", "UTF-8",    // Set correct encoding for Scaladoc.
        "-feature",              // Show details of feature warnings.
        "-language:higherKinds", // Enable higher-kinded types.
        "-language:postfixOps",  // We use this frequently for defining durations.
        "-language:reflectiveCalls",
        "-unchecked",            // Show details of unchecked warnings.
        "-Xfatal-warnings",      // All warnings should result in a compiliation failure.
        "-Xfuture",              // Disables view bounds, adapted args, and unsound pattern matching in 2.11.
        "-Xlint",                // Ensure best practices are being followed.
        "-Yno-adapted-args",     // Prevent implicit tupling of arguments.
        "-Ywarn-dead-code",      // Fail when dead code is present. Prevents accidentally unreachable code.
        "-Ywarn-value-discard"   // Prevent accidental discarding of results in unit functions.
      ),
      ScalariformKeys.preferences := ScalariformKeys.preferences.value
        .setPreference(AlignArguments, true)
        .setPreference(AlignParameters, true)
        .setPreference(AlignSingleLineCaseStatements, true)
        .setPreference(DoubleIndentClassDeclaration, true)
        .setPreference(DoubleIndentClassDeclaration, true)
        .setPreference(PreserveDanglingCloseParenthesis, true)
        .setPreference(RewriteArrowSymbols, true)
        .setPreference(SpaceBeforeColon, true),
      wartremoverErrors ++= Seq(
        Wart.Any2StringAdd,  // Prevent accidental stringification.
        Wart.FinalCaseClass, // Case classes should always be final.
        Wart.IsInstanceOf,   // Prevent type-casing.
        Wart.ListOps,        // List.head, etc. are unsafe.
        Wart.Null,           // Null is bad, bad bad.
        Wart.OptionPartial,  // Option.get is unsafe.
        Wart.Return          // Prevent use of `return` keyword.
      ),
      libraryDependencies ++= Seq(
        "com.typesafe" % "config" % "1.2.1",
        "org.scalaz" %% "scalaz-core" % "7.0.7",
        "org.scala-lang" % "scala-compiler" % scalaVersion.value,
        compilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)
      )
    )
  )
}
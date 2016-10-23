enablePlugins(ScalaJSPlugin)

name := "scalajs-react-template"

version := "1.0"

scalaVersion := "2.11.7"


// create launcher file ( its search for object extends JSApp , make sure there is only one file)
persistLauncher := true

persistLauncher in Test := false

val scalaJSReactVersion = "1.0.0-SNAPSHOT"

val scalaCssVersion = "0.3.1"

libraryDependencies ++= Seq("com.github.japgolly.scalajs-react" %%% "neo" % scalaJSReactVersion,
  "com.github.japgolly.scalacss" %%% "core" % scalaCssVersion,
  "me.chrons" %%% "diode-react" % "1.0.1-SNAPSHOT")

jsDependencies ++= Seq(
)


val bundle = project.in(file("bundle"))

jsDependencies += ProvidedJS / "bundle.js"

addCommandAlias("bundle", "bundle/bundle")

// creates single js resource file for easy integration in html page
skip in packageJSDependencies := false



// copy  javascript files to js folder,that are generated using fastOptJS/fullOptJS

crossTarget in (Compile, fullOptJS) := file("js")

crossTarget in (Compile, fastOptJS) := file("js")

crossTarget in (Compile, packageJSDependencies) := file("js")

crossTarget in (Compile, packageScalaJSLauncher) := file("js")

crossTarget in (Compile, packageMinifiedJSDependencies) := file("js")

artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value /
  ((moduleName in fastOptJS).value + "-opt.js"))



scalacOptions += "-feature"


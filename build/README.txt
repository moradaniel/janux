This folder contains various maven2 pom.xml files with build meta information.  These poms all have
a packaging of type 'pom' and as such do not produce any deployable artifacts.  They include the
following:

- build/pom.xml: janux-project

This pom contains general project information, and other build information
(dependencies, plugins) that are shared by other modules.  This is the parent project of which
most of the other modules are children

- lib/*/pom.xml: janux-lib-*

The various poms in this folder define group of dependencies (util, hibernate, spring, etc...) that
can be used by other projects, and makes it possible to abbreviate the lists of dependencies in each
module.  This is according to the Maven Best Practice recommended by Sonatype at:

	http://www.sonatype.com/books/mvnref-book/reference/pom-relationships-sect-pom-best-practice.html#pom-relationships-sect-grouping-deps

- template/*/pom.xml

The various poms in this folder 'extend' the janux-project pom and define more specialized projects,
for example a project that uses hibernate to persist model objects to a database.


By running:

	mvn install

from this ./build folder it is possible to install all the various poms to the local repository.
This is necessary in the event that any of the information in these poms changes, and before any
other projects that depend on these poms 'see' the changes.

The top-level pom at $JANUX_HOME/pom.xml (or ./build/../pom.xml) defines a janux-master pom that is
used to make it possible to build all the artifacts of the janux project.  It will also build all
the poms in this build folder.

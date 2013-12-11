Eclipse 3.2 support README

As far as I know, there is no perfect Maven/Eclipse integration solution. The approach I have taken is one
that provides Eclipse support as an adjunct to an "official" maven project tree. It takes advantage of Eclipse's
support for "linked resources" (i.e. resources that live outside of the project), as well as Eclipse's User
Libraries feature. 

Here's how to get up and running:

- First, if you haven't done so already, check out the top-level janux project, and build it with:
         
       maven multiproject:install
         
      (This is necessary. The Eclipse projects not only point into the janux source trees, but also use your 
      Maven repository for required build-path libraries, and may even point to janux's target directories for
      auto-generated source.)

- Add a "janux_maven" variable to allow Eclipse projects to point to janux source directories:
    - Go to Preferences>General>Workspace>Linked Resources.
    - Click the New... button. Name your variable "janux_maven"
          and click "Folder..." to select your top-level janux directory.
          
- Set up Eclipse User Libraries to allow your Eclipse projects
      to include in their Build Paths the appropriate libraries
      from your Maven repository, as follows:
    - open janux-eclipse-userlibs.xml and Find/Replace [MAVEN_REPO]
          with the path to your maven repository. (Yes, this is a
          pain, and is fragile, but I don't know of another way to
          do this.)
    - Import the libraries defined in this file: go to
          Preferences>Java>Build Path>User Libraries; click Import;
          select this file; import all the libraries presented.
          
- Check out the desired janux projects from the projects directory (at the moment,
	there's only janux-all):
    - You may now create your own Eclipse projects
          that depend on these, take advantage of source lookups, compile checking, etc.
          

# MIPS Simulation Tool

## Information about this repository

This is the repository that you are going to use **individually** for developing your project. Please use the resources provided in the module to learn about **plagiarism** and how plagiarism awareness can foster your learning.

Regarding the use of this repository, once a feature (or part of it) is developed and **working** or parts of your system are integrated and **working**, define a commit and push it to the remote repository. You may find yourself making a commit after a productive hour of work (or even after 20 minutes!), for example. Choose commit message wisely and be concise.

Please choose the structure of the contents of this repository that suits the needs of your project but do indicate in this file where the main software artefacts are located.

----

An executable JAR file can be found in the root directory, named MipsSimulator-1.0-all.jar. This has been developed and runs using Java 17.

After launching the program, the default MIPS configuration should be loaded from Jsons/MIPS_Model.json

The project can be built into a jar file with Gradle 7.4+, by running the command gradle :shadowJar. The jar file can then be found in build/libs
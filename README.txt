Ονοματεπώνυμο: Tzortzis ALexandros Menelaos
ΑΕΜ: 2638
E-mail: altzortzis@uth.gr

------------------------------------------------------------------------------------------

In order to present the contents of current directory in a flow and dynamic (when resizing window) manner,i chose to use wrapLayout,instead of flowLaout,as it provides the necessary dynamic calculations of panel's
size in which the content are displayed in order to properly resize and wrap those contents inside it
Link for code as well as more information below:
https://tips4java.wordpress.com/2008/11/06/wrap-layout/
  
------------------------------------------------------------------------------------------

I used ant in order to compile,run and archive(create jar file)this project
As result,you can run the commands below from the command line in main directory to perform the same actions:

ant 		: by default it is set to execute program (as well as compile it)
ant compile : compiles the project in src and moves the .class files to build/classes
ant archive : creates a jar file containing project,and stores it in dist directory
ant run		: runs .class files(executes project) and if necessary,compiles the the project
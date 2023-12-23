# fileBrowser

A program to explore the files on your system. The program you write should run on any operating system (Windows, Linux, MAC OS/X, etc.). The program will consist of the main window which will support almost all of the functionality.

![](filebrowser.png)

Figure 1 The layout of the window

The layout of the main window is shown in Figure 1. The areas identified in the figure are as follows:

1. Central horizontal menu: Appears at the top of the window.![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.004.png)
1. Favourites: the user selects the directories he/she uses frequently and wants them to appear as "favourites" for the sake of speedy navigation in his/her system.
1. Search function: it appears in the center of the window just below the horizontal menu and above ![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.005.png)Path>To>Current>Directory.
1. Path>To>Current>Directory  (Breadcrumb):  this  is  a  series  of  links  to  parent directories. ![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.006.png) Each  link  is  separated  from  the  previous  and  next  link  by  the  '>' symbol.
1. Content of the current list: The contents of the current directory are displayed here, in the form of icons. The icons must be aligned next to each other and one below the other.

The colours in the above figure are to highlight the layout and not to copy them as they are in your application. The application is not expected to have any color.

**The main menu**

The main menu has the following options:

<table><tr><th colspan="1">Level 1</th><th colspan="1">Level 2</th><th colspan="1">Description</th></tr>
<tr><td colspan="1" rowspan="2" valign="top">File</td><td colspan="1" valign="top">New Window</td><td colspan="1">Creates a new window. The window always starts from the user's home directory.</td></tr>
<tr><td colspan="1">Exit</td><td colspan="1">Ends the current window.</td></tr>
<tr><td colspan="1" rowspan="4" valign="top"><p>Edit</p><p><i><b>Note:</b> If no file or directory is selected by left- clicking, the option should remain disabled.</i></p></td><td colspan="1" valign="top">Cut</td><td colspan="1" valign="top">Marks a file or directory in order to move it to another location on the file system.</td></tr>
<tr><td colspan="1" valign="top">Copy</td><td colspan="1" valign="top">Marks a file or directory in order to copy it to another location on the file system.</td></tr>
<tr><td colspan="1" valign="top">Paste from</td><td colspan="1" valign="top"><p>The option should be active only if it is preceded by a Copy or Cut command, but not followed by a Paste command.</p><p>Moves or copies a file or directory to in the current file system directory.</p><p>- If moving a file or directory, moves the file or directory to the new destination.</p><p>- If it is a file copy, copies the file to the </p><p>&emsp;new destination.</p><p>- If it is a directory copy, copies the </p><p>&emsp;directory with its contents (recursively) </p><p>to the new destination. If there are ![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.007.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.008.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.009.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.010.png)</p><p>hidden files, these are also copied.</p><p>If during the copy a file or directory is to be overwritten, a modal box must be displayed through which the user can give consent or refuse the overwrite.</p><p>If the user refuses to overwrite a file or directory during the recursive process of copying the contents of a directory, the process stops.</p></td></tr>
<tr><td colspan="1">Rename</td><td colspan="1">Renames a file or directory. For the purpose of</td></tr>
<tr><th colspan="1" rowspan="4"></th><th colspan="1"></th><th colspan="1" valign="top">this opens a <i>modal box</i> containing a textfield which initially has the old name of the file or directory. The user can edit it and save the file or directory with the new name.</th></tr>
<tr><td colspan="1" valign="top">Delete</td><td colspan="1">Deletes a file or directory (with its contents). Before deleting, a Modal window appears asking for confirmation.</td></tr>
<tr><td colspan="1" valign="top">Add to Favourites</td><td colspan="1" valign="top">The current list is added to favorites after the last entry.</td></tr>
<tr><td colspan="1" valign="top">Properties</td><td colspan="1"><p>A modal window appears containing the following information about the file or directory selected by left- clicking, or the current directory if no file or directory is selected:</p><p>1. File or directory name</p><p>2. Full path to the file or directory</p><p>3. Space occupied in the filesystem. If it is a directory, the size refers to the entire contents of the directory.</p><p>4. The permissions for the specific file or directory. The permissions should be displayed with check boxes. If the user has permission to change them they are shown enabled, and if the user does not have permission to change them they are shown disabled. Once the user makes a change, it will be reflected in the permissions of the file.</p></td></tr>
<tr><td colspan="1" valign="top">Search</td><td colspan="1"></td><td colspan="1">The search function (text field and "Search" button) appears or disappears</td></tr>
</table>

**Path>To>Current>Directory or Breadcrumb**

The breadcrumb allows us to know where we are in the file system of our computer. It consists of the directory hierarchy starting from the beginning of the filesystem (C:\ for 

Windows and /  for Linux) up to the current directory. For example, if we are on a Windows  operating  system  and  in  the  C:\Users\tzoral\MyDocuments\  directory  the 

breadcrumb should be **C:> Users > tzoral > MyDocuments**. Each of the words **C:** , **Users** , **tzoral** is a link to the corresponding directory. **MyDocuments** is the current directory, so there is no reason to have a link to it, but it is not wrong if it is a link.

**The content of the current catalogue**

The  program  always  starts  from  the  user's  home  directory.  Browsing  must  be  allowed through the breadcrumb and in parent directories of the user's home directory.![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.011.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.012.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.013.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.014.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.015.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.016.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.017.png)

The content of the current directory consists of icons in a left-to-right order next to each other until the current line is full. When the current line is full it continues to the next line starting again from the left. The icons must be lined up next to each other and one below the other. If the  icons  do  not  fit  in  the  available  space  then  the  user  can  navigate  via  a  vertical JScrollPane  (not  horizontal  as  well).  If  the  window  is  resized  the  icons  are  adjusted accordingly.

Each icon corresponds to a file or directory in the file system. The icons are arranged in the following  order:  first  the  directories  appear  in  alphabetical  order,  then  the  files  again  in alphabetical order. ![ref1]![ref2]![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.020.png)![ref2]![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.021.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.022.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.023.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.024.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.025.png)![ref1]![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.026.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.027.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.028.png)Hidden files do not have to be displayed (it is not a mistake if you do). You are [given ready-made icons](https://drive.google.com/open?id=1SVW3euI--mpTUEftmS42d6jhm9vW6gJ2) so that depending on the type of file you can assign the appropriate icon. The mapping must be done so that the file extension and the icon name (removing the .png extension) coincide. Files whose extension does not match the given ones can be mapped to the question.png file. The icons should be inside the icons directory (exactly as given) in the root of your project. In order to access an icon it must be loaded from your program using the path **"./icons/<name>.png"**, where **<name>** is the name of the icon.

**Left / right click on an icon**

Left-clicking on an icon selects the file or directory corresponding to that icon, without doing anything else. The selection can be made by displaying a faint grey box around the icon.

Right-clicking on an icon displays a pop-up menu with the options provided by the Edit option of the main horizontal menu. The Paste option after right-clicking moves or copies the previously Copy or Cut file or directory to the current directory rather than the newly marked directory.

Left or right clicking on a space where there are no icons has no effect.

Double left-clicking does the following depending on the type of file or directory to which the icon corresponds. If it is a directory, then that directory is selected as the current directory.  Since,  the  current  directory  is  the  selected  directory,  the  current  directory content and breadcrumb areas are updated accordingly. If it is an executable file, then the file is executed without parameters. Otherwise, it opens the file with the program your system uses to open that file type.

**Favorites**

Favorites are a list of links under each other from directory names that are frequently used by the end user. Favorites must be stored in XML format in the file named **properties.xml** in the **.java-file-browser/** directory within the current user's home directory. If the directory and file do not exist they should be created when the end user saves a favorite destination. When the application is started, if this file exists, it should be read and load the favorites saved by the user.
At the start of the program there should be only one favorite directory entry. This directory is the user's home directory and need not be stored within the properties.xml file. This link is always located in the first record of the set of favorite links.

**Left and right click on Favorites**

Left-clicking moves the program to the directory the user selected as the current directory. Right-clicking displays a pop-up menu with the only option being that the user can delete the selected link from favorites. The deletion is immediate, without asking for a confirmation of deletion.

**Search**

The search form should appear or disappear depending on whether the user has clicked on the Search option in the horizontal menu. Initially the search option is disabled, if the user selects it once it will appear, and if the user selects it a 2nd time it will be disabled again.

The search is done as follows. The user can enter a keyword which is part of the name of a file. In addition to the keyword, the user can add a blank in the search string and immediately after the type:<type> option where <type> is the type of the file as specified in its suffix (suffix is the part of the name following the last dot, e.g. .jpg, .mp3, .docx, etc.). No distinction is made between uppercase and lowercase when searching. The special word dir for <type> refers to the exclusive search of directories with that name segment.

The search always starts from the current directory where the user is located.![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.035.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.036.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.037.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.038.png)![](Aspose.Words.b7f5000d-f582-4aa8-8a3f-f4371fa79f15.039.png)

The results of the search should be displayed in a vertical list with one entry per line, where for each file or directory that falls within the search criteria the full path to it will be displayed. Clicking on an icon in the list if it is an executable file will execute it, otherwise it will open with the preferred system application. If it is a directory it will become the current application directory.


1. When searching, the application should not "freeze" until the search is complete. You will need threads.
2. If you press the Search button once, it is renamed to Stop for as long as the search lasts. If the search is completed the button becomes Search again. If the Stop button is pressed the search is completed showing the results that match up to that point.

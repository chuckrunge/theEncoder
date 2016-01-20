# theEncoder
Swing-style desktop app for custom encoding of files

Copyright (C) 2016  Chuck Runge
Lombard, IL.
CGRunge001@GMail.com

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. 

*===============================================*
theEncoder
*===============================================*

theEncoder will encrypt files with a user-supplied password.  The password is enhanced internally, and used to encode the contents of the file.  The same password is used to decode the file.

The filename and filetype are replaced with a generic "encoder.out" file.  When the file is decoded, the user is responsible for remembering the password and restoring the file type.

Encoded files can be sent by email or ftp with a limited degree of privacy.  There are snoopers on any public network who watch your files go by, and encryption makes it much more difficult to read them.

The simplest way to bring up the user interface is to execute the batch file encoder.cmd.  The output file(s) will be created in the same folder with the command file and the jar file. 

USAGE

1. Press "Select Input", navigate to your file, and select it (press "Open").

2. Enter your password, passcode, or passphrase in the text box.  

3. Press "Encode".

A pop-up window will indicate that processing is complete.


Decode works almost the same way.

1. Press "Select Input", navigate to your encoded file, and select it (press "Open").

2. Enter the password, passcode, or passphrase that was used to encode the file in the text box.  

3. Press "Decode".

A pop-up window will indicate that processing is complete.


MULTIPLE FILES

Multiple files can be processed in one run.  If "Select Input" is pressed again after the first file is selected, one additional file will be added each time, and multiple output files will be created.
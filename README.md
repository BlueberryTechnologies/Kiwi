# Blueberry Tech Barcode Editor

![License](https://img.shields.io/github/license/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor)
![Version](https://img.shields.io/github/v/release/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor)
![Downloads](https://img.shields.io/github/downloads/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/total)
![Commits](https://img.shields.io/github/commit-activity/m/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor)

## Support

[![BuyMeACoffee](https://img.shields.io/badge/Buy%20Me%20a%20Coffee-ffdd00?style=flat&logo=buy-me-a-coffee&logoColor=black)](https://www.buymeacoffee.com/blueberrytech)

## Features

- Windows, MacOS and Linux Support
- Code printing
- Text Printing
- Image Printing
- Selectable code options
- Selectable Printers
- Custom path storage
- Code Previews

## Installation

This program supports Windows, Mac, and Linux Operating Systems. If you would like us to support another operating system please submit an issue [here](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/issues) with the enhancement tag letting us know.

You can install the program by following the steps outlined in the [documentation](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/wiki).

The only prerequisite is having a Java JRE which is typically packaged inside of a Java JDK. You can also follow the steps [here](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/wiki/Installation#jre) to install Java on Windows and [here](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/wiki/Installation#linux) on Linux.

The only platform that doesn't require Java is Mac because it is packaged inside of the .PKG file.

---

## Changelog

#### 1.1 (Coming Soon)

- Major UI changes.
  - Dark Mode / Light Mode
- Selectable printing sizes
- Confirm Box for printing
- Print previously generated barcode.

#### 1.0.9

- ##### New Features

  - Add the option to select size of generated codes.
  - Add the option to refresh the printer list.
  - Start of UI changes.

- ##### Bug Fixes
  - Can't generate a link because of invalid characters. [#19](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/issues/19)
  - The generated code doesn't update correctly on the preview. [#27](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/issues/27)

#### 1.0.8

- ##### New Features

  - Add Official Windows Support
  - Organize code
    - Organize groups of features in UI
      - Frame
      - Panel
      - Buttons
      - Menu
    - Also organize generator class
      - Code generation
      - Image generation
      - Setters
      - Getters
    - Comment Code
      - If anyone wants to fork, the code is fully commented so it's easier to understand.

- ##### Bug Fixes

  - Fixed an issue printing text and images [#3](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/issues/3)
  - Changed Printer Resources to point to correct webpage. [#4](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/issues/4)
  - Fixed an issue with UI not reopening after code preview. [#15](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/issues/15)
  - Fixed incorrect version in the about menu. [#18](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/issues/18)
  - Added Windows Support [#16](https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/issues/16)

#### 1.0.7

- Add Official Mac Support (yay!)
- Remember last selected printer
- Code Preview Menu
  - Allows you to preview the code before you print.

#### 1.0.6

- Finish Printer Selecting
  - User can now print to different printers on the network (See docs)
  - Added printer dropdown and printer menu
- UI Changes
  - Modified image selection and notifiers.
  - Changed default application size.
  - Changed various text elements.
  - Changed settings menu + added about menu.
  - Added current version to about section (probably should have added earlier)

#### 1.0.5

- Printer Selecting (Started)
- Various Fixes

#### 1.0.4

- Fixed multiple printing issues
- Fixed multiple issues with file writing and directories
- Removed useless pieces of code.
- Added multiple warnings / error catching

#### 1.0.3

- Condensed code
- It's broken now

#### 1.0.2

- Removed useless folders

#### 1.0.1

- Added the ablility to choose default and custom directories for the codes to be stored in.

#### 1.0.0

- Code generation
- Printing to printers
- UI

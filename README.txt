# Space Chase 🚀

Welcome to the Space Chase repository! Below is an outline of how to install and run this application, developers can also find documentation on cloning this repository, file structures, pull requests and checkstyle configurations here.


# Build and Run

If you would like to run the most stable build then clone the release branch, cd into the top-level project directory and run the following command:

    mvn clean javafx:run

> **Note:** This Javafx project was built with **Java 17** and **maven version 3.8.6**. If you experience issues running this project you should check your java and maven versions.

> **Note:** This software has been optimised for **Windows**. Unexpected behaviours such as problems with sound may occur when running on alternative platforms.


# Developer Information

The following sections provide important information that developers may want to familiarise themselves with.

## Cloning this repository
You can clone this repository by pressing the clone button in the repository. Copy everything from and including https://. **Do not include git clone unless you are using git from the command line**.

In Intelij you can create a new project by **selecting "Get from VCS"** in the welcome window, you will then be prompted to enter your bitbucket login details.

## Getting Bitbucket login details
Go to: https://bitbucket.org/account/settings/

your username is under "Bitbucket profile settings" or you can use your email.

You may need to generate an app password.

Go to: https://bitbucket.org/account/settings/app-passwords/

Select "Create App Password" and follow the prompts on the screen. Save this password somewhere safe in case you need to use it again.

## Folder structure

The project folders have been divided into the following project tree:
```
📦 src
└─ main
   ├─ java
   │  └─ com.example.spacechase
   └─ resources
      └─ com.example.spacechase
         ├─ data
         │  ├─ levels
         │  └─ profiles
         ├─ fonts
         ├─ fxml
         ├─ html
         ├─ images
         └─ licenses
```

> **note**: You can create new folders in the resources.com.example.spacechase directory. **Do not create new folders in java.com.example.spacechase as this is prohibited in maven.**

## Branch naming conventions

Sub-branches should be named appropriately. The name should be like this: **feature**_name_of_jira_story, if it is a **new** feature (aka a Story) or **improvement**_name_of_jira_story **if you are making changes to existing code**.

## Making a pull request

You should make a pull request if you have finished a feature or need help.

1. Find this project in the bitbucket repository.
2. Select Pull request (menu to the left of the screen).
3. Select Create pull request.
4. Add an appropriate title and description of what you have done.

If you are making a pull request because you need help, please put HELP in the Title.

> **Note:** The Source Branch is **your feature branch**, and the Destination Branch is The destination of where you would like to add your code to **typically this is the release branch** but it can be whichever branch is most suitable at the time.

## Using Checkstyle

The Group 40 checkstyle file has been provided for you in the top level project directory. You can download the checkstyle plugin to gather real time feedback while you code.

> **Note:** You may only need to do step 3, so **check step 3 first** before attempting steps 1 and 2

1. Download the checkstyle plugin:
   > File -> Settings -> Plugins -> Marketplace -> CheckStyle-IDEA
2. Add the checkstyle file to the plugin:
   > File -> Settings -> Tools -> Checkstyle -> Add Configuration File (Plus icon) -> Description = Group 40 Checkstyle -> Select "Use a local Checkstyle file" and paste the file path to the checkstyle -> Select Next -> Select Next again (You don't need to input anything into the fields) -> Select Finish -> Select Apply -> Select OK
3. Activate the Checkstyle:
   > Select Checkstyle (Button near bottom of the screen) -> Set active configuration to Group 40 Checkstyle

## FAQ
### My commits are using my personal email instead of my uni email

If your commits on Bitbucket are using your personal email instead of your university email, it is likely that you have used git before on your computer and need to update your email address so it displays properly on Bitbucket.

You can do this without changing your default git email.

> Follow the steps under **"Setting your email address for a single repository"** on this website: [Click Me](https://docs.github.com/en/account-and-profile/setting-up-and-managing-your-personal-account-on-github/managing-email-preferences/setting-your-commit-email-address#setting-your-commit-email-address-in-git)

### What is the difference between "Commit" and "Commit and Push" in IntelliJ?
- A commit is when you save changes locally.
- Push is when you upload all of your commits to the server.

So in that respect, if we always select "Commit and Push" we are making a commit and then pushing all our commits to the server.

> **TL;DR:** We must expect to be making many commits (as often as needed) over the course of a programming session. **You should select "Commit" and at the end of your programming session select Git -> Push.** Using "Commit and Push" all the time is slow as you have to wait for files to be uploaded to the server every time it's pressed.

### Help! My pull request has a merge conflict
As the code author you are responsible for resolving merge conflicts. It is a very simple process and I will walk you through it step by step again here.

Remember merge conflicts must be resolved on your local machine, In IntelliJ:

1. Select Git -> Pull. You should choose **origin** and **name_of_your_branch** to update your local copy to the latest version of the remote branch.
2. Select Git -> Pull. You should choose **origin** and **release** to cause the merge conflict on your local machine.
3. The conflict window will open, if you can't decide on whose code to accept and whose to delete, press the **Merge** button. A window will open with your changes, their changes and a fully functioning editor, you can make your edits in this window.
4. Finally, commit and push your changes to your feature branch.
5. If the merge conflict is resolved the warning dialog will be removed from the pull request.

> **Note:** When a merge conflict is issued, git will completely give up on attempting to merge any changes in the file. **You must remember to also accept all the non-conflicting changes**.

> If you need more help, support is available [here](https://www.jetbrains.com/help/idea/resolving-conflicts.html#distributed-version-control-systems) on the IntelliJ website under **Distributed Version Control Systems**.

> **If after completing the above you are still having problems, cancel the merge by selecting Git -> Cancel Merge and contact a code reviewer.**
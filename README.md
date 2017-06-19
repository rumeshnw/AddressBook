# Address Book

This system user Gradle build system(version 3.1). However, to use this application or run any of the below commands, you do not need Gradle to be installed.

**Pre-requisite**:
- In order to execute use the system Java 8 should be installed.

**Commands**


1. Run Unit Tests
    - Unix      : _./gradlew clean test_
    - Windows   : _gradlew clean test_
    
2. Run Integration Tests
   - Unix      : _./gradlew clean integrationTest_
   - Windows   : _gradlew clean integrationTest_
       
3. Run Code Coverage
   - Unix      : _./gradlew clean test integrationTest jacocoTestReport_
   - Windows   : _gradlew clean test integrationTest jacocoTestReport_
       
4. Run System
    - Unix      : _./gradlew clean run_
    - Windows   : _gradlew clean run_


Once you execute the Run System command, system will boot up and show list of options with following operations,

1. Create new Address Book
2. Add new Contact
3. Delete existing Contact
4. Show all Contacts of an Address Book
5. Show all unique Contacts of all Address Books
99. Create new Address Book

Then you can enter respective option number to interact with the system.

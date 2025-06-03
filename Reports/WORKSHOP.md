# JaCoCo and PIT Reports Demo

## Prerequisites

- Java and Maven installed  
  If Maven is not installed, follow the instructions here: [https://maven.apache.org/install.html](https://maven.apache.org/install.html)

## Steps

1. **Clone the repository**  
   Navigate to the `Reports/demo` folder:
   ```bash
   git clone <your-repo-url>
   cd Reports/demo
   ```

2. **Run tests with JaCoCo and PIT**  
   Open the console and run the following command:
   ```bash
   mvn clean test jacoco:report org.pitest:pitest-maven:mutationCoverage
   ```

3. **View the reports**  
   After running the command, you can find the reports here:
   - JaCoCo Code Coverage: `target/site/jacoco/index.html`
   - PIT Mutation Report: `target/pit-reports/index.html`

4. **Modify test coverage levels**
   - Update the tests to achieve **100% code coverage** and **100% mutation score**.
   - Update the tests to have **around 30% code coverage** and a **mutation score below 50%**.
   - Remove tests entirely and observe the impact on both reports.

## Example Reports

You can find some sample reports in the `example` folder.
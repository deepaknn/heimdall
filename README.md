<p align="center" width="100%"><img src="logo.png" width="180" height="250"></p>
<h1 align="center">Heimdall - All Seeing and All Hearing</h1>

## Monitoring and Alerting Project for Spring Boot
### Heimdall is a monitoring and alerting aspect oriented programming library for Spring Boot applications. It allows you to log method calls, capture method arguments and return values, and send email alerts for errors. With Heimdall, you can monitor your application's behavior and receive notifications about critical issues in real-time.

## Features
1. Method Call Logging: Monitor method calls in your Spring Boot application and log detailed information about each invocation.
2. Error Alerting: Receive email alerts for exceptions and errors occurring in your application, enabling proactive issue detection and resolution.
3. Customizable Configuration: Easily configure email settings, logging preferences, and other aspects of Heimdall to suit your application's requirements.
4. Asynchronous Logging: Utilize a shared log queue manager for asynchronous logging, ensuring minimal impact on application performance.
Setup

## To use Heimdall in your Spring Boot application, follow these steps:
Add Heimdall as a dependency in your pom.xml or build.gradle file. Build the code from this repo in your workspace and add the dependency as below:<br>
`<dependency>`
  <br>`<groupId>com.deepaknn.aop</groupId>`
  <br>`<artifactId>heimdall</artifactId>`
  <br>`<version>0.0.1-SNAPSHOT</version>`
  <br>`<scope>compile</scope>`
<br>`</dependency>`

Configure the email settings and logging preferences in your application properties file as below:
  <br>`#configure your host, for e.g. smtp.elasticemail.com`
  <br>`email.host=<your host>`
  <br>`#configure port, for e.g. 2525`
  <br>`email.port=<your port>`
  <br>`email.username=<your username here>`
  <br>`email.password=<your password here>`
  <br>`email.from = system@example.com`
  <br>`email.to=support1@example.com,support2@example.com`
  <br>`email.subject=Error Alert`
  <br>`#configure your queue size, old logs from the queue will get polled if it reaches the size. Default is 1000 `
  <br>`log.queue.size=100`
  <br>`#configure as true if method logs needs to be printed to standard output. Default is false`
  <br>`log.print=true` 

Annotate classes with @EnableHeimdall to enable monitoring and alerting as below:
<br>`@RestController`
<br>`@EnableHeimdall`
<br>`public class ExampleController {`

    @GetMapping("/example")
    public String exampleMethod() {
        // Your method logic here
        return "Example Response";
    }
`}`<br>
In this example, the exampleMethod is monitored by Heimdall, method will be looged and an email alert will be sent if any exceptions occur during its execution.

Build and run your Spring Boot application.

## Usage
Once Heimdall is integrated into your project, it automatically monitors method calls and sends email alerts for errors. You can customize its behavior by adjusting the configuration settings and annotating specific classes or methods.

## Contributing
Contributions to Heimdall are welcome! If you encounter any issues or have ideas for improvements, please submit a pull request or open an issue on our GitHub repository.

## Contact
For questions, feedback, or support, please contact deepaknnofficial@gmail.com or visit our GitHub repository https://github.com/deepaknn/heimdall for additional resources.

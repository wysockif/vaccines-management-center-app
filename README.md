<img src="https://raw.githubusercontent.com/wysockif/vaccines-management-center-app/master/docs/scr/logo.png" alt="Vaccines management center app" style="height: auto !important;width: auto !important;" >

![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/wysockif/vaccines-management-center-app)

---

### Table of Contents
* [About](#about)
* [Motivation and project status](#motivation-and-project-status)
* [Technologies used](#technologies-used)
* [Input file](#input-file)
* [Output file](#output-file)
* [Requirements](#requirements)
* [Setup and usage](#setup-and-usage)
* [Tests](#tests)
* [Licence](#licence)

---

### About

Vaccines management center (VMC) app is a console application, that enables optimizing the total costs of distributing vaccines. The application solves the <a href="https://en.wikipedia.org/wiki/Minimum-cost_flow_problem">minimum-cost flow problem</a>. 

The project uses the combination of algorithms below to work properly:

* <a href="https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm">Bellmann-Ford algorithm</a> - computing the shortest path;
* <a href="https://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm">Ford-Fulkerson algorithm</a> - computing the maximum flow in a flow network.



---

### Motivation and project status

VMC app was created during my second year of studying Computer Science. It was my individual project for _Algorithms And Data Structures_ subject.



The project was completed successfully. 

Duration time: 05.11.2020 - 03.12.2020.

---

### Technologies used
* Java (13);
* Maven;
* Git;
* IntelliJ.

---

### Input file

Exemplary input file:

```txt
# Producers of vaccines (id | name | daily production)
0 | BioTech 2.0 | 900
1 | Eko Polska 2020 | 1300
2 | Post-Covid Sp. z o.o. | 1100
# Pharmacies (id | name | daily demand)
0 | CentMedEko Centrala | 450
1 | CentMedEko 24h | 690
2 | CentMedEko Nowogrodzka | 1200
# Connections (producer's id | pharmacy's id | max number of provided vaccines per day | cost of vaccine [zł] )
0 | 0 | 800 | 70.5
0 | 1 | 600 | 70
0 | 2 | 750 | 90.99
1 | 0 | 900 | 100
1 | 1 | 600 | 80
1 | 2 | 450 | 70
2 | 0 | 900 | 80
2 | 1 | 900 | 90
2 | 2 | 300 | 100
```
The application is prepared also for not valid input file - displaying the error messages.

---

### Output file

The output file will be generated by the application. It will show which producer should supply specified pharmacy and how many vaccines it should provide.

```txt
Post-Covid Sp. z o.o. -> CentMedEko Centrala [Koszt = 450 * 80.0 = 36000.0 zł]
BioTech 2.0           -> CentMedEko 24h [Koszt = 450 * 70.0 = 31500.0 zł]
Eko Polska 2020       -> CentMedEko 24h [Koszt = 240 * 80.0 = 19200.0 zł]
BioTech 2.0           -> CentMedEko Nowogrodzka [Koszt = 450 * 90.99 = 40945.5 zł]
Eko Polska 2020       -> CentMedEko Nowogrodzka [Koszt = 450 * 70.0 = 31500.0 zł]
Post-Covid Sp. z o.o. -> CentMedEko Nowogrodzka [Koszt = 300 * 100.0 = 30000.0 zł]
Opłaty całkowite: 189145.5 zł
```

---

### Requirements

To run the application you need to have installed JRE 13. 

To build a JAR by yourself you need also Maven.

---




### Setup and usage

1.  Clone this repository:

    ```bash
    git clone git@github.com:wysockif/vaccines-management-center-app.git
    ```

2.  Change your directory to `vaccines-management-center-app/app` and build the JAR file:

    ```bash
    mvn clean install
    ```

3.  Run the application:

    ```bash
    java -jar target/vaccines-management-center-app-1.0-SNAPSHOT.jar path1/inputfile.txt path2/outputfile.txt
    ```
    `path1/inputfile.txt` - where is the input file? 


    `path2/outputfile.txt` - where should the app save the output file?

    If your input file has more than 1000 pharmacies or 1000 producers (1000000 connections) you need to add `-upper_limit=false` at the end of this command.

---

### Tests

1. Run all (62) tests:

    ```bash
    mvn test
    ```
    The tests were created with JUnit 4 and AssertJ.

---


### License
Usage is provided under the [MIT License](http://opensource.org/licenses/mit-license.php). See LICENSE for the full details.

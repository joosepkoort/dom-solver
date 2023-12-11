# Dragons Of Mugloar solver

A brief description of what this project does and who it's for

## Guide

### Prerequisites

- Java 17 or higher
- Gradle 8.5 or higher

### Installation

1. Clone the repository
   ```sh
   git clone https://github.com/joosepkoort/dom-solver.git
   ```
2. Build the project
   ```sh
   ./gradlew build
   ```
3. Run the program (with examples)
   ```sh
   # run with prod profile (specified):
   java -jar "-Dspring.profiles.active=prod" .\build\libs\dom-solver-1.0.0-SNAPSHOT.jar start 2
   # run with default profile:
   java -jar .\build\libs\dom-solver-1.0.0-SNAPSHOT.jar start 10   
   ```

### Profiles
###### profiles available: default, prod
###### parameters available: -start {number of games to play}

### Logs
###### in case of prod profile, logs will be in ./logs/prod folder
###### in case of default profile, logs will be in ./logs/dev folder. (by default turned off - can be turned on using properties file)

### Statistics
###### in case of prod profile, statistic images will be in ./statistics/prod folder
###### in case of default profile, logs will be in ./statistics/dev folder
##### What is displayed in statistics?: 
* The amount of gold over time.
# ![1RK12qJp_gold_chart](https://github.com/joosepkoort/dom-solver/assets/7001273/218ebc5b-ab48-48fa-aeee-1cf45bec61f3)
* The score over time.
# ![1RK12qJp_score_chart](https://github.com/joosepkoort/dom-solver/assets/7001273/fcd3e670-de92-46ff-8b45-eba32d65bc16)
* The difficulty of the quest over time.
# ![1RK12qJp_difficulty_chart](https://github.com/joosepkoort/dom-solver/assets/7001273/0c65af44-b6e0-4896-80c5-6cecc4f85c6b)


### Contributing

Contributions are always welcome!

## License

Distributed under the MIT License. See `LICENSE` for more information.
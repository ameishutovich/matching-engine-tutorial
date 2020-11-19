## Install java
### Windows

1. download java 8
```
https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
```
2. run the downloaded installer
3. add JDK "bin" directory to the PATH environment variable
4. make sure java is installed and added to PATH environmental variable
```
java -version
```

### MacOS
1. install homebrew
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
```

2. install java 8
```
brew tap adoptopenjdk/openjdk
brew cask install adoptopenjdk8
```
or if later fail you can try
```
brew cask install adoptopenjdk/openjdk/adoptopenjdk8
```
3. make sure java is installed and added to PATH environmental variable
```
java -version
```

### Linux
1. add Oracle's PPA, then update your package repository.
```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
```
2. install java
```
sudo apt-get install oracle-java8-installer
```
if you have multiple java installations you can configure default version
```
sudo update-alternatives --config java
```
choose the number to use as a default

3. copy the path from preferred (1.8) installation and then open /etc/environment
```
sudo nano /etc/environment
```

4. add the following line with your own copied path to the file
```
JAVA_HOME="COPIED PATH"
```

5. make sure java is installed and added to PATH environmental variable
```
java -version
```


## Install Maven

### Windows
1. Make sure java is installed and added to PATH environmental variable
```
$ java -version
```

2. Download zip file with maven
    - https://apache.mirrors.nublue.co.uk/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip

3. Unpack it in chosen location

4. Add both `M2_HOME` and `MAVEN_HOME` variables to the Windows environment using system properties, and point it to your Maven folder.

5. Add `%M2_HOME%\bin` to the `PATH` environment variable

6. check everything works
```
$ mvn -version
```

### MacOS
1. Make sure java is installed and added to PATH environmental variable
```
$ java -version
```

2. Install homebrew
```
$ /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
```

3. Install maven
```
$ brew update
$ brew install maven@3.6
```

4. Configure .bash_profile
```
$ export M2_HOME=/usr/local/Cellar/maven/3.6.3_1/libexec
$ export M2=${M2_HOME}/bin
$ export PATH=${PATH}:${M2_HOME}/bin
```

5. Check everything works
```
$ mvn -v
```

### Linux
1. make sure java is installed and added to PATH environmental variable
```
$ java -version
```

2. Install maven
```
$ cd /opt/
$ wget http://apache.mirrors.pair.com/maven/maven-3/3.6.3/binaries/$ apache-maven-3.6.3-bin.tar.gz
$ sudo tar -xvzf apache-maven-3.6.3-bin.tar.gz
$ sudo mv apache-maven-3.6.3 maven
```

3. Configure .bashrc
```
$ export M2_HOME=/opt/maven
$ export PATH=${M2_HOME}/bin:${PATH}
```

4. Check everything works
```
$ mvn -v
```

## Install NodeJS

### Windows
1. Download and Install [NodeJS](https://nodejs.org/en/download/)
2. Check if node and npm are installed
```
$ node -v
$ npm -v
```

### MacOS
1. Install HomeBrew (If not installed already)
```
$ /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
```

2. Install NodeJS
```
$ brew update
$ brew install node
```

3. Check if node and npm are installed
```
$ node -v
$ npm -v
```

### Linux
1. Install NodeJS
```
$ curl -sL https://deb.nodesource.com/setup_12.x | sudo -E bash -
$ sudo apt install nodejs
```
2. Check if node and npm are installed
```
$ node -v
$ npm -v
```

## Running the matching application
```
./start.sh
```

## Example of matching request to backend:
```
curl -X POST localhost:8000/api/matching -d '{"leftSide": [["5", "value"], ["45", "value2"]], "rightSide": [["99", "value3"], ["5", "value"]]}'
```
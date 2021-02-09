# Root Driver History Calculator
Problem Statement
Let's write some code to track driving history for people.

The code will process an input file. You can either choose to accept the input via stdin (e.g. if you're using Ruby cat input.txt | ruby yourcode.rb), or as a file name given on the command line (e.g. ruby yourcode.rb input.txt). You can use any programming language that you want. Please choose a language that allows you to best demonstrate your programming ability.

Each line in the input file will start with a command. There are two possible commands.

The first command is Driver, which will register a new Driver in the app. Example:

    Driver Dan

The second command is Trip, which will record a trip attributed to a driver. The line will be space delimited with the following fields: the command (Trip), driver name, start time, stop time, miles driven. Times will be given in the format of hours:minutes. We'll use a 24-hour clock and will assume that drivers never drive past midnight (the start time will always be before the end time). Example:

    Trip Dan 07:15 07:45 17.3

Discard any trips that average a speed of less than 5 mph or greater than 100 mph.

Generate a report containing each driver with total miles driven and average speed. Sort the output by most miles driven to least. Round miles and miles per hour to the nearest integer.

Example input:

    Driver Dan
    Driver Lauren
    Driver Kumi
    Trip Dan 07:15 07:45 17.3
    Trip Dan 06:12 06:32 21.8
    Trip Lauren 12:01 13:16 42.0
Expected output:

    Lauren: 42 miles @ 34 mph
    Dan: 39 miles @ 47 mph
    Kumi: 0 miles

Install:

    Have java 8 installed

How to execute?

    1. Build the project

    2. Run the main class DriverHistoryCalculator.java for example java DriverHistoryCalculator file_name

What is the process?

    1.  Before coding, I looked into the problem statement and identified 2 entities namely Driver and Trip. 

        There is 1-many relationship between driver and trips ie., each trip is associated to a driver and driver can take multiple trips.  To keep this relationship intact there is an attribute in Driver entity which captures list of valid Trips driver has taken. Similarly, trip contains an attribute called 'driver-name' which tells about the driver who has taken the trip.

    2. Driver entity have following instance variables
        1. List of trips
        2. miles driven by driver
        3. total time driver has driven
        
    3. Trip entity have following instance variables
        1. driver name
        2. start time of trip
        3. end time of trip
        4. miles driven
        
    4. We  add a trip to trips taken by driver only when trip is valid and while adding trip, increment miles driver has driven by trip miles amount and time travelled to trip time amount. (calculated on fly). To facilite this addTrip or addTrips to driver there are 2 methods in Driver entity namely addTrip(), addTrips().
    
Construction of entities from the input and code flow?

    0. We store two maps 
        a) fetch drivers by driverName and fetch List<Trips> by driver name. Reason we are maintaining two maps is that we dont want to missout info on drivers/trips, example in file input if we have trips and then driver.
    
    1. Pass the input file path as a command line argument. If file is not found then FileNotFoundException is thrown.
    
    2. Scan through the file line by line and for each line scanned create driver and trip entity based on the regex. If the scanned line is invalid throw IllegalArgumentException.
    
    3. If line scanned is a driver add a driver entity to driversMap if not exists. If driver exists then throw DuplicateDriverException
    
    4. If scanned line is a trip, validate if the averageSpeed of the trip matches the constraints given in the problem statement (valid trip).  If the trip is valid then add it to driversTripMap and also add it to trips taken by driver by updating driver entity in DriversMap.
       When we add any trip to driver we update milesdriven, totaltimeTravelledInSex
    
    5. Finally throw InvalidDriverException exception if there is any trip where driver is not registered (not given as driver in input).

    6. Once the whole file is iterated, sort the drivers map based on the average speed and show the result as mentioned in problem statement.

Custom Exceptions:

    1. InvalidTripException - thrown when a trip is invalid

    2. InvalidDriverException - thrown if the Driver has not been registered

    3. DuplicateRequestException - Run time exception when duplicate requests come.

Custom Sorter:

    Sort the drivers by the miles driven

Sample input files:
    
    The tst-resources folder under src/test contains the files to test various scenarios.

Run Tests:

    The test folder under src contains all the unit test cases.

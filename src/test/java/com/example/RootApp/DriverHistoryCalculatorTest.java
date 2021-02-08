package com.example.RootApp;

import com.example.RootApp.entity.Trip;
import com.example.RootApp.exceptions.InvalidDriverException;
import com.example.RootApp.exceptions.InvalidTripException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
public class DriverHistoryCalculatorTest {

    @Test
    public void testDriverHistoryWithValidInputs() throws FileNotFoundException {
        String filePath[] = {"src/test/tst-resources/validTestFile"};
        DriverHistoryCalculator.main(filePath);
    }

    @Test
    public void testDriverHistoryWithTripStartDateAfterEndDate() {
        String filePath[] = {"src/test/tst-resources/invalidTestWithEndTimeLessThanStart"};
        String message = "Start time cannot be before end time";
        Exception exception = Assertions.assertThrows(InvalidTripException.class, () -> {
            DriverHistoryCalculator.main(filePath);
        });
        Assert.assertEquals(exception.getMessage(), message);
    }

    @Test
    public void testDriverHistoryWhenTripWithInvalidDriver() {
        String filePath[] = {"src/test/tst-resources/invalidTestWithDriverMissing"};
        String message = "Drivers who aren't registered took trips";
        Exception exception = Assertions.assertThrows(InvalidDriverException.class, () -> {
            DriverHistoryCalculator.main(filePath);
        });
        Assert.assertEquals(exception.getMessage(), message);
    }
}

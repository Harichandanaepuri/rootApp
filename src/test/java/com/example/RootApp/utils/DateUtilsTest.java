package com.example.RootApp.utils;

import com.example.RootApp.DriverHistoryFileReader;
import com.example.RootApp.exceptions.DuplicateRequestException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
public class DateUtilsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetDate() {
        Date actualDate = DateUtils.getDate(10, 20, 30);
        Date expectedDate = new Date(0);
        expectedDate.setHours(10);
        expectedDate.setMinutes(20);
        expectedDate.setSeconds(30);
        Assert.assertThat(actualDate, Matchers.is(expectedDate));
    }

    @Test
    public void testIsAfterWhenStartDateIsAfterEndDate() {
        assertTrue(DateUtils.isAfter(new Date(10), new Date(8)));
    }

    @Test
    public void testIsAfterWhenStartDateIsBeforeEndDate() {
        assertFalse(DateUtils.isAfter(new Date(4), new Date(8)));
    }

    @Test
    public void testGetDateWithRegex() {
        String time = "11:22:33";
        Date expectedDate = DateUtils.getDate(time, ":");
        Date actualDate = getDate(11, 22, 33);
        assertThat("Dates should match", actualDate, Matchers.is(expectedDate));
    }

    @Test
    public void testGetDateWithRegexNotMatching() {
        String time = "11:22:33";
        String regex = " ";
        Exception exception = Assertions.assertThrows(NumberFormatException.class, () -> {
            DateUtils.getDate(time, regex);
        });
    }

    private Date getDate(int hours, int minutes, int seconds) {
        Date date = new Date(0);
        date.setHours(hours);
        date.setMinutes(minutes);
        date.setSeconds(seconds);
        return date;
    }
}

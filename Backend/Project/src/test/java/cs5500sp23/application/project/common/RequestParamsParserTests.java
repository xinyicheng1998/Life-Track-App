package cs5500sp23.application.project.common;

import static org.junit.jupiter.api.Assertions.*;

import cs5500sp23.application.project.server.common.RequestParamsParser;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class RequestParamsParserTest {

  @Test
  void testGetPersonID() {
    String id1 = "123";
    String id2 = "000456";
    String id3 = "000000789";
    assertAll(() -> assertEquals("123", RequestParamsParser.getPersonID(id1)),
        () -> assertEquals("456", RequestParamsParser.getPersonID(id2)),
        () -> assertEquals("789", RequestParamsParser.getPersonID(id3)),
        () -> assertThrows(IllegalArgumentException.class, () -> RequestParamsParser.getPersonID("123abc")),
        () -> assertThrows(IllegalArgumentException.class, () -> RequestParamsParser.getPersonID("")));
  }

  @Test
  void testGetDays() {
    String days1 = "1";
    String days2 = "10";
    String days3 = "100";
    assertAll(() -> assertEquals(1L, RequestParamsParser.getDays(days1)),
        () -> assertEquals(10L, RequestParamsParser.getDays(days2)),
        () -> assertEquals(100L, RequestParamsParser.getDays(days3)),
        () -> assertThrows(IllegalArgumentException.class, () -> RequestParamsParser.getDays("-1")),
        () -> assertThrows(IllegalArgumentException.class, () -> RequestParamsParser.getDays("abc")));
  }

  @Test
  void testGetLocalDate() {
    String date1 = "20220101";
    String date2 = "20220131";
    String date3 = "20220228";
    assertAll(() -> assertEquals(LocalDate.of(2022, 1, 1), RequestParamsParser.getLocalDate(date1)),
        () -> assertEquals(LocalDate.of(2022, 1, 31), RequestParamsParser.getLocalDate(date2)),
        () -> assertEquals(LocalDate.of(2022, 2, 28), RequestParamsParser.getLocalDate(date3)),
        () -> assertThrows(IllegalArgumentException.class, () -> RequestParamsParser.getLocalDate("2022/01/01")),
        () -> assertThrows(IllegalArgumentException.class, () -> RequestParamsParser.getLocalDate("2022-13-01")));
  }

}
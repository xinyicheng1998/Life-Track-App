package cs5500sp23.application.project.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class EntryKeyTest {

  @Test
  public void testGetKey() {
    assertEquals("summary", EntryKey.SUMMARY.getKey());
    assertEquals("lastUpdate", EntryKey.LAST_UPDATE.getKey());
    assertEquals("dateID", EntryKey.DATE_ID.getKey());
    assertEquals("personID", EntryKey.PERSON_ID.getKey());
    assertEquals("caloriesIdle", EntryKey.CAL_IDLE.getKey());
    assertEquals("segments", EntryKey.SEGMENTS.getKey());
  }

}
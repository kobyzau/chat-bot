package by.kobyzau.bot.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

public class DateUtil {

  public static boolean equals(LocalDate d1, LocalDate d2) {
    if (d1 == null && d2 == null) {
      return true;
    }
    if (d1 == null || d2 == null) {
      return true;
    }
    return d1.isEqual(d2);
  }

  public static LocalDate now() {
    return LocalDate.now(minskZone());
  }

  public static LocalDateTime currentTime() {
    return LocalDateTime.now(minskZone());
  }

  public static boolean sleepTime() {
    int hour = currentTime().getHour();
    return hour < 7 || hour == 23;
  }

  public static ZoneId minskZone() {
    return ZoneId.of("UTC+3");
  }

  public static Optional<LocalDate> parseDate(String date) {
    if (StringUtil.isBlank(date)) {
      return Optional.empty();
    }
    String[] parts = date.split("\\.");
    if (parts.length != 3) {
      return Optional.empty();
    }
    try {
      return Optional.of(
          LocalDate.of(
              Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0])));
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}

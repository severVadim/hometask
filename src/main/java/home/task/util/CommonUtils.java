package home.task.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    @SneakyThrows
    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        properties.load(CommonUtils.class.getResourceAsStream(fileName));
        return properties;
    }

    @SneakyThrows
    public static String getJsonStringFromFile(String filePath) {
        return new String(CommonUtils.class.getResourceAsStream(filePath).readAllBytes());
    }


    @SneakyThrows
    public static <T> T parseJsonIntoObject(String jsonBody, Class<T> tClass) {
        return OBJECT_MAPPER.readValue(jsonBody, tClass);
    }

    @SneakyThrows
    public static <T> List<T> parseJsonIntoObjectsList(String jsonBody, Class<T> tClass) {
        return OBJECT_MAPPER.readValue(jsonBody, OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, tClass));
    }


    public static List<String> getDatesFromNowToN(String timeZone, int nDays) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        List<String> dates = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < nDays; i++) {
            dates.add(dateFormat.format(c.getTime()));
            c.add(Calendar.DATE, 1);
        }
        return dates;
    }
}

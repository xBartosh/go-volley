package pl.go.volley.govolley.protocol.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PathsToProtocols {
    private static final String GENERATED_PROTOCOLS = "generated-protocols/";
    private static final String MERGED_PROTOCOLS = "merged-protocols/";
    public static final String PATH_TO_PROTOCOLS_FOLDER = "src/main/resources/static/protocols/";
    public static final String PATH_TO_GENERATED_PROTOCOLS_FOLDER = PATH_TO_PROTOCOLS_FOLDER + GENERATED_PROTOCOLS;
    public static final String PATH_TO_MERGED_PROTOCOL_FOLDER = PATH_TO_PROTOCOLS_FOLDER + MERGED_PROTOCOLS;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss-SSS");

    public static String createPathToGeneratedProtocolsFolder(LocalDateTime time) {
        return PathsToProtocols.PATH_TO_GENERATED_PROTOCOLS_FOLDER + String.format("%s/", time.format(DATE_TIME_FORMATTER));
    }

    public static String createPathToMergedProtocolFile(LocalDateTime time){
        return PathsToProtocols.PATH_TO_MERGED_PROTOCOL_FOLDER + String.format("merged-%s.pdf", time.format(DATE_TIME_FORMATTER));

    }


}

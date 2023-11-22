package com.belhard.resourceservice.service.impl;

import com.belhard.resourceservice.service.MetaDataService;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;

@Slf4j
@Service
public class MetaDataServiceImpl implements MetaDataService {

    private static final String LENGTH_FORMAT = "%02d:%02d:%02d";

    @Override
    public MetaDataDto getMetaData(byte[] data) {
        try {
            Metadata metadata = new Metadata();
            Mp3Parser parser = new Mp3Parser();
            parser.parse(new ByteArrayInputStream(data), new BodyContentHandler(), metadata, new ParseContext());
            return createMetaDataDto(metadata);
        } catch (IOException | SAXException | TikaException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private MetaDataDto createMetaDataDto(Metadata metadata) {
        MetaDataDto metaDataDto = new MetaDataDto();
        metaDataDto.setTitle(metadata.get("dc:title"));
        metaDataDto.setArtist(metadata.get("xmpDM:artist"));
        metaDataDto.setAlbum(metadata.get("xmpDM:album"));
        String releaseDate = metadata.get("xmpDM:releaseDate");
        metaDataDto.setYear(releaseDate == null ? null : Integer.parseInt(releaseDate));
        metaDataDto.setLength(getPrettyDuration(metadata.get("xmpDM:duration")));
        return metaDataDto;
    }

    private static String getPrettyDuration(String rawDuration) {
        long length = (long) Double.parseDouble(rawDuration);
        Duration duration = Duration.ofSeconds(length);
        return String.format(LENGTH_FORMAT, duration.toHours(), duration.toMinutes(), duration.toSeconds());
    }
}

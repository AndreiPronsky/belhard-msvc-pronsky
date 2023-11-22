package com.belhard.resourceservice.service.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class MetaDataDto {
    private String title;
    private String artist;
    private String album;
    private String length;
    private Long resourceId;
    private Integer year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetaDataDto that = (MetaDataDto) o;
        return Objects.equals(title, that.title) && Objects.equals(artist, that.artist) && Objects.equals(album, that.album) && Objects.equals(length, that.length) && Objects.equals(resourceId, that.resourceId) && Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, album, length, resourceId, year);
    }

    @Override
    public String toString() {
        return "MetaDataDto{" +
                "name='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", length='" + length + '\'' +
                ", resourceId=" + resourceId +
                ", year=" + year +
                '}';
    }
}

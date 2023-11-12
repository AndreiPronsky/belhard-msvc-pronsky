package com.belhard.songservice.data;

import com.belhard.songservice.data.entities.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaDataRepository extends JpaRepository<MetaData, Long> {
}

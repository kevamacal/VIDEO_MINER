package aiss.videominer.repository;

import aiss.videominer.model.Channel;
import aiss.videominer.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
}

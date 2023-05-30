package flyffbot.server.repositories;

import flyffbot.server.entity.PipelineEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PipelineRepository extends CrudRepository<PipelineEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE pipeline SET selectedWindowHwnd = :selectedWindowHwnd where id = :id")
    void updateSelectedWindowHwndById(@Param("id") Long id, @Param("selectedWindowHwnd") String selectedWindowHwnd);

    @Modifying
    @Transactional
    @Query("UPDATE pipeline SET paused = true")
    int pauseAll();

    @Modifying
    @Transactional
    @Query("UPDATE pipeline SET paused = (MOD(paused+1, 2) = 1) WHERE id = :id") //Not recognizing NOT, workaround applied :D
    int togglePausedById(@Param("id") Long id);

    PipelineEntity findTopByOrderByIdDesc();

    @Modifying
    @Transactional
    @Query("UPDATE pipeline SET paused = :paused WHERE id = :id")
    void updatePausedById(@Param("id") Long id, @Param("paused") boolean paused);

    @Modifying
    @Transactional
    @Query("UPDATE pipeline SET customActionSlotRunning = :customActionSlotRunning WHERE id = :id")
    void updateCustomActionSlotRunningById(@Param("id") Long id, @Param("customActionSlotRunning") boolean customActionSlotRunning);
}

package flyffbot.server.repositories;

import flyffbot.server.entity.HotkeyEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface HotkeyRepository extends CrudRepository<HotkeyEntity, Long> {
    HotkeyEntity findLastByPipelineId(@Param("pipelineId") Long pipelineId);

    @Modifying
    @Transactional
    @Query("UPDATE hotkey SET hexKeyCode0 = :hexKeyCode0 WHERE id = :id")
    int updateHexKeyCode0ById(@Param("id") Long id, @Param("hexKeyCode0") String hexKeyCode0);

    @Modifying
    @Transactional
    @Query("UPDATE hotkey SET hexKeyCode1 = :hexKeyCode1 WHERE id = :id")
    int updateHexKeyCode1ById(@Param("id") Long id, @Param("hexKeyCode1") String hexKeyCode1);

    @Modifying
    @Transactional
    @Query("UPDATE hotkey SET delayMs = :delayMs WHERE id = :id")
    int updateDelayMsById(@Param("id") Long id, @Param("delayMs") Long delayMs);

    @Modifying
    @Transactional
    @Query("UPDATE hotkey SET active = :active WHERE id = :id")
    int updateActiveById(@Param("id") Long id, @Param("active") boolean active);

    @Modifying
    @Transactional
    @Query("UPDATE hotkey SET lastTimeExecutedMs = :lastTimeExecutedMs WHERE id = :id")
    void updateLastTimeExecutedMs(long id, long lastTimeExecutedMs);

    List<HotkeyEntity> findByPipelineId(long pipelineId);

    @Modifying
    @Transactional
    @Query("UPDATE hotkey SET active = false")
    List<HotkeyEntity> disableAll();

    @Query("SELECT h from hotkey h where active = true ORDER BY nextExecutionTimeMs ASC")
    List<HotkeyEntity> findAllActiveOrderByNextExecutionTimeMsAsc();
}

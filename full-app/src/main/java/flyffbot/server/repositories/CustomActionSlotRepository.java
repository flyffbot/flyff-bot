package flyffbot.server.repositories;

import flyffbot.server.entity.CustomActionSlotEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomActionSlotRepository extends CrudRepository<CustomActionSlotEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE customActionSlot SET hexKeyCode0 = :hexKeyCode0 WHERE id = :id")
    void updateHexKeyCode0ById(@Param("id") Long id, @Param("hexKeyCode0") String hexKeyCode0);


    @Modifying
    @Transactional
    @Query("UPDATE customActionSlot SET hexKeyCode1 = :hexKeyCode1 WHERE id = :id")
    void updateHexKeyCode1ById(@Param("id") Long id, @Param("hexKeyCode1") String hexKeyCode1);


    @Modifying
    @Transactional
    @Query("UPDATE customActionSlot SET castTimeMs = :castTimeMs WHERE id = :id")
    void updateCastTimeMsById(@Param("id") Long id, @Param("castTimeMs") Long castTimeMs);

    List<CustomActionSlotEntity> findByPipelineId(long pipelineId);
}

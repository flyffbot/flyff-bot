package flyffbot.server.services;

import flyffbot.server.entity.CustomActionSlotEntity;
import flyffbot.server.exceptions.HotkeyNotFound;
import flyffbot.server.repositories.CustomActionSlotRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomActionSlotServiceImpl {
    @Autowired
    private CustomActionSlotRepository repository;

    public CustomActionSlotEntity addCustomActionSlot(Long pipelineId) {
        val toInsert = CustomActionSlotEntity.builder()
                .pipelineId(pipelineId)
                .hexKeyCode1("0x31")
                .castTimeMs(1000L)
                .build();
        return repository.save(toInsert);
    }

    public void updateCustomActionSlot(Long id, int keyIndex, String hexValue){
        switch (keyIndex) {
            case 0:
                repository.updateHexKeyCode0ById(id, hexValue);
                break;
            case 1:
                repository.updateHexKeyCode1ById(id, hexValue);
                break;
            default:
                throw new HotkeyNotFound("Invalid key index, allowed: 0=first key, 1=second key");
        }
    }

    public void updateCastTime(Long id, Long castTimeMs) {
        repository.updateCastTimeMsById(id, castTimeMs);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public List<CustomActionSlotEntity> findByPipelineId(long pipelineId) {
        return repository.findByPipelineId(pipelineId);
    }
}

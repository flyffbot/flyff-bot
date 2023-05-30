package flyffbot.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "customActionSlot")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomActionSlotEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String hexKeyCode0;
    private String hexKeyCode1;
    private long castTimeMs;
    private long pipelineId;
    private long lastTimeExecutedMs;
}

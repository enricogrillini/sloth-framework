package it.eg.sloth.framework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Enrico Grillini
 */
@Slf4j
public class FrameComponent {

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("toString error", e);
            return StringUtil.EMPTY;
        }
    }

}

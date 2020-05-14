package it.eg.sloth.framework.common.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseMessage implements Message {

  Level severity;
  String description;
  String subDescription;


}

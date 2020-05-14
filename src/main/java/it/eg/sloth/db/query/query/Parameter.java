package it.eg.sloth.db.query.query;

import it.eg.sloth.framework.FrameComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Enrico Grillini
 * 
 */
@Getter
@Setter
@AllArgsConstructor
public class Parameter extends FrameComponent {

  int sqlType;
  Object value;

}

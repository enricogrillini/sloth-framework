package it.eg.sloth.webdesktop.dto.notificationcenter.impl;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.webdesktop.dto.notificationcenter.iface.NotificationMessageFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseMessageFunction extends FrameComponent implements NotificationMessageFunction {

  String name;
  String title;
  String className;

}

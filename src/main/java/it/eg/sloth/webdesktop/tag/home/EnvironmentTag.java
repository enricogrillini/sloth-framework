package it.eg.sloth.webdesktop.tag.home;

import it.eg.sloth.framework.configuration.ConfigSingleton;
import it.eg.sloth.framework.view.AbstractTag;
import it.eg.sloth.jaxb.config.Environment;

public class EnvironmentTag extends AbstractTag {

  private static final long serialVersionUID = 1L;

  @Override
  protected int startTag() throws Throwable {

    writeln("<div style=\"position: absolute; bottom: 10px; left: 20px;\">");

    if (ConfigSingleton.getInstance().getEnvironment() == Environment.DEVELOP) {
      writeln(" <div style=\"font-size: 20pt\">Ambiente: <b>Sviluppo</b></div>");
    } else if (ConfigSingleton.getInstance().getEnvironment() == Environment.TEST) {
      writeln(" <div style=\"font-size: 20pt\">Ambiente: <b>Test</b></div>");
    } else if (ConfigSingleton.getInstance().getEnvironment() == Environment.PRODUCTION) {
      writeln(" <div style=\"font-size: 20pt\">Ambiente: <b>Produzione</b></div>");
    }

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    writeln("</div>");
  }

}

package it.eg.sloth.webdesktop.api;

import it.eg.sloth.form.Form;
import it.eg.sloth.webdesktop.api.model.BffComponent;
import it.eg.sloth.webdesktop.dto.WebDesktopDto;

public class BffApi {

  protected <F extends Form> boolean checkAccess(BffComponent bffComponent, WebDesktopDto webDesktopDto) {
    if (webDesktopDto.getUser() == null || !webDesktopDto.getUser().isLogged()) {
      bffComponent.setSessionExpired(true);
      bffComponent.getMessageList().addBaseError("Sessione scaduta");

      return false;
    }

    return true;
  }

  protected <F extends Form> boolean checkAccess(BffComponent bffComponent, WebDesktopDto webDesktopDto, Class<F> myClass) {
    if (!checkAccess(bffComponent, webDesktopDto)) {
      return false;
    }

    if (myClass != null && !myClass.isInstance(webDesktopDto.getForm())) {
      bffComponent.setWrongPage(true);
      bffComponent.getMessageList().addBaseError("Pagina errata");

      return false;
    }

    return true;
  }

}

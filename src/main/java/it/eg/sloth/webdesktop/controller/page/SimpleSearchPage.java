package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.webdesktop.controller.common.SimpleSearchPageInterface;

/**
 * Fornisce l'implementazione di una pagina di ricerca
 *
 * @param <F>
 * @author Enrico Grillini
 */
public abstract class SimpleSearchPage<F extends Form> extends SimplePage<F> implements SimpleSearchPageInterface {

    public SimpleSearchPage() {
        super();
    }

    @Override
    public boolean defaultNavigation() throws Exception {
        if (super.defaultNavigation()) {
            return true;
        }

        String[] navigation = getWebRequest().getNavigation();

        if (navigation.length == 1) {
            if (NavigationConst.LOAD.equals(navigation[0])) {
                onLoad();
                return true;
            } else if (NavigationConst.RESET.equals(navigation[0])) {
                onReset();
                return true;
            }
        }

        return false;
    }

    public void onLoad() throws Exception {
        execLoad();
    }

    public void onReset() throws Exception {
        execReset();
    }

}

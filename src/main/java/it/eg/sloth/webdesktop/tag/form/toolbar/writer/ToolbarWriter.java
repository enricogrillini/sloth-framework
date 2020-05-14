package it.eg.sloth.webdesktop.tag.form.toolbar.writer;

import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.webdesktop.tag.form.AbstractHtmlWriter;

public class ToolbarWriter extends AbstractHtmlWriter {


    /**
     * excelButton
     *
     * @return
     */
    public static String excelButton(String name, String lastController, boolean disabled) {
        String hrefHtml = " href=\"" + lastController + "?" + NavigationConst.navStr(NavigationConst.EXCEL, name) + "=true\"";

        String disabledHtml = "";
        if (disabled) {
            disabledHtml = " disabled=\"\"";
        }

        return " <a" + hrefHtml + disabledHtml + " class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Esporta in excel\"><i class=\"far fa-file-excel\"></i> Excel</a>";
    }

    /**
     * elencoButton
     *
     * @param disabled
     * @return
     */
    public static String elencoButton(boolean disabled) {
        return new StringBuilder()
                .append("<button name=\"")
                .append(NavigationConst.navStr(NavigationConst.ELENCO))
                .append("\"")
                .append(getAttribute("disabled", disabled, ""))
                .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Ritorna all'elenco\"><i class=\"fas fa-table\"></i> Elenco</button>")
                .toString();
    }

    /**
     * Navigazione di base: primo record
     *
     * @param grid
     * @param disabled
     * @return
     */
    public static String firstRowButton(Grid<?> grid, boolean disabled) {
        if (grid.isFirstButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append("<button name=\"")
                    .append(NavigationConst.navStr(NavigationConst.FIRST_ROW, grid.getName()))
                    .append("\"")
                    .append(getAttribute("disabled", disabled || grid.getDataSource().isFirst(), ""))
                    .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Primo record\"><i class=\"fas fa-fast-backward\"></i></button>")
                    .toString();
        }
    }

    /**
     * Navigazione di base: pagina precedente
     *
     * @param grid
     * @param disabled
     * @return
     */
    public static String prevPageButton(Grid<?> grid, boolean disabled) {
        if (grid.isPrevPageButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append("<button name=\"")
                    .append(NavigationConst.navStr(NavigationConst.PREV_PAGE, grid.getName()))
                    .append("\"")
                    .append(getAttribute("disabled", disabled || grid.getDataSource().isFirstPage(), ""))
                    .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Pagina precedende\"><i class=\"fas fa-backward\"></i></button>")
                    .toString();
        }
    }

    /**
     * Navigazione di base: record precedente
     *
     * @param grid
     * @param disabled
     * @return
     */
    public static String prevButton(Grid<?> grid, boolean disabled) {
        if (grid.isPrevButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append("<button name=\"")
                    .append(NavigationConst.navStr(NavigationConst.PREV, grid.getName()))
                    .append("\"")
                    .append(getAttribute("disabled", disabled || grid.getDataSource().isFirst(), ""))
                    .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Record precedende\"><i class=\"fas fa-step-backward\"></i></button>")
                    .toString();
        }
    }

    /**
     * Navigazione di base: record successivo
     *
     * @param grid
     * @param disabled
     * @return
     */
    public static String nextButton(Grid<?> grid, boolean disabled) {
        if (grid.isNextButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append("<button name=\"")
                    .append(NavigationConst.navStr(NavigationConst.NEXT, grid.getName()))
                    .append("\"")
                    .append(getAttribute("disabled", disabled || grid.getDataSource().isLast(), ""))
                    .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Record successivo\"><i class=\"fas fa-step-forward\"></i></button>")
                    .toString();
        }
    }

    /**
     * Navigazione di base: pagina sucessiva
     *
     * @param grid
     * @param disabled
     * @return
     */
    public static String nextPageButton(Grid<?> grid, boolean disabled) {
        if (grid.isNextPageButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append("<button name=\"")
                    .append(NavigationConst.navStr(NavigationConst.NEXT_PAGE, grid.getName()))
                    .append("\"")
                    .append(getAttribute("disabled", disabled || grid.getDataSource().isLastPage(), ""))
                    .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Pagina sucessiva\"><i class=\"fas fa-forward\"></i></button>")
                    .toString();
        }
    }

    /**
     * Navigazione di base: Ultima pagina
     *
     * @param grid
     * @param disabled
     * @return
     */
    public static String lastRowButton(Grid<?> grid, boolean disabled) {
        if (grid.isLastButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append("<button name=\"")
                    .append(NavigationConst.navStr(NavigationConst.LAST_ROW, grid.getName()))
                    .append("\"")
                    .append(getAttribute("disabled", disabled || grid.getDataSource().isLast(), ""))
                    .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Ultimo record\"><i class=\"fas fa-fast-forward\"></i></button>")
                    .toString();
        }
    }


    /**
     * Scrive il pulsante Commit
     */
    public static String commitButton() {
        return new StringBuilder()
                .append("<button name=\"")
                .append(NavigationConst.navStr(NavigationConst.COMMIT))
                .append("\"")
                .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Salva\"><i class=\"fas fa-save\"></i> Salva</button>")
                .toString();
    }

    /**
     * Scrive il pulsante Rollback
     */
    public static String rollbackButton() {
        return new StringBuilder()
                .append("<button name=\"")
                .append(NavigationConst.navStr(NavigationConst.ROLLBACK))
                .append("\"")
                .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Annulla modifiche\"><i class=\"fas fa-undo-alt\"></i> Annulla</button>")
                .toString();
    }

    /**
     * Scrive il pulsante Insert
     *
     * @param grid
     * @return
     */
    public static String insertButton(Grid<?> grid) {
        if (grid.isDeleteButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append("<button name=\"")
                    .append(NavigationConst.navStr(NavigationConst.INSERT, grid.getName()))
                    .append("\"")
                    .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Inserisci record\"><i class=\"fas fa-plus\"></i> Inserisci</button>")
                    .toString();
        }
    }

    /**
     * Scrive il pulsante Delete
     *
     * @param grid
     * @return
     */
    public static String deleteButton(Grid<?> grid) {
        if (grid.isDeleteButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append("<button name=\"")
                    .append(NavigationConst.navStr(NavigationConst.DELETE, grid.getName()))
                    .append("\"")
                    .append(getAttribute("disabled", grid.getDataSource().size() == 0, ""))
                    .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Elimina il record corrente\"><i class=\"fas fa-minus\"></i> Elimina</button>")
                    .toString();
        }
    }

    /**
     * Scrive il pulsante Update
     *
     * @param grid
     * @return
     */
    public static String updateButton(Grid<?> grid) {
        return updateButton(grid.getName(), grid.isUpdateButtonHidden(), grid.getDataSource().size() == 0);
    }

    /**
     * Scrive il pulsante Update
     *
     * @param name
     * @param hidden
     * @param disabled
     * @return
     */
    public static String updateButton(String name, boolean hidden, boolean disabled) {
        if (hidden) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append("<button name=\"")
                    .append(NavigationConst.navStr(NavigationConst.UPDATE, name))
                    .append("\"")
                    .append(getAttribute("disabled", disabled, ""))
                    .append(" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Modifica il record corrente\"><i class=\"fas fa-pencil-alt\"></i> Modifica</button>")
                    .toString();
        }
    }

}

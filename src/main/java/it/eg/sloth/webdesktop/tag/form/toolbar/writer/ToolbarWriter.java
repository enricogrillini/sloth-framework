package it.eg.sloth.webdesktop.tag.form.toolbar.writer;

import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.chart.SimpleChart;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.pageinfo.PageStatus;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Enrico Grillini
 */
public class ToolbarWriter extends HtmlWriter {

    private static final String OPEN_BAR = "\n" +
            "<!-- Toolbar -->\n" +
            "<div class=\"d-flex justify-content-between p-1\">\n";

    private static final String OPEN_SECTION = " <div class=\"form-inline\">\n";

    private static final String CLOSE_SECTION = " </div>\n";

    private static final String CLOSE_BAR = "</div>\n";

    private static final String EXCEL = "  <a{0} class=\"btn btn-link btn-sm{1}\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Esporta in excel\"><i class=\"far fa-file-excel\"></i> Excel</a>\n";

    private static final String ELENCO = "  <button{0}{1} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Ritorna all''elenco\"><i class=\"fas fa-table\"></i> Elenco</button>\n";

    private static final String FIRST_ROW = "  <button{0}{1} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Primo record\"><i class=\"fas fa-fast-backward\"></i></button>\n";
    private static final String PREV_PAGE = "  <button{0}{1} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Pagina precedende\"><i class=\"fas fa-backward\"></i></button>\n";
    private static final String PREV = "  <button{0}{1} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Record precedende\"><i class=\"fas fa-step-backward\"></i></button>\n";
    private static final String NEXT = "  <button{0}{1} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Record successivo\"><i class=\"fas fa-step-forward\"></i></button>\n";
    private static final String NEXT_PAGE = "  <button{0}{1} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Pagina sucessiva\"><i class=\"fas fa-forward\"></i></button>\n";
    private static final String LAST_ROW = "  <button{0}{1} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Ultimo record\"><i class=\"fas fa-fast-forward\"></i></button>\n";

    private static final String INSERT = "  <button{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Inserisci record\"><i class=\"fas fa-plus\"></i> Inserisci</button>\n";
    private static final String DELETE = "  <button{0}{1} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Elimina il record corrente\"><i class=\"far fa-trash-alt\"></i> Elimina</button>\n";
    private static final String UPDATE = "  <button{0}{1} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Modifica il record corrente\"><i class=\"fas fa-pencil-alt\"></i> Modifica</button>\n";

    private static final String COMMIT = "  <button{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Salva\"><i class=\"fas fa-save\"></i> Salva</button>\n";
    private static final String ROLLBACK = "  <button{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Annulla modifiche\"><i class=\"fas fa-undo-alt\"></i> Annulla</button>\n";

    private static final String INFO_EMPTY = "  <span class=\"btn-sm align-middle\"> Tabella vuota</span>";
    private static final String INFO_RECORDS = "  <span class=\"btn-sm align-middle\"> {0} Records</span>\n";
    private static final String INFO_REC = "  <span class=\"btn-sm align-middle\"> Rec. {0} di {1}</span>\n";
    private static final String INFO_PAG = "  <span class=\"btn-sm align-middle\"> Pag. {0} di {1}</span>\n";

    /**
     * excelButton
     *
     * @return
     */
    public static String excelButton(String name, String lastController, boolean disabled) {
        return MessageFormat.format(EXCEL,
                getAttribute("href", lastController + "?" + NavigationConst.navStr(NavigationConst.EXCEL, name) + "=true"),
                disabled ? " disabled" : "");
    }

    /**
     * elencoButton
     *
     * @param disabled
     * @return
     */
    public static String elencoButton(boolean disabled) {
        return MessageFormat.format(ELENCO,
                getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.ELENCO)),
                getAttribute(ATTR_DISABLED, disabled, ""));
    }

    /**
     * Navigazione di base: primo record
     *
     * @param grid
     * @param disabled
     * @return
     */
    public static String firstRowButton(Grid<?> grid, boolean disabled) {
        if (grid == null || grid.getDataSource() == null || grid.isFirstButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return MessageFormat.format(FIRST_ROW,
                    getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.FIRST_ROW, grid.getName())),
                    getAttribute(ATTR_DISABLED, disabled || grid.getDataSource().isFirst(), ""));
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
        if (grid == null || grid.getDataSource() == null || grid.isPrevPageButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return MessageFormat.format(PREV_PAGE,
                    getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.PREV_PAGE, grid.getName())),
                    getAttribute(ATTR_DISABLED, disabled || grid.getDataSource().isFirstPage(), ""));
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
        if (grid == null || grid.getDataSource() == null || grid.isPrevButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return MessageFormat.format(PREV,
                    getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.PREV, grid.getName())),
                    getAttribute(ATTR_DISABLED, disabled || grid.getDataSource().isFirst(), ""));
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
        if (grid == null || grid.getDataSource() == null || grid.isNextButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return MessageFormat.format(NEXT,
                    getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.NEXT, grid.getName())),
                    getAttribute(ATTR_DISABLED, disabled || grid.getDataSource().isLast(), ""));
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
        if (grid == null || grid.getDataSource() == null || grid.isNextPageButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return MessageFormat.format(NEXT_PAGE,
                    getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.NEXT_PAGE, grid.getName())),
                    getAttribute(ATTR_DISABLED, disabled || grid.getDataSource().isLastPage(), ""));
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
        if (grid == null || grid.getDataSource() == null || grid.isLastButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return MessageFormat.format(LAST_ROW,
                    getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.LAST_ROW, grid.getName())),
                    getAttribute(ATTR_DISABLED, disabled || grid.getDataSource().isLast(), ""));
        }
    }

    /**
     * Scrive il pulsante Insert
     *
     * @param grid
     * @return
     */
    public static String insertButton(Grid<?> grid) {
        if (grid == null || grid.getDataSource() == null || grid.isInsertButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return MessageFormat.format(INSERT,
                    getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.INSERT, grid.getName())));
        }
    }

    /**
     * Scrive il pulsante Delete
     *
     * @param grid
     * @return
     */
    public static String deleteButton(Grid<?> grid) {
        if (grid == null || grid.getDataSource() == null || grid.isDeleteButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return MessageFormat.format(DELETE,
                    getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.DELETE, grid.getName())),
                    getAttribute(ATTR_DISABLED, grid.getDataSource().size() == 0, ""));
        }
    }

    /**
     * Scrive il pulsante Update
     *
     * @param grid
     * @return
     */
    public static String updateButton(Grid<?> grid) {
        if (grid == null || grid.getDataSource() == null || grid.isUpdateButtonHidden()) {
            return StringUtil.EMPTY;
        } else {
            return updateButton(grid.getName(), grid.isUpdateButtonHidden(), grid.getDataSource().size() == 0);
        }
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
            return MessageFormat.format(UPDATE,
                    getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.UPDATE, name)),
                    getAttribute(ATTR_DISABLED, disabled, ""));
        }
    }

    /**
     * Scrive il pulsante Commit
     */
    public static String commitButton() {
        return MessageFormat.format(COMMIT,
                getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.COMMIT)));
    }

    /**
     * Scrive il pulsante Rollback
     */
    public static String rollbackButton() {
        return MessageFormat.format(ROLLBACK,
                getAttribute(ATTR_NAME, NavigationConst.navStr(NavigationConst.ROLLBACK)));
    }


    /**
     * Apre la sezione di sinistra
     */
    public static String openLeft() {
        return OPEN_BAR + OPEN_SECTION;
    }

    /**
     * Chiude la sezione di sinistra
     */
    public static String closeLeft() {
        return CLOSE_SECTION;
    }

    /**
     * Apre la sezione di destra
     */
    public static String openRight() {
        return OPEN_SECTION;
    }

    /**
     * Chiude la sezione di destra
     */
    public static String closeRight() {
        return CLOSE_SECTION + CLOSE_BAR;
    }


    /**
     * Barra di navigazione semplice: non gestisce il concetto di record corrente
     *
     * @param grid
     * @return
     */
    public static String gridNavigationSimple(Grid<?> grid, String lastController, PageStatus pageStatus) {
        StringBuilder result = new StringBuilder();
        if (grid.getDataSource() == null || grid.getDataSource().size() == 0) {
            result.append(INFO_EMPTY);
        } else {
            if (grid.getDataSource().getPageSize() > 0) {
                result
                        .append(firstRowButton(grid, pageStatus.isChanging()))
                        .append(prevPageButton(grid, pageStatus.isChanging()))
                        .append(nextPageButton(grid, pageStatus.isChanging()))
                        .append(lastRowButton(grid, pageStatus.isChanging()))
                        .append(MessageFormat.format(INFO_PAG, (grid.getDataSource().getCurrentPage() + 1), grid.getDataSource().pages()));
            } else {
                result.append(MessageFormat.format(INFO_RECORDS, grid.getDataSource().size()));
            }

            result.append(excelButton(grid.getName(), lastController, pageStatus.isChanging()));
        }

        return result.toString();
    }

    public static String gridEditingSimple(Grid<?> grid, PageStatus pageStatus) {
        if (grid.getDataSource() == null) {
            return StringUtil.EMPTY;
        }

        StringBuilder result = new StringBuilder()
                .append(ToolbarWriter.insertButton(grid))
                .append(ToolbarWriter.deleteButton(grid));

        if (pageStatus.isChanging()) {
            result
                    .append(ToolbarWriter.commitButton())
                    .append(ToolbarWriter.rollbackButton());
        } else {
            result.append(ToolbarWriter.updateButton(grid));
        }

        return result.toString();
    }

    public static String gridNavigationEditable(Grid<?> grid, String lastController, PageStatus pageStatus) {
        StringBuilder result = new StringBuilder();
        if (grid.getDataSource() == null || grid.getDataSource().size() == 0) {
            result.append(INFO_EMPTY);
        } else {
            if (grid.getDataSource().getPageSize() > 0) {
                result
                        .append(firstRowButton(grid, false))
                        .append(prevPageButton(grid, false))
                        .append(prevButton(grid, false))
                        .append(nextButton(grid, false))
                        .append(nextPageButton(grid, false))
                        .append(lastRowButton(grid, false));
            } else {
                result
                        .append(firstRowButton(grid, false))
                        .append(prevButton(grid, false))
                        .append(nextButton(grid, false))
                        .append(lastRowButton(grid, false));
            }

            result.append(excelButton(grid.getName(), lastController, pageStatus.isChanging()));

            // Informazioni di sintesi
            if (grid.getDataSource().size() > 0) {
                result.append(MessageFormat.format(INFO_REC, (grid.getDataSource().getCurrentRow() + 1), grid.getDataSource().size()));
                if (grid.getDataSource().getPageSize() > 0) {
                    result.append(MessageFormat.format(INFO_PAG, (grid.getDataSource().getCurrentPage() + 1), grid.getDataSource().pages()));
                }
            }
        }
        return result.toString();
    }

    public static String gridNavigationMasterDetail(Grid<?> grid, String lastController, PageStatus pageStatus) {
        StringBuilder result = new StringBuilder();
        if (grid.getDataSource() == null || grid.getDataSource().size() == 0) {
            result.append(INFO_EMPTY);
        } else {
            if (grid.getDataSource().getPageSize() > 0) {
                result
                        .append(firstRowButton(grid, pageStatus.isChanging()))
                        .append(prevPageButton(grid, pageStatus.isChanging()))
                        .append(prevButton(grid, pageStatus.isChanging()))
                        .append(nextButton(grid, pageStatus.isChanging()))
                        .append(nextPageButton(grid, pageStatus.isChanging()))
                        .append(lastRowButton(grid, pageStatus.isChanging()));
            } else {
                result
                        .append(firstRowButton(grid, pageStatus.isChanging()))
                        .append(prevButton(grid, pageStatus.isChanging()))
                        .append(nextButton(grid, pageStatus.isChanging()))
                        .append(lastRowButton(grid, pageStatus.isChanging()));
            }

            if (pageStatus == PageStatus.MASTER) {
                result.append(excelButton(grid.getName(), lastController, pageStatus.isChanging()));
            } else {
                result.append(elencoButton(pageStatus.isChanging()));
            }

            // Informazioni di sintesi
            if (grid.getDataSource().size() > 0) {
                result.append(MessageFormat.format(INFO_REC, (grid.getDataSource().getCurrentRow() + 1), grid.getDataSource().size()));
                if (grid.getDataSource().getPageSize() > 0) {
                    result.append(MessageFormat.format(INFO_PAG, (grid.getDataSource().getCurrentPage() + 1), grid.getDataSource().pages()));
                }
            }
        }

        return result.toString();
    }

    public static String gridEditingMasterDetail(Grid<?> grid, PageStatus pageStatus, boolean insertButtonHidden, boolean deleteButtonHidden, boolean updateButtonHidden, boolean commitButtonHidden, boolean rollbackButtonHidden) {
        if (grid == null) {
            return StringUtil.EMPTY;
        }

        StringBuilder result = new StringBuilder();
        if (pageStatus.isClean()) {
            if (!insertButtonHidden) {
                result.append(ToolbarWriter.insertButton(grid));
            }

            if (!deleteButtonHidden) {
                result.append(ToolbarWriter.deleteButton(grid));
            }

            if (!updateButtonHidden) {
                result.append(ToolbarWriter.updateButton(grid));
            }
        } else {
            if (!commitButtonHidden) {
                result.append(ToolbarWriter.commitButton());
            }

            if (!rollbackButtonHidden) {
                result.append(ToolbarWriter.rollbackButton());
            }
        }

        return result.toString();
    }

    public static String gridEditingSubMasterDetail(Grid<?> grid, PageStatus pageStatus) {
        if (grid == null) {
            return StringUtil.EMPTY;
        }

        StringBuilder result = new StringBuilder();
        if (pageStatus.isChanging()) {
            result.append(ToolbarWriter.insertButton(grid));

            if (!grid.isCurrentRowLocked()) {
                result.append(ToolbarWriter.deleteButton(grid));
            }
        }

        return result.toString();
    }


    public static String simpleChart(SimpleChart<?> chart, String lastController, PageStatus pageStatus) {
        StringBuilder result = new StringBuilder();
        if (chart.getDataTable() == null || chart.getDataTable().size() == 0) {
            // NOP
        } else {
            result.append(excelButton(chart.getName(), lastController, pageStatus.isChanging()));
        }
        return result.toString();
    }


}

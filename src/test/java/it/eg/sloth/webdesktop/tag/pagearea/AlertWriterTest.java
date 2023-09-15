package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.alertcenter.AlertsCenterSingleton;
import it.eg.sloth.webdesktop.alertcenter.model.Alert;
import it.eg.sloth.webdesktop.alertcenter.model.AlertType;
import it.eg.sloth.webdesktop.tag.pagearea.writer.ContentWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class AlertWriterTest {
    private static final String ALERTS_CENTER_1 = ResourceUtil.normalizedResourceAsString("snippet-html/pagearea/alert-center-1.html");
    private static final String ALERTS_CENTER_2 = ResourceUtil.normalizedResourceAsString("snippet-html/pagearea/alert-center-2.html");

    private static final String ALERTS_CARD_1 = ResourceUtil.normalizedResourceAsString("snippet-html/pagearea/alert-cards-1.html");
    private static final String ALERTS_CARD_2 = ResourceUtil.normalizedResourceAsString("snippet-html/pagearea/alert-cards-2.html");

    Alert alert1;
    Alert alert2;

    @BeforeEach
    void init() throws FrameworkException {
        alert1 = Alert.builder()
                .idAlert(1)
                .type(AlertType.INFO)
                .date(TimeStampUtil.parseTimestamp("21/09/2021", TimeStampUtil.DEFAULT_FORMAT))
                .text("text")
                .detail("detail")
                .href(null)
                .build();

        alert2 = Alert.builder()
                .idAlert(2)
                .type(AlertType.WARNING)
                .date(TimeStampUtil.parseTimestamp("21/09/2021", TimeStampUtil.DEFAULT_FORMAT))
                .text("text2")
                .detail("detail2")
                .href("www2")
                .build();

        AlertsCenterSingleton.getInstance().clear();
        AlertsCenterSingleton.getInstance().add(alert1);
    }

    @Test
    void writeAlertCenterTest() throws FrameworkException {
        assertEquals(ALERTS_CENTER_1, ContentWriter.alertCenter(Locale.ITALY).replace("\r\n", "\n"));

        AlertsCenterSingleton.getInstance().add(alert2);
        assertEquals(ALERTS_CENTER_2, ContentWriter.alertCenter(Locale.ITALY).replace("\r\n", "\n"));
    }

    @Test
    void writeAlertsCardTest() throws FrameworkException {
        assertEquals(ALERTS_CARD_1, ContentWriter.alertCards(Locale.ITALY));

        AlertsCenterSingleton.getInstance().add(alert2);
        assertEquals(ALERTS_CARD_2, ContentWriter.alertCards(Locale.ITALY));
    }

}

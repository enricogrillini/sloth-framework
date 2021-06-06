package it.eg.sloth.form.modal;

import it.eg.sloth.form.base.Element;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class Modal implements Element {

    String name;
    Locale locale;
    String title;

    public Modal(String name, String title) {
        this.name = name.toLowerCase();
        this.locale = Locale.getDefault();
        this.title = title;
    }


}